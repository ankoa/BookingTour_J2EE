package com.tourbooking.service;

import com.tourbooking.model.Account;
import com.tourbooking.repository.AccountRepository;
import com.tourbooking.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuthService extends DefaultOAuth2UserService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // Lấy thông tin từ OAuth2 (ví dụ: email)
        String email = oAuth2User.getAttribute("email");

        // Tìm hoặc tạo mới tài khoản trong cơ sở dữ liệu
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            account = new Account();
            account.setEmail(email);
            account.setAccountName(email.split("@")[0]);
            account.setRole("USER");
            account.setStatus(1); // Mặc định là kích hoạt
            accountRepository.save(account);
        }

        // Trả về CustomUserDetails hoặc CustomOAuth2User
        return new CustomUserDetails(account, oAuth2User.getAttributes());
    }
}
