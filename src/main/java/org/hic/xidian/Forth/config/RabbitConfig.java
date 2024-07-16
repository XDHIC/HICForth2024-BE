package org.hic.xidian.Forth.config;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Getter
@Configuration
public class RabbitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Value("${rabbitmq.email.queue}")
    private String emailQueueName;

    @Value("${rabbitmq.email.exchange}")
    private String emailExchangeName;

    @Value("${rabbitmq.email.routingkey}")
    private String emailRoutingKey;

    /**
     * 定义邮件队列
     */
    @Bean
    Queue emailQueue() {
        return new Queue(emailQueueName, true);
    }

    /**
     * 定义邮件交换机
     */
    @Bean
    DirectExchange emailExchange() {
        return new DirectExchange(emailExchangeName);
    }

    /**
     * 绑定队列和交换机，指定路由键
     */
    @Bean
    Binding emailBinding(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(emailRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
