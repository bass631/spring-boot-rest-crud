package spring_boot_security.service;

import spring_boot_security.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAllRoles();

    Set<Role> getRoleById(List<Long> rolesId);
}