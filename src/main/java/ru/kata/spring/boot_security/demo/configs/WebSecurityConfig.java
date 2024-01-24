package ru.kata.spring.boot_security.demo.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Configuration // можно убрать
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;

    @Lazy
    private final UserServiceImpl userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/registration").permitAll()
                .antMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .successHandler(successUserHandler) //настраивает форму входа в систему,
                // указывает successUserHandler в качестве обработчика успешной аутентификации
                .permitAll()// и разрешает доступ к форме входа всем пользователям
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    /*
    Этот @Bean-метод daoAuthenticationProvider() создает и возвращает экземпляр DaoAuthenticationProvider.
    DaoAuthenticationProvider является классом из библиотеки Spring Security и
    предоставляет аутентификацию на основе данных, полученных от UserDetailsService.

В данном методе устанавливаются две основные зависимости DaoAuthenticationProvider:

1. passwordEncoder(): Здесь устанавливается PasswordEncoder, который будет использоваться
для кодирования и проверки паролей пользователей при аутентификации.
2. userDetailsService: Здесь устанавливается UserDetailsService, который будет использоваться
для получения информации о пользователях при аутентификации.

В итоге, этот DaoAuthenticationProvider будет использоваться в конфигурации Spring Security
для обработки аутентификации пользователей.
     */

    //    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        return daoAuthenticationProvider;
//    }
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception { //  Этот метод настраивает правила
//        // безопасности для HTTP-запросов. Он использует объект HttpSecurity, чтобы определить,
//        // какие запросы требуют авторизации и какие требуют аутентификации
//        http
//                .authorizeRequests() // начало настройки правил авторизации
//                .antMatchers("/", "/index", "/new").permitAll() // разрешает доступ к корневому пути и пути "/index" без авторизации
//                // следующие три строки отвечают за авторизацию
//                .antMatchers("/users/**").authenticated()
//                .anyRequest().authenticated() // требует авторизацию для всех остальных запросов
//                .and()
//                .formLogin()
//                .loginPage("/new.html")
//                .successHandler(successUserHandler).permitAll() //настраивает форму входа в систему,
//                // указывает successUserHandler в качестве обработчика успешной аутентификации
//                // и разрешает доступ к форме входа всем пользователям
//                .and()
//                .logout().permitAll(); // настраивает выход из системы и разрешает доступ к нему всем пользователям
//    }

    // аутентификация inMemory //-> userDetailService

    //inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("{bcrypt}$2a$12$YeAuAkhNJeApas15uoN1uOkf4sfUr6s5tfOVgkxL6rva0ogtCNA9S")
//                        .roles("USER")
//                        .build();
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("{bcrypt}$2a$12$YeAuAkhNJeApas15uoN1uOkf4sfUr6s5tfOVgkxL6rva0ogtCNA9S")
//                        .roles("ADMIN", "USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    // jdbcAuthentication
//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//        UserDetails user =
//                User.builder()
//                        .username("user")
//                        .password("{bcrypt}$2a$12$YeAuAkhNJeApas15uoN1uOkf4sfUr6s5tfOVgkxL6rva0ogtCNA9S") // user
//                        .roles("USER")
//                        .build();
//        UserDetails admin =
//                User.builder()
//                        .username("admin")
//                        .password("{bcrypt}$2a$12$YeAuAkhNJeApas15uoN1uOkf4sfUr6s5tfOVgkxL6rva0ogtCNA9S") // user
//                        .roles("ADMIN", "USER")
//                        .build();
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        if (jdbcUserDetailsManager.userExists(user.getUsername())) {
//            jdbcUserDetailsManager.deleteUser(user.getUsername());
//        }
//        if (jdbcUserDetailsManager.userExists(admin.getUsername())) {
//            jdbcUserDetailsManager.deleteUser(admin.getUsername());
//        }
//        jdbcUserDetailsManager.createUser(user);
//        jdbcUserDetailsManager.createUser(admin);
//
//        return jdbcUserDetailsManager;
//    }

    //
}