package ua.com.owu.crm_programming_school.services.orderService;

import org.springframework.http.ResponseEntity;

import ua.com.owu.crm_programming_school.models.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


public interface OrderService {

    ResponseEntity<OrderPaginated> getAllOrders(Integer page, String order, Integer size, String name, String surname, String email, String phone,
                                                Integer age, String course, String courseFormat, String courseType,
                                                Integer alreadyPaid, String group, Integer sum, String status, String manager, String my,
                                                LocalDate startDate, LocalDate endDate, Principal principal);

    ResponseEntity<Order> getById(Integer id);

    ResponseEntity<Order> updateOrder(Integer id, OrderEdit orderEdit, Principal principal);

    ResponseEntity<List<Comment>> getComments(Integer orderId);

    ResponseEntity<Comment> createComment(Integer orderId, CommentRequest commentRequest, Principal principal);

    ResponseEntity<byte[]> getFilteredOrdersExcel(Integer page, String order, Integer size, String name, String surname, String email, String phone, Integer age, String course, String courseFormat, String courseType, Integer alreadyPaid, String group, Integer sum, String status, String manager, String my, LocalDate startDate, LocalDate endDate, Principal principal);
}
