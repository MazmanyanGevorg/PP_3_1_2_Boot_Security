package ru.kata.spring.boot_security.demo.dao;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
@Repository
public interface UserDAO {
    List<User> getAllUsers();

    User getUserById(Long userId);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(Long userId);

//    User findByUsername(String username);
}
