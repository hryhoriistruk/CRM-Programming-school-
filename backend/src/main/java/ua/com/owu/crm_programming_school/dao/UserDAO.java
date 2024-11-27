package ua.com.owu.crm_programming_school.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.owu.crm_programming_school.models.Role;
import ua.com.owu.crm_programming_school.models.User;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findUserById(Integer id);

    boolean existsByRolesAndEmail(Role role, String email);

    boolean existsByEmail(String email);
}

