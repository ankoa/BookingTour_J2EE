package com.tourbooking.security;

import com.tourbooking.model.Account;
import com.tourbooking.model.Customer;
import com.tourbooking.service.AccountService;
import com.tourbooking.service.CustomerService;
import com.tourbooking.service.OAuthService;
import com.tourbooking.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/**").permitAll()
                )
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/account-login")   .failureUrl("/login?error")
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint
                                        .userService(oAuthService)
                        )
                        .successHandler(
                                (request, response,
                                 authentication) -> {
                                    var oidcUser = (DefaultOidcUser) authentication.getPrincipal();

                                    if (accountService.getAccountByEmail(oidcUser.getEmail()) == null) {
                                        Customer customer = new Customer();
                                        customer.setCustomerName(oidcUser.getFullName());
                                        customer.setTime(new Date());
                                        customer.setStatus(1);
                                        customerService.addCustomer(customer);
                                        Account account = new Account();
                                        account.setAccountName(oidcUser.getName());
                                        account.setEmail(oidcUser.getEmail());
                                        account.setPassword(new BCryptPasswordEncoder().encode(oidcUser.getName()));
                                        account.setRole("ROLE_USER");
                                        account.setCustomer(customer);
                                        account.setTime(LocalDateTime.now());
                                        account.setStatus(1);
                                        accountService.addAccount(account);
                                    };
                                    response.sendRedirect("/");
                                }
                        )
                )
                .formLogin(form -> form
                        .loginPage("/account-login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");

                            String role = authentication.getAuthorities().toArray()[0].toString();
                            response.getWriter().write("{\"status\": \"success\", \"role\": \"" + role + "\"}");
                            response.getWriter().flush();
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            String errorMessage = "";

                            if (exception instanceof BadCredentialsException) {
                                errorMessage = "Tài khoản hoặc mật khẩu không đúng.";
                            } else if (exception instanceof UsernameNotFoundException) {
                                errorMessage = "Tài khoản không tồn tại.";
                            } else if (exception instanceof LockedException) {
                                errorMessage = "Tài khoản đã bị khóa.";
                            }else{
                                errorMessage = "Đăng nhập thất bại";
                            }
                            response.getWriter().write("{\"status\": \"error\", \"message\": \"" + errorMessage + " \"}");
                            response.getWriter().flush();
                        })
                        .permitAll())
                .rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                        .userDetailsService(userDetailsService())
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/account-login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
