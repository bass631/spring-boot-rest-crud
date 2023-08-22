package spring_boot_security.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import spring_boot_security.model.Role;
import spring_boot_security.model.User;
import spring_boot_security.service.RoleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    public UserDaoImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        log.debug("Получаем из БД всех user");
        TypedQuery<User> users = entityManager.createQuery("SELECT u FROM User u", User.class);
        return users.getResultList();
    }

    @Override
    public void saveUser(User user, Set<Role> roles) {
        log.debug("Сохраняем в БД user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        entityManager.merge(user);
    }

    @Override
    public void updateUser(User user, long id, Set<Role> roles) {
        log.debug("Обновляем в БД user");
        User user1 = entityManager.find(User.class, id);
        if (!user1.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            log.debug("Внимание! Пароль изменен!");
        }
        user.setId(id);
        user.setRoles(roles);
        entityManager.merge(user);
    }

    @Override
    public void updateUser(User user) {
        log.debug("Обновляем в БД user");
        User user1 = entityManager.find(User.class, user.getId());

        if (!user1.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            log.debug("Внимание! Пароль изменен!");
        }
        user.setRoles(roleService.getRoleById(user.getRolesId()));
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        log.debug("Удаляем из БД user");
        User deleteUser = entityManager.find(User.class, id);
        entityManager.remove(deleteUser);
    }

    @Override
    public User findByUsername(String email) {
        log.debug("Ищем в БД user по email");
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email =:email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public User getUserById(long id) {
        log.debug("Ищем в БД user по id");
        User user = entityManager.find(User.class, id);
        return user;
    }
}
