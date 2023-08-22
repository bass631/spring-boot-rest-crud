package spring_boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_boot_security.model.Role;
import spring_boot_security.model.User;
import spring_boot_security.service.RoleService;
import spring_boot_security.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/")
    public String index(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @PostMapping(value = "admin/save")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("rolesId") List<Long> rolesId) {
        Set<Role> roles = roleService.getRoleById(rolesId);
        userService.saveUser(user, roles);
        return "redirect:/admin/";
    }

    @PostMapping(value = "admin/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("updId") long id,
                             @RequestParam("rolesId") List<Long> rolesId) {
        Set<Role> roles = roleService.getRoleById(rolesId);
        userService.updateUser(user, id, roles);
        return "redirect:/admin/";
    }

    @DeleteMapping(value = "admin/delete")
    public String deleteUser(@RequestParam("delId") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
}
