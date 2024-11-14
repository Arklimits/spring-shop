package com.arklimits.shop.config;

import com.arklimits.shop.filter.ApiJwtFilter;
import com.arklimits.shop.filter.AuthJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApiJwtFilter apiJwtFilter;
    private final AuthJwtFilter authJwtFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.csrfTokenRepository(csrfTokenRepository()))
            .sessionManagement(
                (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .addFilterBefore(apiJwtFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(authJwtFilter, ExceptionTranslationFilter.class)

            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
            .formLogin((formLogin) -> formLogin.loginPage("/login").defaultSuccessUrl("/"))
            .logout(
                logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").deleteCookies("jwt"));
        return http.build();
    }
}