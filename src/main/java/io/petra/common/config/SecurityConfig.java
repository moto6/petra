package io.petra.common.config;

import io.petra.account.domain.AccountRole;
import io.petra.auth.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    private final String[] authOnly = new String[]{};
    private static final String[] availableUriArray = new String[]{
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
            "/myLogin",
            "/api/users/**",
            "/stomp/**",
            "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
            "/api-docs/json/swagger-config", "/api-docs/**",
            "/docs"
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
                .antMatchers(availableUriArray).permitAll()
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


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/images/**",
                        "/"
                );
    }

}
