package com.jari.jari.common.auth;

import com.jari.jari.account.entity.Account;
import com.jari.jari.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jari.jari.common.auth.UnknownAccount.guestAuth;
import static com.jari.jari.common.config.ServerConfigure.ACCOUNT_URL;
import static com.jari.jari.common.config.ServerConfigure.ARTICLE_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    public static final String AUTH_KEY = "Authentication";

    private final AccountService accountService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String authInfo = request.getHeader(AUTH_KEY);

        //@@todo npe
        if ((authInfo != null) && guestAccessDenyCondition(method, uri)) {


            Account account = accountService.authInfoParser(authInfo);

            if (account.equals(guestAuth())) {
                log.info("보안문제로 차단된 api");
                //
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("UNAUTHORIZED API Call");
                return false;
            }
        }
        return true;
    }


    private boolean guestAccessDenyCondition(String method, String uri) {
        return ((!method.equals("GET")) && uri.startsWith(ARTICLE_URL)) || //GET을 제외한 모든 article 관련 API
                (uri.startsWith(ACCOUNT_URL)); //모든 account 관련 API
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
