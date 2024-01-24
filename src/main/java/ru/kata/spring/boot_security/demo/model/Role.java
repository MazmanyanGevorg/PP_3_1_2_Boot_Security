package ru.kata.spring.boot_security.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


    public Role(Long id) {
        this.id = id;
    }

    public Role(long l, String roleUser) {
        this.id = l;
        this.name = roleUser;
    }
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
/*
Аннотация @Transient в данной части кода означает, что поле users не будет
сохранено в базе данных при использовании механизма JPA.
Она говорит JPA игнорировать это поле при сохранении сущности Role в базе данных.

Однако, аннотация @ManyToMany(mappedBy = "roles") определяет отношение
многие-ко-многим между сущностями Role и User, при этом mappedBy = "roles" указывает JPA,
что связь управляется сущностью User и полем roles в ней. То есть, это указывает
JPA использовать связь, определенную в поле roles в классе User,
для установления соответствия между этой сущностью Role и сущностями User.

Таким образом, это отношение позволяет одной роли соотноситься с несколькими
пользователями (многие ко многим), и при этом аннотация @Transient гарантирует,
что поле users не будет отображаться как столбец в базе данных,
а его значение будет управляться через связи между таблицами.
 */