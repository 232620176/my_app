package com.hydra.blank.db.sqlmap.impl;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.util.ObjectUtils;

import com.hydra.blank.db.sqlmap.SqlMap;
import com.hydra.blank.db.sqlmap.support.MyBatisExceptionTranslator;
import com.hydra.blank.db.sqlmap.support.SpringManagedTransactionFactory;

public class SqlMapFactoryBean implements FactoryBean<SqlMap>, InitializingBean {

	private DataSource dataSource;

	private Resource configLocations;

	private Resource[] mappingLocations;

	private Properties sqlMapProperties;

	private String environment = SqlMapFactoryBean.class.getName();

	private String encoding = "UTF-8";

	private SqlSessionFactory sqlMapFactory;

	private TransactionFactory transactionFactory;

	private SqlMap sqlMap;

	private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
	
	private PersistenceExceptionTranslator exceptionTranslator;

	public void setConfigLocation(Resource configLocation) {
		this.configLocations = configLocation;
	}

	public void setMappingLocations(Resource[] mappingLocations) {
		this.mappingLocations = mappingLocations;
	}

	public void setProperties(Properties sqlMapClientProperties) {
		this.sqlMapProperties = sqlMapClientProperties;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.sqlMap = buildSqlMap(this.configLocations, this.mappingLocations, this.sqlMapProperties, this.environment);
	}

	protected SqlMap buildSqlMap(Resource configLocation, Resource[] mappingLocations, Properties properties, String environment)
			throws IOException {

		if (configLocation == null) {
			throw new IllegalArgumentException("'configLocation' is required");
		}
		Configuration configuration;

		try {
			Reader reader = new InputStreamReader(configLocation.getInputStream(), encoding);
			XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader, null, properties);
			configuration = xmlConfigBuilder.parse();
		} catch (Exception ex) {
			throw new NestedIOException("Failed to parse config resource: " + configLocation, ex);
		} finally {
			ErrorContext.instance().reset();
		}

		if (this.transactionFactory == null) {
			this.transactionFactory = new SpringManagedTransactionFactory();
		}

		Environment env = new Environment(this.environment, this.transactionFactory, this.dataSource);

		configuration.setEnvironment(env);

		if (!ObjectUtils.isEmpty(mappingLocations)) {
			for (Resource mapperLocation : mappingLocations) {
				if (mapperLocation == null) {
					continue;
				}

				// this block is a workaround for issue http://code.google.com/p/mybatis/issues/detail?id=235
				// when running MyBatis 3.0.4. Not needed in 3.0.5 and above.
				String path;
				if (mapperLocation instanceof ClassPathResource) {
					path = ((ClassPathResource) mapperLocation).getPath();
				} else {
					path = mapperLocation.toString();
				}

				try {
					Reader reader = new InputStreamReader(mapperLocation.getInputStream(), encoding);
					@SuppressWarnings("deprecation")
					XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(reader, configuration, path,
							configuration.getSqlFragments());
					xmlMapperBuilder.parse();
				} catch (Exception e) {
					throw new NestedIOException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
				} finally {
					ErrorContext.instance().reset();
				}

			}
		}

		this.sqlMapFactory = this.sqlSessionFactoryBuilder.build(configuration);
		
		if(this.exceptionTranslator == null) {
			this.exceptionTranslator = new MyBatisExceptionTranslator(
					this.sqlMapFactory.getConfiguration().getEnvironment().getDataSource(), true);
		}

		return new SqlMapImpl(this.dataSource, this.sqlMapFactory, this.exceptionTranslator);

	}

	@Override
	public SqlMap getObject() {
		return this.sqlMap;
	}

	@Override
	public Class<? extends SqlMap> getObjectType() {
		return (this.sqlMap != null ? this.sqlMap.getClass() : SqlMap.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
