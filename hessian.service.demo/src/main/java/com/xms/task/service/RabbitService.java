package com.xms.task.service;

import com.alibaba.fastjson.JSON;

import com.xms.task.domain.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * Created by Joseph on 2017/3/3 0003.
 */
@Component
public class RabbitService {
    @Resource(name = "directTemplate")
    private AmqpTemplate directTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void sendMessage(String routingKey, Object message) {
        directTemplate.convertAndSend(routingKey, message);
    }

    public void sendMessage(String route, String receiptHandle, Object body) {
        if (null != body) {
            EventMessage eventMessage = new EventMessage();
            eventMessage.setBody(JSON.toJSONString(body).getBytes(StandardCharsets.UTF_8));
            eventMessage.setReceiptHandle(receiptHandle);
            try {
                sendMessage(route, eventMessage);
            } catch (Throwable e) {
                logger.error("Send rabbit fail! Message body: {}, Exception:{}", eventMessage.toString(), e);
                throw e;
            }
            logger.debug("Send rabbit success! Message body: {}", eventMessage.toString());
        }
    }
}
