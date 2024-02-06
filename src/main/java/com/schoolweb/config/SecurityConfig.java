package com.schoolweb.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger logger= LoggerFactory.getLogger(SecurityConfig.class);
    @Bean
    public UserDetailsService users() {
        return new userLoginService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        try {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(users());
            authenticationProvider.setPasswordEncoder(passwordEncoder());
            return authenticationProvider;
        } catch (Exception e) {
            logger.error("Error creating AuthenticationProvider: " + e.getMessage());
            throw new RuntimeException("Failed to create AuthenticationProvider");
        }
    }

    // with custom thymeleaf form authentication.
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz.requestMatchers("/login", "/register" ,"/css/**","/js/**").permitAll()
                        .requestMatchers("/student/**").hasAuthority("student")
                        .requestMatchers("/staff/**").hasAuthority("staff")
                        .requestMatchers("/admin/**").hasAuthority("admin")
                                .anyRequest().authenticated())
                .formLogin((formLogin) -> formLogin.loginPage("/login").loginProcessingUrl("/login")
                        .successHandler(new RoleBasedAuthHandler()).permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login").permitAll())
                .authenticationProvider(authenticationProvider())
                .build();
    }
}
