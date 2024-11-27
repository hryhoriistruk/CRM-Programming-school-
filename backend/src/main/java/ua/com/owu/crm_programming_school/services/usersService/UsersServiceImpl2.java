package ua.com.owu.crm_programming_school.services.usersService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.com.owu.crm_programming_school.models.User;

import java.security.Principal;

@Service
public class UsersServiceImpl2 implements UsersService {
    @Override
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        return null;
    }
}
