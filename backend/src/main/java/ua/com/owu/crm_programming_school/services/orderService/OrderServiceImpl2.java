package ua.com.owu.crm_programming_school.services.orderService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ua.com.owu.crm_programming_school.models.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl2 implements OrderService{

    @Override
    public ResponseEntity<OrderPaginated> getAllOrders(Integer page, String order, Integer size, String name, String surname, String email, String phone,
                                                       Integer age, String course, String courseFormat, String courseType,
                                                       Integer alreadyPaid, String group, Integer sum, String status, String manager, String my,
                                                       LocalDate startDate, LocalDate endDate, Principal principal) {
        return null;
    }

    @Override
    public ResponseEntity<Order> getById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Order> updateOrder(Integer id, OrderEdit orderEdit, Principal principal) {
        return null;
    }

    @Override
    public ResponseEntity<List<Comment>> getComments(Integer orderId) {
        return null;
    }

    @Override
    public ResponseEntity<Comment> createComment(Integer orderId, CommentRequest commentRequest, Principal principal) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getFilteredOrdersExcel(Integer page, String order, Integer size, String name, String surname, String email, String phone, Integer age, String course, String courseFormat, String courseType, Integer alreadyPaid, String group, Integer sum, String status, String manager, String my, LocalDate startDate, LocalDate endDate, Principal principal) {
        return null;
    }
}
