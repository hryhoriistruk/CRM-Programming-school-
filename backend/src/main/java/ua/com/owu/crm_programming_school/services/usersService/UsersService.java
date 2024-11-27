package ua.com.owu.crm_programming_school.services.usersService;

import org.springframework.http.ResponseEntity;
import ua.com.owu.crm_programming_school.models.User;

import java.security.Principal;

public interface UsersService {

    ResponseEntity<User> getCurrentUser(Principal principal);
}
