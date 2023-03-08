package com.example.top.repository;

import com.example.top.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query (value = "update User u set u.password = :newPassword where u.username = :username")
    int updatePasswordByUsername(String username, String newPassword);
}
