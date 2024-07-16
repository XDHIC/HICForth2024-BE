package org.hic.xidian.Forth.repository;

import org.hic.xidian.Forth.domain.dto.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    // 根据邮箱查找账号
    Optional<Account> findByEmail(String email);
    
    // 检查是否存在某个邮箱
    boolean existsByEmail(String email);
}
