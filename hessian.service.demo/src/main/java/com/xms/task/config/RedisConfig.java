package com.xms.task.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * Created by Joseph on 2017/3/21 0021.
 */
@Configuration
@PropertySource(value = {"classpath:redis.properties"})
@EnableTransactionManagement(proxyTargetClass = true)
public class RedisConfig {
    @Resource
    private Environment env;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.valueOf(env.getProperty(getPrefix() + "redis.pool.maxActive").trim()));
        jedisPoolConfig.setMaxIdle(Integer.valueOf(env.getProperty(getPrefix() + "redis.pool.maxIdle").trim()));
        jedisPoolConfig.setMinIdle(Integer.valueOf(env.getProperty(getPrefix() + "redis.pool.minIdle").trim()));
        jedisPoolConfig
                .setMaxWaitMillis(Long.valueOf(env.getProperty(getPrefix() + "redis.pool.maxWaitMillis").trim()));
        jedisPoolConfig.setTestOnBorrow(Boolean
                .valueOf(env.getProperty(getPrefix() + "redis.pool.testOnBorrow").trim()));
        jedisPoolConfig.setTestOnReturn(Boolean
                .valueOf(env.getProperty(getPrefix() + "redis.pool.testOnReturn").trim()));
        jedisPoolConfig.setTestWhileIdle(Boolean.valueOf(env.getProperty(getPrefix() + "redis.pool.testWhileIdle")
                .trim()));
        jedisPoolConfig.setBlockWhenExhausted(Boolean.valueOf(env.getProperty(
                getPrefix() + "redis.pool.blockWhenExhausted").trim()));
        jedisPoolConfig.setEvictionPolicyClassName(env.getProperty(getPrefix() + "redis.pool.evictionPolicyClassName")
                .trim());
        jedisPoolConfig.setLifo(Boolean.valueOf(env.getProperty(getPrefix() + "redis.pool.lifo").trim()));
        jedisPoolConfig.setNumTestsPerEvictionRun(Integer.valueOf(env.getProperty(
                getPrefix() + "redis.pool.numTestsPerEvictionRun").trim()));
        jedisPoolConfig.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty(
                getPrefix() + "redis.pool.minEvictableIdleTimeMillis").trim()));
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty(
                getPrefix() + "redis.pool.timeBetweenEvictionRunsMillis").trim()));
        jedisPoolConfig.setTestWhileIdle(Boolean.valueOf(env.getProperty(getPrefix() + "redis.pool.testWhileIdle")
                .trim()));
        return jedisPoolConfig;
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool() {
        final String host = env.getProperty(getPrefix() + "redis.host").trim();
        final int port = Integer.parseInt(env.getProperty(getPrefix() + "redis.port").trim());
        final int timeout = Integer.parseInt(env.getProperty(getPrefix() + "redis.timeout").trim());
        final String password = StringUtils.isEmpty(env.getProperty(getPrefix() + "redis.password")) ? null : env
                .getProperty(getPrefix() + "redis.password").trim();
        final int database = Integer.parseInt(env.getProperty(getPrefix() + "redis.database").trim());

        return new JedisPool(jedisPoolConfig(), host, port, timeout, password, database);
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisOrderPool() {
        // used to store the business server's order no when updating user task schedule
        final String host = env.getProperty(getPrefix() + "redis.host").trim();
        final int port = Integer.parseInt(env.getProperty(getPrefix() + "redis.port").trim());
        final int timeout = Integer.parseInt(env.getProperty(getPrefix() + "redis.timeout").trim());
        final String password = StringUtils.isEmpty(env.getProperty(getPrefix() + "redis.password")) ? null : env
                .getProperty(getPrefix() + "redis.password").trim();
        final int database = Integer.parseInt(env.getProperty(getPrefix() + "redis.order.database").trim());

        return new JedisPool(jedisPoolConfig(), host, port, timeout, password, database);
    }

    public String getPrefix() {
        return "";
    }

}
