package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    public AdminController() {
    }

    @GetMapping("")
    public String getUsersListForm(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/list";
    }

    ////////////////////////////////create new user////////////////////////////////////////

    @GetMapping("/new")
    public String getNewUserForm(@ModelAttribute("user") User user) {
        return "/new_user";
    }

    @PostMapping("/new")
    public String creatNewUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/";
    }

//////////////////////////////edit/update/showById/////////////////////////////////////////////

    @GetMapping("/edit")
    public ModelAndView editUserForm(@RequestParam Long id) {
        ModelAndView mav = new ModelAndView("/edit_user");
        User user = userService.getUserById(id);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/";
    }

/////////////////////////////////delete///////////////////////////////////////////////

    @PostMapping("/")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
