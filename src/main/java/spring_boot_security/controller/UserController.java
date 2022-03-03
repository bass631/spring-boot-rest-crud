package spring_boot_security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_boot_security.model.User;


@Controller
public class UserController {

    @GetMapping("/user")
    public String getUser(@AuthenticationPrincipal User users, Model model) {
        model.addAttribute(users);
        return "index";
    }
}
