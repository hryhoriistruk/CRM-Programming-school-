package ua.com.owu.crm_programming_school.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ua.com.owu.crm_programming_school.dao.UserDAO;
import ua.com.owu.crm_programming_school.models.ResponseError;
import ua.com.owu.crm_programming_school.models.User;
import ua.com.owu.crm_programming_school.services.jwtService.JwtService;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private UserDAO userDAO;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String requestUri = request.getRequestURI();

        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ") || requestUri.contains("/auth")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorizationHeader.replace("Bearer ", "");
            String userEmail = jwtService.extractUsername(token);
            User user = userDAO.findByEmail(userEmail);

            if (user != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);


                if (jwtService.isTokenValid(token, userDetails, user)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authenticationToken);
                } else {
                    System.out.println(401);
                    response.setHeader("TokenError", "invalid token");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                    ResponseError responseError = ResponseError
                            .builder()
                            .error("invalid token")
                            .code(401)
                            .build();

                    response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
                    return;
                }
            }

        } catch (IOException | UsernameNotFoundException | ServletException e) {
            throw new RuntimeException(e);
        } catch (ExpiredJwtException e) {
            response.setHeader("TokenError", "token is dead");
            response.resetBuffer();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            ResponseError responseError = ResponseError
                    .builder()
                    .error("token is dead")
                    .code(401)
                    .build();

            response
                    .getOutputStream()
                    .write(new ObjectMapper().writeValueAsBytes(responseError));

            return;

    } catch (MalformedJwtException | SignatureException e) {
        response.setHeader("TokenError", "invalid token");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        ResponseError responseError = ResponseError
                .builder()
                .error("invalid token")
                .code(401)
                .build();

        response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
        return;
    }

        filterChain.doFilter(request, response);
    }
}
