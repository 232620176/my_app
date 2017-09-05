package com.hydra.blank.db.sqlmap.impl;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.util.Assert;

import com.hydra.blank.db.sqlmap.SqlMap;
import com.hydra.blank.db.sqlmap.SqlMapSession;
import com.hydra.blank.db.sqlmap.support.SqlSessionUtils;

public class SqlMapImpl implements SqlMap {
	
	static Logger logger = LoggerFactory.getLogger(SqlMapImpl.class);

	private boolean cacheStatement;
	
	private DataSource dataSource;

	private PersistenceExceptionTranslator exceptionTranslator;

	private SqlSessionFactory sqlMapClient;

	public SqlMapImpl(DataSource dataSource, SqlSessionFactory sqlMapClient, PersistenceExceptionTranslator exceptionTranslator) {
		this.dataSource = dataSource;
		this.sqlMapClient = sqlMapClient;
		this.exceptionTranslator = exceptionTranslator;
		if (this.sqlMapClient == null) {
			throw new IllegalArgumentException("Property 'sqlSessionFactory' is required");
		}
	}

	@Override
	public SqlMapSession startSession(boolean batch) {
		SqlSession session = batch ? createSession(dataSource, ExecutorType.BATCH) : createSession(dataSource,
				cacheStatement ? ExecutorType.REUSE : ExecutorType.SIMPLE);
		return new SqlMapSessionImpl(session, this.sqlMapClient, this.exceptionTranslator);
	}

	private SqlSession createSession(DataSource src, ExecutorType type) {

		// Obtain JDBC Connection to operate on...
		SqlSession session = SqlSessionUtils.getSqlSession(this.sqlMapClient, type, this.exceptionTranslator);
		if (logger.isDebugEnabled()) {
			logger.debug("Opened SqlSession [" + session + "] for iBATIS operation");
		}

		return session;

	}

	private <T> T execute(SqlMapCallback<T> action) throws DataAccessException {
		Assert.notNull(action, "Callback object must not be null");
		SqlSession session = createSession(dataSource, cacheStatement ? ExecutorType.REUSE : ExecutorType.SIMPLE);
		try {
			// Execute given callback...
			try {
				T result = action.doInSession(session);
				if (!SqlSessionUtils.isSqlSessionTransactional(session, this.sqlMapClient)) {
					session.commit();
				}
				return result;
			} catch (Throwable ex) {
				Throwable unwrapped = ExceptionUtil.unwrapThrowable(ex);
				if (this.exceptionTranslator != null && unwrapped instanceof PersistenceException) {
					unwrapped = this.exceptionTranslator.translateExceptionIfPossible((PersistenceException) unwrapped);
				}
				throw (DataAccessException) unwrapped;
			}
			// Processing finished - potentially session still to be closed.
		} finally {
			SqlSessionUtils.closeSqlSession(session, this.sqlMapClient);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T queryForObject(String statementName) throws DataAccessException {
		return (T) queryForObject(statementName, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T queryForObject(final String statementName, final Object parameterObject) throws DataAccessException {
		return (T) execute(new SqlMapCallback<Object>() {
			@Override
			public Object doInSession(SqlSession executor) {
				return executor.selectOne(statementName, parameterObject);
			}
		});
	}

	@Override
	public <T> List<T> queryForList(String statementName) throws DataAccessException {
		return queryForList(statementName, null);
	}

	@Override
	public <T> List<T> queryForList(final String statementName, final Object parameterObject) throws DataAccessException {
		return execute(new SqlMapCallback<List<T>>() {
			@Override
			public List<T> doInSession(SqlSession executor) {
				return executor.selectList(statementName, parameterObject);
			}
		});
	}

	public <T> List<T> queryForList(String statementName, int skipResults, int maxResults) throws DataAccessException {
		return queryForList(statementName, null, skipResults, maxResults);
	}

	@Override
	public <T> List<T> queryForList(final String statementName, final Object parameterObject, final int skipResults,
			final int maxResults) throws DataAccessException {
		return execute(new SqlMapCallback<List<T>>() {
			@Override
			public List<T> doInSession(SqlSession executor) {
				return executor.selectList(statementName, parameterObject, new RowBounds(skipResults, maxResults));
			}
		});
	}

	@Override
	public void queryWithRowHandler(final String statementName, final Object parameterObject, final ResultHandler rowHandler)
			throws DataAccessException {
		execute(new SqlMapCallback<Object>() {
			@Override
			public Object doInSession(SqlSession executor) {
				executor.select(statementName, parameterObject, rowHandler);
				return null;
			}
		});
	}

	@Override
	public void queryWithRowHandler(final String statementName, final Object parameterObject, final int skipResults,
			final int maxResults, final ResultHandler rowHandler) throws DataAccessException {
		execute(new SqlMapCallback<Object>() {
			@Override
			public Object doInSession(SqlSession executor) {
				executor.select(statementName, parameterObject, new RowBounds(skipResults, maxResults), rowHandler);
				return null;
			}
		});
	}

	@Override
	public int insert(String statementName) throws DataAccessException {
		return insert(statementName, null);
	}

	@Override
	public int insert(final String statementName, final Object parameterObject) throws DataAccessException {
		return execute(new SqlMapCallback<Integer>() {
			@Override
			public Integer doInSession(SqlSession executor) {
				return executor.insert(statementName, parameterObject);
			}
		});
	}

	@Override
	public int update(String statementName) throws DataAccessException {
		return update(statementName, null);
	}

	@Override
	public int update(final String statementName, final Object parameterObject) throws DataAccessException {
		return execute(new SqlMapCallback<Integer>() {
			@Override
			public Integer doInSession(SqlSession executor) {
				return executor.update(statementName, parameterObject);
			}
		});
	}

	@Override
	public void update(String statementName, Object parameterObject, int requiredRowsAffected) throws DataAccessException {
		int actualRowsAffected = update(statementName, parameterObject);
		if (actualRowsAffected != requiredRowsAffected) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(statementName, requiredRowsAffected, actualRowsAffected);
		}
	}

	@Override
	public int delete(String statementName) throws DataAccessException {
		return delete(statementName, null);
	}

	@Override
	public int delete(final String statementName, final Object parameterObject) throws DataAccessException {
		return execute(new SqlMapCallback<Integer>() {
			@Override
			public Integer doInSession(SqlSession executor) {
				return executor.delete(statementName, parameterObject);
			}
		});
	}

	@Override
	public void delete(String statementName, Object parameterObject, int requiredRowsAffected) throws DataAccessException {
		int actualRowsAffected = delete(statementName, parameterObject);
		if (actualRowsAffected != requiredRowsAffected) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(statementName, requiredRowsAffected, actualRowsAffected);
		}
	}

	interface SqlMapCallback<T> {

		T doInSession(SqlSession session);

	}

}
