package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUserForm(Model model) {

        model.addAttribute( "users1", userService.getAllUsers());
        System.out.println(userService.getAllUsers());
        for (User user : userService.getAllUsers()) {
            System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
            System.out.println(user.getRoles());
        }
        System.out.println();
        return "/user";
    }

//    @GetMapping("")
//    public String getUserForm(Model model, Principal principal) {
//        User user = userService.findByName(principal.getName());
//        model.addAttribute("users1", user);
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println(principal);
//        System.out.println(principal);
//        System.out.println(principal);
//        System.out.println(principal);
//        System.out.println(principal);
//        System.out.println(principal);
//        System.out.println(principal);
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("------------------------------------------------------------");
//        return "/user";
//    }
}
