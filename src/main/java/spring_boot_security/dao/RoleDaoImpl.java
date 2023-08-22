package spring_boot_security.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import spring_boot_security.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        log.debug("Получаем из БД все role");
        TypedQuery<Role> roles = entityManager.createQuery("SELECT r FROM Role r", Role.class);
        return roles.getResultList();
    }

    @Override
    public Set<Role> getRoleById(List<Long> rolesId) {
        log.debug("Получаем из БД role по id");
        TypedQuery<Role> role = entityManager.createQuery("select r from Role r where r.id in :role", Role.class)
                .setParameter("role", rolesId);
        return new HashSet<>(role.getResultList());
    }
}
