package ua.com.owu.crm_programming_school.services.authenticationService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import ua.com.owu.crm_programming_school.models.AuthenticationResponse;
import ua.com.owu.crm_programming_school.models.Password;
import ua.com.owu.crm_programming_school.models.RequestRefresh;
import ua.com.owu.crm_programming_school.models.TokenObtainPair;

public interface AuthenticationService {
    public ResponseEntity activate(String token, Password password);

    public ResponseEntity<AuthenticationResponse> authenticate(TokenObtainPair tokenObtainPair);

    public ResponseEntity<AuthenticationResponse> refresh(RequestRefresh requestRefresh, HttpServletResponse response);
}
