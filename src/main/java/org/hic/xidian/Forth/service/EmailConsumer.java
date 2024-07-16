package org.hic.xidian.Forth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class EmailConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    // 假设邮件服务已定义（这里需要您实现具体的邮件发送逻辑）
    @Autowired
    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${rabbitmq.email.queue}")
    public void receiveMessage(Map<String, Object> message) {
        logger.info("Received message from RabbitMQ: {}", message);
        try {
            String email = (String) message.get("email");
            Integer code = (Integer) message.get("code");

            // 调用邮件服务发送邮件
            emailService.sendEmail(email, "Your verification code", "Your verification code is: " + code);

            logger.info("Email sent successfully to {}", email);
        } catch (Exception e) {
            logger.error("Error while processing message", e);
        }
    }
}
