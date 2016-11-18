package com.akhalikov.backend.hibernate5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.inject.Named;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
class DataSourceContextConfig {
  @Bean
  @Named("databaseProperties")
  Properties databaseProperties() {
    Properties properties = new Properties();
    properties.put("user", "sa");
    properties.put("password", "");
    properties.put("sql.enforce_tdc_delete", "false");
    properties.put("sql.enforce_tdc_update", "false");
    properties.put("sql.syntax_pgs", "true");
    return properties;
  }

  @Bean
  DataSource dataSource(final Properties databaseProperties) {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setDriverClass(org.hsqldb.jdbc.JDBCDriver.class);
    dataSource.setUrl("jdbc:hsqldb:mem");
    dataSource.setConnectionProperties(databaseProperties);
    return dataSource;
  }

  @Bean
  JdbcTemplate jdbcTemplate(final DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
