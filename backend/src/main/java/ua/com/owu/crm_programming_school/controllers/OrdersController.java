package ua.com.owu.crm_programming_school.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.services.orderService.OrderService;
import ua.com.owu.crm_programming_school.views.Views;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "orders")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(@Qualifier("orderServiceImpl1") OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "get all orders",
            description = "Get all orders",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderPaginatedResponse.class)))})
    @JsonView(Views.Level3.class)
    public ResponseEntity<OrderPaginated> getAllOrders(
            @Parameter(description = "Page number within the paginated result set")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Order sorting parameter. Example: \"id\" asc or \"-id\" desc")
            @RequestParam(defaultValue = "-id") String order,
            @Parameter(description = "Number of results to return per page")
            @RequestParam(defaultValue = "25") Integer size,
            @Parameter(description = "User's first name")
            @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "User's last name")
            @RequestParam(value = "surname", required = false) String surname,
            @Parameter(description = "User's email address")
            @RequestParam(value = "email", required = false) String email,
            @Parameter(description = "User's phone number")
            @RequestParam(value = "phone", required = false) String phone,
            @Parameter(description = "User's age")
            @RequestParam(value = "age", required = false) Integer age,
            @Parameter(description = "User's course",
                    schema = @Schema(allowableValues = {"FS", "QACX", "JCX", "JSCX", "FE", "PCX"}))
            @RequestParam(value = "course", required = false) String course,
            @Parameter(description = "User's course format",
                    schema = @Schema(allowableValues = {"static", "online"}))
            @RequestParam(value = "courseFormat", required = false) String courseFormat,
            @Parameter(description = "User's course type",
                    schema = @Schema(allowableValues = {"pro", "minimal", "premium", "incubator", "vip"}))
            @RequestParam(value = "courseType", required = false) String courseType,
            @Parameter(description = "Amount already paid by the user")
            @RequestParam(value = "alreadyPaid", required = false) Integer alreadyPaid,
            @Parameter(description = "Name of the group")
            @RequestParam(value = "group", required = false) String group,
            @Parameter(description = "Total amount for course payment")
            @RequestParam(value = "sum", required = false) Integer sum,
            @Parameter(description = "Order status. Available values: In work, New, Agree, Disagree, Dubbing")
            @RequestParam(value = "status", required = false) String status,
            @Parameter(description = "Manager's username")
            @RequestParam(value = "manager", required = false) String manager,
            @Parameter(description = "Flag indicating whether the request is for the current user (true) or all users (false)")
            @RequestParam(value = "my", defaultValue = "false") String my,
            @Parameter(description = "Start date for filtering. Example: ?start_date=2020-01-01")
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @Parameter(description = "End date for filtering. Example: ?end_date=2022-01-01")
            @RequestParam(value = "endDate", required = false) LocalDate endDate, Principal principal) {


        return orderService.getAllOrders(page, order, size, name, surname, email, phone, age,
                course, courseFormat, courseType, alreadyPaid, group, sum, status, manager, my, startDate, endDate, principal);
    }



    @GetMapping("/excel")
    @Operation(summary = "Export filtered orders to Excel",
            description = "Export orders that match the filter criteria to an Excel file.",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponse.class)))})
    @JsonView(Views.Level3.class)
    public ResponseEntity<byte[]> getFilteredOrdersExcel(
            @Parameter(description = "Page number within the paginated result set")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Order sorting parameter. Example: \"id\" asc or \"-id\" desc")
            @RequestParam(defaultValue = "-id") String order,
            @Parameter(description = "Number of results to return per page")
            @RequestParam(defaultValue = "1000") Integer size,
            @Parameter(description = "User's first name")
            @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "User's last name")
            @RequestParam(value = "surname", required = false) String surname,
            @Parameter(description = "User's email address")
            @RequestParam(value = "email", required = false) String email,
            @Parameter(description = "User's phone number")
            @RequestParam(value = "phone", required = false) String phone,
            @Parameter(description = "User's age")
            @RequestParam(value = "age", required = false) Integer age,
            @Parameter(description = "User's course",
                    schema = @Schema(allowableValues = {"FS", "QACX", "JCX", "JSCX", "FE", "PCX"}))
            @RequestParam(value = "course", required = false) String course,
            @Parameter(description = "User's course format",
                    schema = @Schema(allowableValues = {"static", "online"}))
            @RequestParam(value = "courseFormat", required = false) String courseFormat,
            @Parameter(description = "User's course type",
                    schema = @Schema(allowableValues = {"pro", "minimal", "premium", "incubator", "vip"}))
            @RequestParam(value = "courseType", required = false) String courseType,
            @Parameter(description = "Amount already paid by the user")
            @RequestParam(value = "alreadyPaid", required = false) Integer alreadyPaid,
            @Parameter(description = "Name of the group")
            @RequestParam(value = "group", required = false) String group,
            @Parameter(description = "Total amount for course payment")
            @RequestParam(value = "sum", required = false) Integer sum,
            @Parameter(description = "Order status. Available values: In work, New, Agree, Disagree, Dubbing")
            @RequestParam(value = "status", required = false) String status,
            @Parameter(description = "Manager's username")
            @RequestParam(value = "manager", required = false) String manager,
            @Parameter(description = "Flag indicating whether the request is for the current user (true) or all users (false)")
            @RequestParam(value = "my", defaultValue = "false") String my,
            @Parameter(description = "Start date for filtering. Example: ?start_date=2020-01-01")
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @Parameter(description = "End date for filtering. Example: ?end_date=2022-01-01")
            @RequestParam(value = "endDate", required = false) LocalDate endDate, Principal principal) {

        return orderService.getFilteredOrdersExcel(page, order, size, name, surname, email, phone, age,
                course, courseFormat, courseType, alreadyPaid, group, sum, status, manager, my, startDate, endDate, principal);
    }



    @Operation(summary = "get order by id",
            description = "Get order by id",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)))})
    @GetMapping("/{id}")
    @JsonView(Views.Level3.class)
    public ResponseEntity<Order> getById(@PathVariable Integer id) {
        return orderService.getById(id);
    }

    @Operation(summary = "update order by id",
            description = "Update order by id",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseError.class),
                                    examples = @ExampleObject(
                                            name = "errorResponse",
                                            value = "{\"error\": \"One or more fields are not valid\", \"code\": 400, \"details\": [\"Field 'age': min age 16\", \"Field 'email': Enter a valid email address.\"]}")))})
    @PatchMapping("/{id}")
    @JsonView(Views.Level3.class)
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody  @Valid OrderEdit orderEdit, Principal principal) {
        return orderService.updateOrder(id, orderEdit, principal);
    }

    @Operation(summary = "get comments by order id",
            description = "Get comments by order id",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentListResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)))})
    @GetMapping("/{order_id}/comments")
    @JsonView(Views.Level3.class)
    public ResponseEntity<List<Comment>> getComments(@PathVariable Integer order_id) {
        return orderService.getComments(order_id);
    }

    @Operation(summary = "create comment",
            description = "Create comment",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)))})
    @PostMapping("/{order_id}/comments")
    @JsonView(Views.Level3.class)
    public ResponseEntity<Comment> createComment(@PathVariable Integer order_id, @RequestBody CommentRequest commentRequest, Principal principal) {
        return orderService.createComment(order_id, commentRequest, principal);
    }
}
