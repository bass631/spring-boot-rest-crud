package spring_boot_security.dao;

import spring_boot_security.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    List<Role> getAllRoles();

    Set<Role> getRoleById(List<Long> rolesId);
}
