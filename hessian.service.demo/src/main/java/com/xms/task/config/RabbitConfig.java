package com.xms.task.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Joseph on 2017/3/2 0002.
 */
@Configuration
@PropertySource(value = {"classpath:rabbit.properties"})
public class RabbitConfig {
    @Value("${rabbit.host}")
    private String rabbitHost;
    @Value("${rabbit.vhost}")
    private String rabbitVhost;
    @Value("${rabbit.username}")
    private String rabbitUsername;
    @Value("${rabbit.password}")
    private String rabbitPassword;
    @Value("${rabbit.port}")
    private String rabbitPort;

    @Bean
    public ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory rabbitFactory = new com.rabbitmq.client.ConnectionFactory();
        rabbitFactory.setHost(rabbitHost);
        rabbitFactory.setVirtualHost(rabbitVhost);
        rabbitFactory.setUsername(rabbitUsername);
        rabbitFactory.setPassword(rabbitPassword);
        rabbitFactory.setPort(Integer.parseInt(rabbitPort));
        return new CachingConnectionFactory(rabbitFactory);
    }

    @Bean
    public RabbitAdmin admin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate directTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange("pbl-direct-exchange");
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean(name = "directExchange")
    DirectExchange directExchange() {
        return new DirectExchange("pbl-direct-exchange", true, false);
    }

    @Bean
    public List<Queue> queues() {
        return Arrays.asList(
                new Queue("gainRewardQ", true, false, false),
                new Queue("imQ", true, false, false)
        );
    }

    @Bean
    public List<Binding> bindings() {
        return Arrays.asList(
                new Binding("gainRewardQ", Binding.DestinationType.QUEUE, "pbl-direct-exchange", "msg2gainReward", null),
                new Binding("imQ", Binding.DestinationType.QUEUE, "pbl-direct-exchange", "msg2im", null)
                );
    }
}
