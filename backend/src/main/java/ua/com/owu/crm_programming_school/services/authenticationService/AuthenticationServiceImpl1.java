package ua.com.owu.crm_programming_school.services.authenticationService;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;

import ua.com.owu.crm_programming_school.dao.UserDAO;
import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.services.jwtService.JwtService;



@Service
@AllArgsConstructor
public class AuthenticationServiceImpl1 implements AuthenticationService {
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;


    public ResponseEntity<String> activate(String activationToken, Password password) {
        String username = jwtService.extractUsername(activationToken);
        User user = userDAO.findByEmail(username);
        if (user.getActivationToken()!=null && user.getActivationToken().equals(activationToken)) {
            user.setIs_active(true);
            user.setActivationToken(null);
            user.setPassword(passwordEncoder.encode(password.getPassword()));
            userDAO.save(user);
        } else  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<AuthenticationResponse> authenticate(TokenObtainPair tokenObtainPair) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        tokenObtainPair.getUsername(),
                        tokenObtainPair.getPassword()
                )
        );
        User user = userDAO.findByEmail(tokenObtainPair.getUsername());
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        user.setLastLogin(LocalDateTime.now().withNano(0));
        userDAO.save(user);

        AuthenticationResponse responseAuth = AuthenticationResponse
                .builder()
                .access(accessToken)
                .refresh(refreshToken)
                .build();

        return new ResponseEntity<>(responseAuth, HttpStatus.OK);
    }

    public ResponseEntity<AuthenticationResponse> refresh(RequestRefresh requestRefresh, HttpServletResponse response) {

        try {

            String accessToken = requestRefresh.getRefresh();
            String username = jwtService.extractUsername(accessToken);
            User user = userDAO.findByEmail(username);
            String newAccessToken = null;
            String newRefreshToken = null;

            if (user.getRefreshToken().equals(accessToken)) {
                newAccessToken = jwtService.generateToken(user);
                newRefreshToken = jwtService.generateRefreshToken(user);
                user.setRefreshToken(newRefreshToken);
                userDAO.save(user);

                AuthenticationResponse responseAuth = AuthenticationResponse
                        .builder()
                        .access(newAccessToken)
                        .refresh(newRefreshToken)
                        .build();


                return new ResponseEntity<>(responseAuth, HttpStatus.OK);

            } else {
                response.setHeader("TokenError", "Token is not valid");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                ResponseError responseError = ResponseError
                        .builder()
                        .error("Token is not valid")
                        .code(401)
                        .build();
                try {
                    response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (MalformedJwtException | SignatureException e) {


            response.setHeader("TokenError", "Token is not valid");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            ResponseError responseError = ResponseError
                    .builder()
                    .error("Token is not valid")
                    .code(401)
                    .build();
            try {
                response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
