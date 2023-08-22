package spring_boot_security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring_boot_security.model.Role;
import spring_boot_security.model.User;
import spring_boot_security.service.RoleService;
import spring_boot_security.service.UserService;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api")
public class RESTController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RESTController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        log.debug("Получен запрос на получение всех user");
        List<User> allUsers = userService.getAllUsers();
        return allUsers != null && !allUsers.isEmpty()
                ? new ResponseEntity<>(allUsers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        log.debug("Получен запрос на получение сохранение user");
        Set<Role> roles = roleService.getRoleById(user.getRolesId());
        userService.saveUser(user, roles);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        log.debug("Получен запрос на обновление user");
        userService.updateUser(user);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        log.debug("Получен запрос на удаление user");
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        log.debug("Получен запрос на получение user");
        User user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
        log.debug("Получен запрос на получение user");
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
