package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserServiceImpl userService;

    public LoginController(UserServiceImpl userService) {
        this.userService = userService;
    }

    ////////////////////////////////Главная страница//////////////////////////////

    @GetMapping("")
    public String welcomeToLogin() {
        return "/index";
    }

    ////////////////////////////////Страница регистрации//////////////////////////////

    @GetMapping("/registration")
    public String getNewUserForm(@ModelAttribute("user") User user) {
        return "/new_user";
    }

    @PostMapping("/registration")
    public String addNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/new_user";
        }

        if (!userService.saveUser(user)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "/new_user";
        }

        return "redirect:/user";
    }



}
