package ru.kata.spring.boot_security.demo.model;

//import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Size(min = 2, message = "Не меньше 5 знаков")
    private String name;

    @Column(name = "username")
    @Size(min = 2, message = "Не меньше 5 знаков")
    private String username;

    @Column(name = "surname")
    @Size(min = 2, message = "Не меньше 5 знаков")
    private String surname;

    @Column(name = "age")
    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
    private int age;

    @Column(name = "level")
    @Min(value = 0, message = "Level should not be less than 0")
    @Max(value = 100, message = "Level should not be greater than 100")
    private int level;

    @Column(name = "points")
    @Min(value = 0, message = "Points should not be less than 0")
    @Max(value = 100_000, message = "Points should not be greater than 100_000")
    private int points;

    @Column(name = "password")
    @Size(min = 2, message = "Не меньше 5 знаков")
    private String password;

//    @Transient
//    private String passwordConfirm;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    /*
    - `joinColumns` и `inverseJoinColumns`: Эти аннотации определяют столбцы,
    которые связывают таблицы `User` и `Role`. `joinColumns` указывает на
    столбец "user_id" в таблице-связке, который ссылается на первичный ключ (`id`) таблицы `User`.
    - `inverseJoinColumns` указывает на столбец "role_id" в таблице-связке, который ссылается на
    первичный ключ (`id`) таблицы `Role`.
     */

    public User(String username, String name, String surname, int age, int level, int points, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.level = level;
        this.points = points;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // возвращает список ролей одного пользователя
        Set<Role> roles = this.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
    /*
    Этот метод в Spring Security используется для получения
        списка ролей (GrantedAuthority) пользователя.
    В данном коде метод getAuthorities() перебирает список ролей
        пользователя (this.getRoles()) и создает список SimpleGrantedAuthority,
        который является реализацией интерфейса GrantedAuthority. Каждая роль преобразуется
        в SimpleGrantedAuthority с именем роли (role.getName()), и добавляется в список authorities.
    Возвращается список authorities, который представляет собой коллекцию ролей,
        присвоенных пользователю. Этот список ролей используется для проверки доступа
        пользователя к определенным ресурсам или операциям в системе с использованием
        механизмов авторизации Spring Security.
     */

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
