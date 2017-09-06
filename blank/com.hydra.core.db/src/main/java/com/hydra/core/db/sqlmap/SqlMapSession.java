package com.hydra.core.db.sqlmap;
import java.util.List;

import org.apache.ibatis.session.ResultHandler;
import org.springframework.dao.DataAccessException;

public interface SqlMapSession {

	<T> T queryForObject(String statementName) throws DataAccessException;

	<T> T queryForObject(String statementName, Object parameterObject) throws DataAccessException;

	<T> List<T> queryForList(String statementName) throws DataAccessException;

	<T> List<T> queryForList(String statementName, Object parameterObject) throws DataAccessException;

	<T> List<T> queryForList(String statementName, Object parameterObject, int skipResults, int maxResults)
			throws DataAccessException;

	void queryWithRowHandler(String statementName, Object parameterObject, ResultHandler rowHandler)
			throws DataAccessException;

	void queryWithRowHandler(String statementName, Object parameterObject, int skipResults, int maxResults,
			ResultHandler rowHandler) throws DataAccessException;

	int insert(String statementName) throws DataAccessException;

	int insert(String statementName, Object parameterObject) throws DataAccessException;

	int update(String statementName) throws DataAccessException;

	int update(String statementName, Object parameterObject) throws DataAccessException;

	void update(String statementName, Object parameterObject, int requiredRowsAffected) throws DataAccessException;

	int delete(String statementName) throws DataAccessException;

	int delete(String statementName, Object parameterObject) throws DataAccessException;

	void delete(String statementName, Object parameterObject, int requiredRowsAffected) throws DataAccessException;
	
	<T> T getMapper(Class<T> type);
	
	void close() throws DataAccessException;
	
	void commit() throws DataAccessException;
	
	void rollback() throws DataAccessException;
}
