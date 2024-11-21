package com.tourbooking.service;

import com.tourbooking.model.Account;
import com.tourbooking.repository.AccountRepository;
import com.tourbooking.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String account_name) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountName(account_name);
        if (account == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản");
        }
        return new CustomUserDetails(account);
    }
}
