package com.estsoft.blogjpa.config;

import com.estsoft.blogjpa.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

//@Configuration
//public class WebSecurityConfig {
//    private UserDetailService userDetailService;
//
//    public WebSecurityConfig(UserDetailService userDetailService) {
//        this.userDetailService = userDetailService;
//    }
//
//    @Bean
//    public WebSecurityCustomizer configure() { // 1) 스프링 시큐리티 기능 비활성화
//        return web -> web.ignoring().requestMatchers(toH2Console())
//                .requestMatchers("/static/**");
//    }
//
//    // 2) 특정 HTTP 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.authorizeHttpRequests() // 3) 인증, 인가 설정
//                .requestMatchers("/login", "/signup", "/user").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin() // 4) 폼 기반 로그인 설정
//                .loginPage("/login")
//                .defaultSuccessUrl("/articles")
//                .and()
//                .logout() // 5) 로그아웃 설정
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true)
//                .and()
//                .csrf().disable() // 6) csrf 비활성화
//                .build();
//    }
//
//    //7 ) 인증관리자 관련 설정
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
//        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailService)
//                .passwordEncoder(bCryptPasswordEncoder)
//                .and()
//                .build();
//    }
//
//    // 9) 패스워드 인코더로 사용할 빈 등록
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer configure() {      // 스프링 시큐리티 기능 비활성화
        return web -> web.ignoring().requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/login", "/signup", "/user").permitAll()
                                .anyRequest().authenticated())
                .formLogin(auth -> auth.loginPage("/login")
                        .defaultSuccessUrl("/articles"))
                .logout(auth -> auth.logoutSuccessUrl("/login")
                        .invalidateHttpSession(true))
                .csrf(auth -> auth.disable());
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}