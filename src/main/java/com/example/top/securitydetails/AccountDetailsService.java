package com.example.top.securitydetails;

import com.example.top.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = repository.findAccountByUsername(username);

        if (account == null) throw new UsernameNotFoundException("Could not find user");

        return new AccountDetails(account);
    }
}
