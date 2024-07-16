package org.hic.xidian.Forth.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Annotation:
 * 注解 @Document 表示链接 MongoDB 中的 "account" 集合
 * 注解 @Data 和 @NoArgsConstructor 来自 Lombok，用于生成 getter、setter 和无参构造函数
 *
 * Fields:
 * - @Id: 主键字段，MongoDB 中的主键通常为字符串类型
 * - password: 密码
 * - email: 邮箱地址
 * - phone: 电话号码
 * - major: 专业
 * - role: 用户角色，使用整数表示不同的角色类型
 * - gender: 性别，使用整数表示性别（例如：1 表示男性，0 表示女性）
 * - registerDate: 注册日期
 */
@Data
@NoArgsConstructor
@Document(collection = "account")
public class Account {
    @Id private String id;

    private String password;
    private String email;

//    private String phone;
//    private String major;
//    private Integer role;
//    private Integer gender;

    private Date registerDate;

}
