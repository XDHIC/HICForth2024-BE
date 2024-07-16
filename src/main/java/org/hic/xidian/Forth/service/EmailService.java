package org.hic.xidian.Forth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(String to, String subject, String text) {
        // 这里应该实现真实的邮件发送逻辑，例如使用JavaMailSender
        logger.info("Sending email to: {}, Subject: {}, Body: {}", to, subject, text);
    }
}
