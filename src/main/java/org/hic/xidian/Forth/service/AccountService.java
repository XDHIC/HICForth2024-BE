package org.hic.xidian.Forth.service;

import org.hic.xidian.Forth.config.RabbitConfig;
import org.hic.xidian.Forth.domain.dto.Account;
import org.hic.xidian.Forth.domain.vo.ConfirmRegisterVO;
import org.hic.xidian.Forth.repository.AccountRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AccountService {
    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AccountRepository accountRepository;

//    @Autowired
//    private PasswordEncoder encoder;

    @Value("${spring.redis.key-prefix}")
    private String keyPrefix;

    // 生成验证码并发送到邮箱
    public String registerEmailVerifyCode(String email, String ip) {
        // 检查是否请求频繁
        String limitKey = keyPrefix + "verify_email_limit:" + ip;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(limitKey))) {
            return "Request too frequent.";
        }

        // 检查用户名和邮箱是否已经存在
        if (accountRepository.existsByEmail(email)) {
            return "Email has been registered.";
        }

        // 生成验证码
        Random random = new Random();
//        int verifyCode = random.nextInt(899999) + 100000;
        int verifyCode = 666666;
        Map<String, Object> data = Map.of("email", email, "code", verifyCode);

        // 发送验证码到消息队列
        amqpTemplate.convertAndSend(rabbitConfig.getEmailExchangeName(), rabbitConfig.getEmailRoutingKey(), data);

        // 将验证码存储到Redis，并设置有效期
        String verifyKey = keyPrefix + "verify_email_code:" + email;
        stringRedisTemplate.opsForValue().set(verifyKey, String.valueOf(verifyCode), 5, TimeUnit.MINUTES);
//        stringRedisTemplate.opsForValue().set(limitKey, "1", 1, TimeUnit.MINUTES);

        return null;
    }

    // 验证验证码并注册账户
    public String registerEmailAccount(ConfirmRegisterVO vo) {
        String email = vo.getEmail();
        String key = keyPrefix + "verify_email_code:" + email;

        // 验证验证码
        String code = stringRedisTemplate.opsForValue().get(key);
        if (code == null) {
            return "Code has expired.";
        } else if (!code.equals(vo.getCode())) {
            return "Invalid code.";
        }

        // 创建新账户
        Account account = new Account();
        account.setPassword(vo.getPassword());
        account.setEmail(email);
        account.setRegisterDate(new Date());
        accountRepository.save(account);

        // 删除Redis中的验证码
        stringRedisTemplate.delete(key);

        return null;
    }
}
