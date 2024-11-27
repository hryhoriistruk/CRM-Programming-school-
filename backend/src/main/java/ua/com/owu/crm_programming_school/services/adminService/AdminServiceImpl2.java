package ua.com.owu.crm_programming_school.services.adminService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ua.com.owu.crm_programming_school.models.*;

import java.util.Map;


@Service
public class AdminServiceImpl2 implements AdminService {
    @Override
    public ResponseEntity<UserResponse> registerManager(UserRequest userRequest, HttpServletResponse response) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseAccess> requestToken(Integer id) {
        return null;
    }

    @Override
    public boolean adminExists() {
        return false;
    }

    @Override
    public void createAdminDefault() {

    }

    @Override
    public ResponseEntity<User> banManager(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<User> unbanManager(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<UserPaginated> getAll(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getOrderStatistics() {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getManagerStatistics(Integer id) {
        return null;
    }

}
