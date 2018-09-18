package com.hydra.core.db.sqlmap.impl;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.util.Assert;

import com.hydra.core.db.sqlmap.SqlMapSession;
import com.hydra.core.db.sqlmap.support.SqlSessionUtils;

public class SqlMapSessionImpl implements SqlMapSession {
    private SqlSession                     sqlSession;

    private PersistenceExceptionTranslator exceptionTranslator;

    private SqlSessionFactory              sqlMapClient;

    SqlMapSessionImpl(SqlSession session, SqlSessionFactory sqlMapClient,
                      PersistenceExceptionTranslator exceptionTranslator) {
        this.sqlSession = session;
        this.exceptionTranslator = exceptionTranslator;
        this.sqlMapClient = sqlMapClient;
    }

    private <T> T execute(SqlMapSessionCallback<T> action) throws DataAccessException {
        Assert.notNull(action, "Callback object must not be null");

        // Execute given callback...
        try {
            return action.doInSession(sqlSession);
        } catch (Throwable ex) {
            Throwable unwrapped = ExceptionUtil.unwrapThrowable(ex);
            if (this.exceptionTranslator != null && unwrapped instanceof PersistenceException) {
                unwrapped = this.exceptionTranslator.translateExceptionIfPossible((PersistenceException) unwrapped);
            }
            throw (DataAccessException) unwrapped;
        }
        // Processing finished - potentially session still to be closed.
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T queryForObject(String statementName) throws DataAccessException {
        return (T) queryForObject(statementName, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T queryForObject(final String statementName, final Object parameterObject) throws DataAccessException {
        return (T) execute(new SqlMapSessionCallback<Object>() {
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
    public <T> List<T> queryForList(final String statementName, final Object parameterObject)
            throws DataAccessException {
        return execute(new SqlMapSessionCallback<List<T>>() {
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
                                    final int maxResults)
            throws DataAccessException {
        return execute(new SqlMapSessionCallback<List<T>>() {
            @Override
            public List<T> doInSession(SqlSession executor) {
                return executor.selectList(statementName, parameterObject, new RowBounds(skipResults, maxResults));
            }
        });
    }

    @Override
    public void queryWithRowHandler(final String statementName, final Object parameterObject,
                                    final ResultHandler rowHandler)
            throws DataAccessException {
        execute(new SqlMapSessionCallback<Object>() {
            @Override
            public Object doInSession(SqlSession executor) {
                executor.select(statementName, parameterObject, rowHandler);
                return null;
            }
        });
    }

    @Override
    public void queryWithRowHandler(final String statementName, final Object parameterObject, final int skipResults,
                                    final int maxResults, final ResultHandler rowHandler)
            throws DataAccessException {
        execute(new SqlMapSessionCallback<Object>() {
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
        return execute(new SqlMapSessionCallback<Integer>() {
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
        return execute(new SqlMapSessionCallback<Integer>() {
            @Override
            public Integer doInSession(SqlSession executor) {
                return executor.update(statementName, parameterObject);
            }
        });
    }

    @Override
    public void update(String statementName, Object parameterObject, int requiredRowsAffected)
            throws DataAccessException {
        int actualRowsAffected = update(statementName, parameterObject);
        if (actualRowsAffected != requiredRowsAffected) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(statementName, requiredRowsAffected,
                    actualRowsAffected);
        }
    }

    @Override
    public int delete(String statementName) throws DataAccessException {
        return delete(statementName, null);
    }

    @Override
    public int delete(final String statementName, final Object parameterObject) throws DataAccessException {
        return execute(new SqlMapSessionCallback<Integer>() {
            @Override
            public Integer doInSession(SqlSession executor) {
                return executor.delete(statementName, parameterObject);
            }
        });
    }

    @Override
    public void delete(String statementName, Object parameterObject, int requiredRowsAffected)
            throws DataAccessException {
        int actualRowsAffected = delete(statementName, parameterObject);
        if (actualRowsAffected != requiredRowsAffected) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(statementName, requiredRowsAffected,
                    actualRowsAffected);
        }
    }

    interface SqlMapSessionCallback<T> {

        T doInSession(SqlSession session);

    }

    @Override
    public void commit() throws DataAccessException {
        execute(new SqlMapSessionCallback<Object>() {
            @Override
            public Object doInSession(SqlSession executor) {
                executor.commit();
                return null;
            }
        });
    }

    @Override
    public void rollback() throws DataAccessException {
        execute(new SqlMapSessionCallback<Object>() {
            @Override
            public Object doInSession(SqlSession executor) {
                executor.rollback();
                return null;
            }
        });
    }

    @Override
    public void close() throws DataAccessException {
        SqlSessionUtils.closeSqlSession(this.sqlSession, this.sqlMapClient);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return this.sqlSession.getMapper(type);
    }

}
