package spring_boot_security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_boot_security.model.User;

@Slf4j
@Controller
public class UserController {

    @GetMapping("/user")
    public String getUser(@AuthenticationPrincipal User users, Model model) {
        log.debug("Получен запрос на получение user");
        model.addAttribute(users);
        return "index";
    }
}
