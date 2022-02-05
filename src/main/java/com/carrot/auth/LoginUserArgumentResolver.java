package com.carrot.auth;

import com.carrot.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import static com.carrot.auth.UnknownAccount.guestAuth;


@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTH_KEY = "Authentication";

    private final HttpServletRequest httpServletRequest;

    private final AccountService accountService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.getParameterAnnotation(AuthUser.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authValue = httpServletRequest.getHeader(AUTH_KEY);
        if (authValue != null) {
            return accountService.authInfoParser(authValue);
        }
        return guestAuth();
    }


}
