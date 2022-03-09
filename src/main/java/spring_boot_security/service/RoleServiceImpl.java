package spring_boot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_boot_security.dao.RoleDao;
import spring_boot_security.model.Role;

import java.util.List;
import java.util.Set;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Transactional
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Transactional
    public Set<Role> getRoleById(List<Integer> rolesId) {
        return roleDao.getRoleById(rolesId);
    }
}
