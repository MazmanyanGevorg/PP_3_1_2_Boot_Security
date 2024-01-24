package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("user");
    }
}
/*
   - registry.addViewController("/user") - определяет URL-путь "/user".
   - .setViewName("user") - указывает на имя представления (view) "user".
   Когда пользователь обращается к URL-пути "/user",
   будет отображено представление с именем "user".
 */