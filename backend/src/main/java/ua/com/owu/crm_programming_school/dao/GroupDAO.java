package ua.com.owu.crm_programming_school.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.owu.crm_programming_school.models.Group;

public interface GroupDAO extends JpaRepository<Group, Integer> {
    Group findByName(String name);
}
