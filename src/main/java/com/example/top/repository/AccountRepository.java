package com.example.top.repository;

import com.example.top.entity.employee.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByUsername(String username);
    List<Account> findByUsername(String username);
}
