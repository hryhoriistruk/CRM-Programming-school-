package ua.com.owu.crm_programming_school.services.authenticationService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import ua.com.owu.crm_programming_school.models.AuthenticationResponse;
import ua.com.owu.crm_programming_school.models.Password;
import ua.com.owu.crm_programming_school.models.RequestRefresh;
import ua.com.owu.crm_programming_school.models.TokenObtainPair;

public class AuthenticationServiceImpl2 implements AuthenticationService{
    @Override
    public ResponseEntity activate(String token, Password password) {

        return null;
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(TokenObtainPair tokenObtainPair) {
        return null;
    }

    @Override
    public ResponseEntity<AuthenticationResponse> refresh(RequestRefresh requestRefresh, HttpServletResponse response) {
        return null;
    }
}
