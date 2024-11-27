package ua.com.owu.crm_programming_school.services.orderService;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ua.com.owu.crm_programming_school.dao.GroupDAO;
import ua.com.owu.crm_programming_school.dao.OrderDAO;
import ua.com.owu.crm_programming_school.dao.UserDAO;
import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.models.Comment;
import ua.com.owu.crm_programming_school.specification.OrderSpecifications;


@Service
@AllArgsConstructor
public class OrderServiceImpl1 implements OrderService {
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private GroupDAO groupDAO;

    public ResponseEntity<OrderPaginated> getAllOrders(Integer page, String order, Integer size, String name, String surname, String email, String phone,
                                                       Integer age, String course, String courseFormat, String courseType,
                                                       Integer alreadyPaid, String group, Integer sum, String status, String manager, String my,
                                                       LocalDate startDate, LocalDate endDate, Principal principal) {
        try {
            boolean isMy = "true".equalsIgnoreCase(my);

            Sort.Direction sortDirection = Sort.Direction.ASC;
            String sortBy;

            if (order != null && !order.isEmpty()) {
                if (order.startsWith("-")) {
                    sortDirection = Sort.Direction.DESC;
                    sortBy = order.substring(1);
                } else {
                    sortBy = order;
                }
            } else {
                sortBy = order;
            }
            int totalRecords = (int) orderDAO.count();
            int maxPage = (int) Math.ceil((double) totalRecords / size);
            int originalPage = page;

            if (page > maxPage) {
                page = maxPage;
            }
            if (page < 1) {
                page = 1;
            }

            Pageable pageable = PageRequest.of(page - 1, size, sortDirection, sortBy);

            Specification<Order> spec = Specification.where(null);

            spec = spec.and(OrderSpecifications.filterByName(name));
            spec = spec.and(OrderSpecifications.filterBySurname(surname));
            spec = spec.and(OrderSpecifications.filterByEmail(email));
            spec = spec.and(OrderSpecifications.filterByPhone(phone));
            spec = spec.and(OrderSpecifications.filterByAge(age));
            spec = spec.and(OrderSpecifications.filterByCourse(course));
            spec = spec.and(OrderSpecifications.filterByCourseFormat(courseFormat));
            spec = spec.and(OrderSpecifications.filterByCourseType(courseType));
            spec = spec.and(OrderSpecifications.filterBySum(sum));
            spec = spec.and(OrderSpecifications.filterByStatus(status));
            spec = spec.and(OrderSpecifications.filterByAlreadyPaid(alreadyPaid));
            spec = spec.and(OrderSpecifications.filterByGroup(group));
            spec = spec.and(OrderSpecifications.filterByManager(manager));
            spec = spec.and(OrderSpecifications.filterByMy(principal, isMy));
            spec = spec.and(OrderSpecifications.filterByStartDate(startDate));
            spec = spec.and(OrderSpecifications.filterByEndDate(endDate));

            Page<Order> orders = orderDAO.findAll(spec, pageable);

            OrderPaginated orderPaginated = new OrderPaginated();
            orderPaginated.setTotal_items((int) orders.getTotalElements());
            orderPaginated.setTotal_pages(orders.getTotalPages());
            orderPaginated.setPrev(originalPage > 1 ? generatePageUrlPrev(originalPage - 1, order, maxPage) : null);
            orderPaginated.setNext(originalPage < maxPage ? generatePageUrlNext(originalPage + 1, order, maxPage) : null);
            orderPaginated.setItems(orders.getContent());

            return new ResponseEntity<>(orderPaginated, HttpStatus.OK);

        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Order> getById(Integer id) {
        Order order = null;
        if (id > 0) {
            order = orderDAO.findById(Long.valueOf(id)).get();
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Order> updateOrder(Integer id, OrderEdit orderEdit, Principal principal) {
        Order order = orderDAO.findById(Long.valueOf(id)).get();
        if (order != null) {

            String email = principal.getName();
            User manager = userDAO.findByEmail(email);
            boolean isOwner = order.getManager() != null && order.getManager().getEmail().equals(principal.getName());
            boolean isManagerNotAssigned = order.getManager() == null;

            Group group = groupDAO.findByName(orderEdit.getGroup());

            if (isOwner || isManagerNotAssigned) {
                if (orderEdit.getName() != null && !orderEdit.getName().isEmpty()) {
                    order.setName(orderEdit.getName());
                }
                if (orderEdit.getSurname() != null && !orderEdit.getSurname().isEmpty()) {
                    order.setSurname(orderEdit.getSurname());
                }
                if (orderEdit.getEmail() != null && !orderEdit.getEmail().isEmpty()) {
                    order.setEmail(orderEdit.getEmail());
                }
                if (orderEdit.getPhone() != null && !orderEdit.getPhone().isEmpty()) {
                    order.setPhone(orderEdit.getPhone());
                }
                if (orderEdit.getAge() != null ) {
                    order.setAge(orderEdit.getAge());
                }
                if (orderEdit.getCourse() != null && !orderEdit.getCourse().isEmpty()) {
                    order.setCourse(orderEdit.getCourse());
                }
                if (orderEdit.getCourseFormat() != null && !orderEdit.getCourseFormat().isEmpty()) {
                    order.setCourseFormat(orderEdit.getCourseFormat());
                }
                if (orderEdit.getCourseType() != null && !orderEdit.getCourseType().isEmpty()) {
                    order.setCourseType(orderEdit.getCourseType());
                }
                if (orderEdit.getAlreadyPaid() != null ) {
                    order.setAlreadyPaid(orderEdit.getAlreadyPaid());
                }
                if (orderEdit.getSum() != null) {
                    order.setSum(orderEdit.getSum());
                }
                if (orderEdit.getMsg() != null && !orderEdit.getMsg().isEmpty()) {
                    order.setMsg(orderEdit.getMsg());
                }
                if (orderEdit.getStatus() != null && !orderEdit.getStatus().isEmpty()) {
                    order.setStatus(orderEdit.getStatus());
                    if (orderEdit.getStatus().equals("New")) {
                        order.setManager(null);
                    } else {
                        order.setManager(manager);
                    }
                } else {
                    order.setManager(manager);
                    order.setStatus("In work");
                }
                if (group != null ) {
                    order.setGroup(group);
                } else if (orderEdit.getGroup() != null && !orderEdit.getGroup().isEmpty()) {
                    Group newGroup = new Group(orderEdit.getGroup());
                    order.setGroup(newGroup);
                }

                Order savedOrder = orderDAO.save(order);

                return new ResponseEntity<>(savedOrder, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.valueOf("You cannot do it"));
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<Comment>> getComments(Integer orderId) {
        Order order = null;
        if (orderId > 0) {
            order = orderDAO.findById(Long.valueOf(orderId)).get();
            if (order != null) {
                List<Comment> comments = order.getComments();
                return new ResponseEntity<>(comments, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Comment> createComment(Integer orderId, CommentRequest commentRequest, Principal principal) {
        Order order = null;
        if (orderId > 0) {
            order = orderDAO.findById(Long.valueOf(orderId)).get();

            if (order != null) {
                String email = principal.getName();
                User manager = userDAO.findByEmail(email);
                boolean isOwner = order.getManager() != null && order.getManager().getEmail().equals(principal.getName());
                boolean isManagerNotAssigned = order.getManager() == null;

                if (isOwner || isManagerNotAssigned) {
                    List<Comment> comments = order.getComments();
                    Comment comment = Comment
                            .builder()
                            .comment(commentRequest.getComment())
                            .createdAt(LocalDateTime.now().withNano(0))
                            .orderId(orderId)
                            .manager(manager)
                            .build();
                    comments.add(comment);
                    order.setComments(comments);
                    order.setManager(manager);
                    String status = order.getStatus();

                    if ("New".equals(status) || status == null) {
                        order.setStatus("In work");
                    }

                    Order savedOrder = orderDAO.save(order);
                    int lastIndex = savedOrder.getComments().size() - 1;
                    Comment savedComment = savedOrder.getComments().get(lastIndex);

                    return new ResponseEntity<>(savedComment, HttpStatus.OK);
                }
                return new ResponseEntity<>(null, HttpStatus.valueOf("You cannot do it"));
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<byte[]> getFilteredOrdersExcel(Integer page, String order, Integer size, String name, String surname, String email, String phone, Integer age, String course, String courseFormat, String courseType, Integer alreadyPaid, String group, Integer sum, String status, String manager, String my, LocalDate startDate, LocalDate endDate, Principal principal) {
        try {

            boolean isMy = "true".equalsIgnoreCase(my);

            Sort.Direction sortDirection = Sort.Direction.ASC;
            String sortBy;

            if (order != null && !order.isEmpty()) {
                if (order.startsWith("-")) {
                    sortDirection = Sort.Direction.DESC;
                    sortBy = order.substring(1);
                } else {
                    sortBy = order;
                }
            } else {
                sortBy = order;
            }
            int totalRecords = (int) orderDAO.count();
            int maxPage = (int) Math.ceil((double) totalRecords / size);
            int originalPage = page;

            if (page > maxPage) {
                page = maxPage;
            }
            if (page < 1) {
                page = 1;
            }

            Pageable pageable = PageRequest.of(page - 1, size, sortDirection, sortBy);

            Specification<Order> spec = Specification.where(null);

            spec = spec.and(OrderSpecifications.filterByName(name));
            spec = spec.and(OrderSpecifications.filterBySurname(surname));
            spec = spec.and(OrderSpecifications.filterByEmail(email));
            spec = spec.and(OrderSpecifications.filterByPhone(phone));
            spec = spec.and(OrderSpecifications.filterByAge(age));
            spec = spec.and(OrderSpecifications.filterByCourse(course));
            spec = spec.and(OrderSpecifications.filterByCourseFormat(courseFormat));
            spec = spec.and(OrderSpecifications.filterByCourseType(courseType));
            spec = spec.and(OrderSpecifications.filterBySum(sum));
            spec = spec.and(OrderSpecifications.filterByStatus(status));
            spec = spec.and(OrderSpecifications.filterByAlreadyPaid(alreadyPaid));
            spec = spec.and(OrderSpecifications.filterByGroup(group));
            spec = spec.and(OrderSpecifications.filterByManager(manager));
            spec = spec.and(OrderSpecifications.filterByMy(principal, isMy));
            spec = spec.and(OrderSpecifications.filterByStartDate(startDate));
            spec = spec.and(OrderSpecifications.filterByEndDate(endDate));


            Page<Order> orders = orderDAO.findAll(spec, pageable);

            List<Order> filteredOrders = orders.getContent();

            byte[] excelFile = generateExcelFile(filteredOrders);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "filtered_orders.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(excelFile);

        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public byte[] generateExcelFile(List<Order> orders) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = (Sheet) workbook.createSheet("Orders");

            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("Id");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Surname");
            headerRow.createCell(3).setCellValue("Email");
            headerRow.createCell(4).setCellValue("Phone");
            headerRow.createCell(5).setCellValue("Age");
            headerRow.createCell(6).setCellValue("Course");
            headerRow.createCell(7).setCellValue("Course Format");
            headerRow.createCell(8).setCellValue("Course Type");
            headerRow.createCell(9).setCellValue("Status");
            headerRow.createCell(10).setCellValue("Sum");
            headerRow.createCell(11).setCellValue("Already Paid");
            headerRow.createCell(12).setCellValue("Group");
            headerRow.createCell(13).setCellValue("Created At");
            headerRow.createCell(14).setCellValue("Manager");

            int rowNum = 1;
            for (Order order : orders) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(order.getId());
                row.createCell(1).setCellValue(order.getName() != null ? order.getName() : "");
                row.createCell(2).setCellValue(order.getSurname() != null ? order.getSurname() : "");
                row.createCell(3).setCellValue(order.getEmail() != null ? order.getEmail() : "");
                row.createCell(4).setCellValue(order.getPhone() != null ? order.getPhone() : "");
                row.createCell(5).setCellValue(order.getAge() != null ? order.getAge().toString() : "");
                row.createCell(6).setCellValue(order.getCourse() != null ? order.getCourse() : "");
                row.createCell(7).setCellValue(order.getCourseFormat() != null ? order.getCourseFormat() : "");
                row.createCell(8).setCellValue(order.getCourseType() != null ? order.getCourseType() : "");
                row.createCell(9).setCellValue(order.getStatus() != null ? order.getStatus() : "");
                row.createCell(10).setCellValue(order.getSum() != null ? order.getSum().toString() : "");
                row.createCell(11).setCellValue(order.getAlreadyPaid() != null ? order.getAlreadyPaid().toString() : "");
                row.createCell(12).setCellValue(order.getGroup() != null && order.getGroup().getName() != null ? order.getGroup().getName() : "");
                row.createCell(13).setCellValue(order.getCreated() != null ? order.getCreated().toString() : "");
                row.createCell(14).setCellValue(order.getManager() != null && order.getManager().getName() != null ? order.getManager().getName() : "");

            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String generatePageUrlPrev(int pageUrl, String order, int maxPage) {
        if (pageUrl < 1) {
            pageUrl = 2;
        }

        if (pageUrl >= maxPage) {
            pageUrl = maxPage - 1;
        }

        return "/api/v1/orders?page=" + pageUrl +
                "&order=" + order;
    }

    private String generatePageUrlNext(int pageUrl, String order, int maxPage) {

        if (pageUrl <= 1) {
            pageUrl = 2;
        }
        if (pageUrl >= maxPage) {
            pageUrl = maxPage;
        }
        return "/api/v1/orders?page=" + pageUrl +
                "&order=" + order;
    }
}
