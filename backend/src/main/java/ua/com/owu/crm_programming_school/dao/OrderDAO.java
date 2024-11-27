package ua.com.owu.crm_programming_school.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.com.owu.crm_programming_school.models.Order;
import ua.com.owu.crm_programming_school.models.User;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Long countByStatus(String status);

    Long countByManager(User manager);

   Long countByStatusAndManager(String status, User manager);
}

