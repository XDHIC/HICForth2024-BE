package org.hic.xidian.Forth.domain.vo;


import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Data
@Component
public class EmailRegisterVO {
    @Email(message = "Invalid email address.")
    private String email;

    @Length(min = 6, max = 26, message = "Password length must be between 6 and 26.")
    private String password;
}
