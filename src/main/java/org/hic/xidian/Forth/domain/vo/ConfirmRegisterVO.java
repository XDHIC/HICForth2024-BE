package org.hic.xidian.Forth.domain.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Data
@Component
public class ConfirmRegisterVO {

    private EmailRegisterVO emailRegisterVO;

    @Length(min = 6, max = 6)
    String code;

    public String getEmail() {
        return emailRegisterVO.getEmail();
    }

    public String getPassword() {
        return emailRegisterVO.getPassword();
    }

    public void setEmail(String email) {
        emailRegisterVO.setEmail(email);
    }

    public void setPassword(String password) {
        emailRegisterVO.setPassword(password);
    }
}
