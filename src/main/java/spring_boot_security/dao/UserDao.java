package spring_boot_security.dao;

import spring_boot_security.model.Role;
import spring_boot_security.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    List<User> getAllUsers();

    void saveUser(User user, Set<Role> roles);

    void updateUser(User user, int id, Set<Role> roles);

    void updateUser(User user);

    void deleteUser(int id);

    User findByUsername(String username);

    User getUserById(int Id);
}
