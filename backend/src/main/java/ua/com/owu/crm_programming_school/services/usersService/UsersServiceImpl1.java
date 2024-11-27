package ua.com.owu.crm_programming_school.services.usersService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.com.owu.crm_programming_school.dao.UserDAO;
import ua.com.owu.crm_programming_school.models.User;

import java.security.Principal;

@Service
@AllArgsConstructor
public class UsersServiceImpl1 implements UsersService{
    private UserDAO userDAO;

    @Override
    public ResponseEntity<User> getCurrentUser(Principal principal ) {
      String username = principal.getName();

      if(username.isEmpty()) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      User user = userDAO.findByEmail(username);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
