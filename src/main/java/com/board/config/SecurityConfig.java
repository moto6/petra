package com.board.config;

import com.board.auth.CustomOAuth2UserService;
import com.board.accounts.entity.AccountRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    private final String[] authOnly = new String[]{};
    private static final String[] permitAll = new String[]{
            "/",
            "/css/**",
            "/images/**",
            "/js/**",
            "/h2-console/**",
            "/profile",
            "/post/**",
            "/users/login",
            "/users/login/error",
            "/users/logout",
            "/error",
            "/myLogin"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //H2 콘솔 접근을 위해서
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                //
                .and()
                .authorizeRequests()
                //permit
                .antMatchers(permitAll)
                .permitAll()
                //auth need
                .antMatchers(authOnly)
                .hasAnyRole(AccountRole.USER.name(), AccountRole.ADMIN.name())
                .anyRequest()
                .authenticated()
                //로그아웃시 리디렉션 위치
                .and()
                .logout()
                .logoutSuccessUrl("/")
                //로그인시 설정 진입점
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);//UserService 인터페이스 구현체
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
