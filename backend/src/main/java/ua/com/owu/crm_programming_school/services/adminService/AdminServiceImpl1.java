package ua.com.owu.crm_programming_school.services.adminService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import ua.com.owu.crm_programming_school.dao.OrderDAO;
import ua.com.owu.crm_programming_school.dao.UserDAO;
import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.services.jwtService.JwtService;

@Service
@AllArgsConstructor
public class AdminServiceImpl1 implements AdminService {
    private UserDAO userDAO;
    private OrderDAO orderDAO;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public ResponseEntity<UserResponse> registerManager(UserRequest userRequest, HttpServletResponse response) {
        try {
            if (userDAO.existsByEmail(userRequest.getEmail())) {
                response.setHeader("Duplicate", "Invalid email");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                ResponseError responseError = ResponseError
                        .builder()
                        .error("duplicate email")
                        .code(400)
                        .build();
                try {
                    response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }


            User manager = User
                    .builder()
                    .name(userRequest.getName())
                    .surname(userRequest.getSurname())
                    .email(userRequest.getEmail())
                    .password(passwordEncoder.encode("1111"))
                    .roles(List.of(Role.MANAGER))
                    .createdAt(LocalDateTime.now().withNano(0))
                    .is_active(false)
                    .is_staff(true)
                    .is_superuser(false)
                    .build();

            User managerSaved = userDAO.save(manager);

            UserResponse userResponse = UserResponse
                    .builder()
                    .id(managerSaved.getId())
                    .name(managerSaved.getName())
                    .surname(managerSaved.getSurname())
                    .email(managerSaved.getEmail())
                    .build();

            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseAccess> requestToken(Integer id) {
        User user = userDAO.findById(id).get();
        String activationToken = jwtService.generateActivationToken(user);

        user.setActivationToken(activationToken);
        userDAO.save(user);

        ResponseAccess response = ResponseAccess
                .builder()
                .access(activationToken)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void createAdminDefault() {
        User admin = User
                .builder()
                .name("admin")
                .surname("adminovych")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .roles(List.of(Role.ADMIN))
                .createdAt(LocalDateTime.now().withNano(0))
                .is_active(true)
                .is_staff(true)
                .is_superuser(true)
                .build();

        userDAO.save(admin);

    }

    @Override
    public ResponseEntity<User> banManager(Integer id) {
        User manager = null;
        if (id > 0) {
            manager = userDAO.findById(id).get();

            if (manager != null && !manager.getRoles().contains(Role.ADMIN)) {
                manager.setIs_active(false);
                User savedUser = userDAO.save(manager);
                return new ResponseEntity<>(savedUser, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.valueOf("manager not exist or is ADMIN"));
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<User> unbanManager(Integer id) {
        User manager = null;
        if (id > 0) {
            manager = userDAO.findById(id).get();

            if (manager != null) {
                manager.setIs_active(true);
                User savedUser = userDAO.save(manager);
                return new ResponseEntity<>(savedUser, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.valueOf("no value present"));
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<UserPaginated> getAll(Integer page, Integer size) {

        try {
            String sortBy = "id";

            int totalRecords = (int) userDAO.count();
            int maxPage = (int) Math.ceil((double) totalRecords / size);
            int originalPage = page;

            if (page > maxPage) {
                page = maxPage;
            }
            if (page < 1) {
                page = 1;

            }

            Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, sortBy);

            Page<User> managers = userDAO.findAll(pageable);

            UserPaginated managerPaginated = new UserPaginated();
            managerPaginated.setTotal_items((int) managers.getTotalElements());
            managerPaginated.setTotal_pages(managers.getTotalPages());
            managerPaginated.setPrev(originalPage > 1 ? generatePageUrlPrev(originalPage - 1, maxPage) : null);
            managerPaginated.setNext(originalPage < maxPage ? generatePageUrlNext(originalPage + 1, maxPage) : null);
            managerPaginated.setItems(managers.getContent());

            return new ResponseEntity<>(managerPaginated, HttpStatus.OK);

        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getOrderStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        Long totalCount = orderDAO.count();
        Long nullStatusCount = orderDAO.countByStatus(null);
        Long newStatusCount = orderDAO.countByStatus("New");
        Long newAndNullStatusCount = newStatusCount + nullStatusCount;
        statistics.put("total_count", totalCount);
        List<Map<String, Object>> statusCounts = new ArrayList<>();

        statusCounts.add(createStatusCount("In work", orderDAO.countByStatus("In work")));
        statusCounts.add(createStatusCount("Agree", orderDAO.countByStatus("Agree")));
        statusCounts.add(createStatusCount("New", newAndNullStatusCount));
        statusCounts.add(createStatusCount("Disagree", orderDAO.countByStatus("Disagree")));
        statusCounts.add(createStatusCount("Dubbing", orderDAO.countByStatus("Dubbing")));

        List<Map<String, Object>> formattedStatusCounts = new ArrayList<>();
        for (Map<String, Object> statusCount : statusCounts) {
            Map<String, Object> formattedStatusCount = new LinkedHashMap<>();
            formattedStatusCount.put("status", statusCount.get("status"));
            formattedStatusCount.put("count", statusCount.get("count"));
            formattedStatusCounts.add(formattedStatusCount);
        }

        statistics.put("statuses", formattedStatusCounts);
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getManagerStatistics(Integer id) {
        Map<String, Object> statistics = new HashMap<>();

        User manager = userDAO.findById(id).orElse(null);

        if (manager == null) {
            return new ResponseEntity<>(statistics, HttpStatus.NOT_FOUND);
        }

        Long totalCount = orderDAO.countByManager(manager);
        statistics.put("total_count", totalCount);
        Long nullStatusCount = orderDAO.countByStatusAndManager(null, manager);
        Long newStatusCount = orderDAO.countByStatusAndManager("New", manager);
        Long newAndNullStatusCount = newStatusCount + nullStatusCount;

        List<Map<String, Object>> statusCounts = new ArrayList<>();
        statusCounts.add(createStatusCount("In work", orderDAO.countByStatusAndManager("In work", manager)));
        statusCounts.add(createStatusCount("Agree", orderDAO.countByStatusAndManager("Agree", manager)));
        statusCounts.add(createStatusCount("New",  newAndNullStatusCount));
        statusCounts.add(createStatusCount("Disagree", orderDAO.countByStatusAndManager("Disagree", manager)));
        statusCounts.add(createStatusCount("Dubbing", orderDAO.countByStatusAndManager("Dubbing", manager)));

        List<Map<String, Object>> formattedStatusCounts = new ArrayList<>();
        for (Map<String, Object> statusCount : statusCounts) {
            Map<String, Object> formattedStatusCount = new LinkedHashMap<>();
            formattedStatusCount.put("status", statusCount.get("status"));
            formattedStatusCount.put("count", statusCount.get("count"));
            formattedStatusCounts.add(formattedStatusCount);
        }

        statistics.put("statuses", formattedStatusCounts);

        return new ResponseEntity<>(statistics, HttpStatus.OK);

    }


    private Map<String, Object> createStatusCount(String status, Long count) {
        Map<String, Object> statusCount = new HashMap<>();
        statusCount.put("status", status);
        statusCount.put("count", count);
        return statusCount;
    }


    private String generatePageUrlPrev(int pageUrl, int maxPage) {
        if (pageUrl < 1) {
            pageUrl = 2;
        }
        if (pageUrl >= maxPage) {
            pageUrl = maxPage - 1;
        }

        return "/api/v1/admin/users?page=" + pageUrl;
    }

    private String generatePageUrlNext(int pageUrl, int maxPage) {

        if (pageUrl <= 1) {
            pageUrl = 2;
        }
        if (pageUrl >= maxPage) {
            pageUrl = maxPage;
        }
        return "/api/v1/admin/users?page=" + pageUrl;
    }

    public boolean adminExists() {
        return userDAO.existsByRolesAndEmail(Role.ADMIN, "admin@gmail.com");
    }


}
