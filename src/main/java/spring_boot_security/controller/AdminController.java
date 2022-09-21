package spring_boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_boot_security.model.User;
import spring_boot_security.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsersPageNew(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("authenticatedUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("newUser", new User());
        return "/admin";
    }
}
