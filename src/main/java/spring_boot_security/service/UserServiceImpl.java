package spring_boot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_boot_security.dao.UserDao;
import spring_boot_security.model.Role;
import spring_boot_security.model.User;


import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void saveUser(User user, Set<Role> roles) {
        userDao.saveUser(user, roles);
    }

    @Override
    @Transactional(timeout = 10)
    public void updateUser(User user, long id, Set<Role> roles) {
        userDao.updateUser(user, id, roles);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }
}
