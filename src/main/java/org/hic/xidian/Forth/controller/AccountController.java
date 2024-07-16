package org.hic.xidian.Forth.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hic.xidian.Forth.domain.vo.ConfirmRegisterVO;
import org.hic.xidian.Forth.domain.vo.EmailRegisterVO;
import org.hic.xidian.Forth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // 注册时发送验证码
    @PostMapping("/register")
    public ResponseEntity<String> registerEmailVerifyCode(@RequestBody EmailRegisterVO vo, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String response = accountService.registerEmailVerifyCode(vo.getEmail(), ip);
        if (response == null) {
            return new ResponseEntity<>("Verify code has sent.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 提交验证码并创建账户
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmEmailRegistration(@RequestBody ConfirmRegisterVO vo) {
        String response = accountService.registerEmailAccount(vo);
        if (response == null) {
            return new ResponseEntity<>("Register Successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
