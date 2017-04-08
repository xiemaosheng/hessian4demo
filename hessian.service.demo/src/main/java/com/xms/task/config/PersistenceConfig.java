package com.xms.task.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.sql.SQLException;

@Configuration
@PropertySource(value = {"classpath:mysql.properties"})
@EnableTransactionManagement(proxyTargetClass = true)
public class PersistenceConfig {

    @Resource
    private Environment env;

    @Bean(name = "taskTransactionManager")
    public PlatformTransactionManager taskManager() {
        return new DataSourceTransactionManager(dataSourceTask());
    }

    @Bean(name = "logTransactionManager")
    public PlatformTransactionManager logManager() {
        return new DataSourceTransactionManager(dataSourceLog());
    }

    @Bean(destroyMethod = "close", initMethod = "init")
    public DruidDataSource dataSourceTask() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("druid.driverClassName").trim());
        dataSource.setInitialSize(Integer.parseInt(env.getProperty("druid.initialSize").trim()));
        dataSource.setMinIdle(Integer.parseInt(env.getProperty("druid.minIdle").trim()));
        dataSource.setMaxActive(Integer.parseInt(env.getProperty("druid.maxActive").trim()));
        dataSource.setMaxWait(Long.parseLong(env.getProperty("druid.maxWait").trim()));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getProperty(
                "druid.timeBetweenEvictionRunsMillis").trim()));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("druid.minEvictableIdleTimeMillis")
                .trim()));
        dataSource.setValidationQuery(env.getProperty("druid.validationQuery").trim());
        dataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("druid.testWhileIdle").trim()));
        dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("druid.testOnBorrow").trim()));
        dataSource.setTestOnReturn(Boolean.valueOf((env.getProperty("druid.testOnReturn").trim())));
        dataSource.setPoolPreparedStatements(Boolean.valueOf(env.getProperty("druid.poolPreparedStatements").trim()));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(env.getProperty(
                "druid.maxPoolPreparedStatementPerConnectionSize").trim()));
        dataSource.setUrl(env.getProperty("druid.url").trim());
        dataSource.setUsername(env.getProperty("druid.user").trim());
        dataSource.setPassword(env.getProperty("druid.password").trim());

        try {
            dataSource.setFilters(env.getProperty("druid.filters"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }

    @Bean(destroyMethod = "close", initMethod = "init")
    public DruidDataSource dataSourceLog() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("druid.driverClassName").trim());
        dataSource.setInitialSize(Integer.parseInt(env.getProperty("druid.initialSize").trim()));
        dataSource.setMinIdle(Integer.parseInt(env.getProperty("druid.minIdle").trim()));
        dataSource.setMaxActive(Integer.parseInt(env.getProperty("druid.maxActive").trim()));
        dataSource.setMaxWait(Long.parseLong(env.getProperty("druid.maxWait").trim()));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getProperty(
                "druid.timeBetweenEvictionRunsMillis").trim()));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("druid.minEvictableIdleTimeMillis")
                .trim()));
        dataSource.setValidationQuery(env.getProperty("druid.validationQuery").trim());
        dataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("druid.testWhileIdle").trim()));
        dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("druid.testOnBorrow").trim()));
        dataSource.setTestOnReturn(Boolean.valueOf((env.getProperty("druid.testOnReturn").trim())));
        dataSource.setPoolPreparedStatements(Boolean.valueOf(env.getProperty("druid.poolPreparedStatements").trim()));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(env.getProperty(
                "druid.maxPoolPreparedStatementPerConnectionSize").trim()));
        dataSource.setUrl(env.getProperty("log.druid.url").trim());
        dataSource.setUsername(env.getProperty("log.druid.user").trim());
        dataSource.setPassword(env.getProperty("log.druid.password").trim());

        try {
            dataSource.setFilters(env.getProperty("druid.filters"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }

    @Bean
    public SqlSessionFactory basicSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceTask());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver
                .getResources("classpath:com/nd/task/repository/mysql/mapper/task/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionFactory logSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceLog());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver
                .getResources("classpath:com/nd/task/repository/mysql/mapper/log/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

}
