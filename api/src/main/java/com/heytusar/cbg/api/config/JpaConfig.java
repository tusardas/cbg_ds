package com.heytusar.cbg.api.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class JpaConfig {
	@Value("${spring.datasource.diverClass}")
	private String driverClass;
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.hikari.connection-timeout}")
	private String connectionTimeout;
	
	@Value("${spring.datasource.hikari.minimum-idle}")
	private String minimumIdle;
	
	@Value("${spring.datasource.hikari.maximum-pool-size}")
	private String maximumPoolSize;
	
	@Value("${spring.datasource.hikari.idle-timeout}")
	private String idleTimeout;

	@Value("${spring.datasource.hikari.max-lifetime}")
	private String maxLifetime;
	
	@Value("${spring.jpa.properties.hibernate.dialect}")
	private String dialect;
	
	@Value("${spring.jpa.properties.hibernate.id.new_generator_mapping}")
	private String new_generator_mapping;
	
	@Value("${spring.jpa.properties.hibernate.format_sql}")
	private String format_sql;
	
	
	@Value("${spring.jpa.properties.hibernate.hbm2ddl.auto}")
	private String auto;
	
	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	
	public String getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(String connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public String getMinimumIdle() {
		return minimumIdle;
	}

	public void setMinimumIdle(String minimumIdle) {
		this.minimumIdle = minimumIdle;
	}

	public String getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(String maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public String getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(String idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public String getMaxLifetime() {
		return maxLifetime;
	}

	public void setMaxLifetime(String maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	public String getNew_generator_mapping() {
		return new_generator_mapping;
	}

	public void setNew_generator_mapping(String new_generator_mapping) {
		this.new_generator_mapping = new_generator_mapping;
	}

	public String getFormat_sql() {
		return format_sql;
	}

	public void setFormat_sql(String format_sql) {
		this.format_sql = format_sql;
	}

	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] {"com.heytusar.cbg.core.models"});

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalJpaProperties());

		return em;
	}
	
	@Bean
	public DataSource dataSource(){
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(driverClass);
	    dataSource.setUrl(url);
	    dataSource.setUsername(username);
	    dataSource.setPassword(password);
	    dataSource.setConnectionProperties(additionalConnectionProperties());
	    return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
	 
	    return transactionManager;
	}
	 
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	    return new PersistenceExceptionTranslationPostProcessor();
	}
	
	Properties additionalConnectionProperties() {
		Properties properties = new Properties();
	    properties.setProperty("hikari.connection-timeout", connectionTimeout);
	    properties.setProperty("hikari.minimum-idle", minimumIdle);
	    properties.setProperty("hikari.maximum-pool-size", maximumPoolSize);
	    properties.setProperty("hikari.idle-timeout", idleTimeout);
	    properties.setProperty("hikari.max-lifetime", maxLifetime);
		return properties;
	}
	
	Properties additionalJpaProperties() {
	    Properties properties = new Properties();
	    properties.setProperty("hibernate.hbm2ddl.auto", auto);
	    properties.setProperty("hibernate.dialect", dialect);
	    properties.setProperty("hibernate.id.new_generator_mapping", new_generator_mapping);
	    properties.setProperty("hibernate.format_sql", format_sql);
	    return properties;
	}
	
	//To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
