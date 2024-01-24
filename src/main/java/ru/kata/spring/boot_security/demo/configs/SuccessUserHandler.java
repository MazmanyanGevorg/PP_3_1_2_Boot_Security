package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    /*
    Этот класс позволяет настроить действия, которые должны выполняться при
    успешной аутентификации пользователя с использованием Spring Security.
    В данном случае, если пользователь имеет роль "ROLE_USER",
    он будет перенаправлен на определенный URL "/user",
    в противном случае он будет перенаправлен на корневой URL "/".
     */
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        // authentication.getAuthorities() - получает список ролей пользователя,
        // представленных в виде объектов GrantedAuthority
        //   - AuthorityUtils.authorityListToSet(authentication.getAuthorities()) - преобразует
        //   список ролей в набор (Set) строковых значений

        if (roles.contains("ROLE_USER")) { //  - roles.contains("ROLE_USER") - проверяет, содержит ли набор роль "ROLEUSER".
            httpServletResponse.sendRedirect("/user"); // если у пользователя есть роль "ROLEUSER", перенаправляет его на URL "/user"
        }else if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else {
            httpServletResponse.sendRedirect("/"); // - если у пользователя нет роли "ROLEUSER", перенаправляет его на корневой URL "/"
        }
    }
}