package ua.com.owu.crm_programming_school.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.services.adminService.AdminService;
import ua.com.owu.crm_programming_school.views.Views;

import java.util.Map;


@RestController
@Tag(name = "admin")
@RequestMapping(value = "/admin")
public class AdminController {
    AdminService adminService;

    public AdminController(@Qualifier("adminServiceImpl1") AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/users")
    @Operation(summary = "create new user",
            description = "Create new user",
            responses = {@ApiResponse(description = "created", responseCode = "201",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "duplicate email",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseError.class),
                                    examples = @ExampleObject(name = "errorResponse",
                                            value = "{\"error\": \"duplicate email\", \"code\": 400}")))})

    public ResponseEntity<UserResponse> registerManager(@RequestBody @Valid UserRequest userRequest, HttpServletResponse response) {
        return adminService.registerManager(userRequest, response);
    }


    @GetMapping("/users/{id}/re_token")
    @Operation(summary = "get token",
            description = "get some token: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpb...",
            responses = {@ApiResponse(description = "success", responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseAccess.class)))})
    public ResponseEntity<ResponseAccess> requestToken(@PathVariable Integer id) {
        return adminService.requestToken(id);
    }

    @Operation(summary = "ban manager",
            description = "Ban manager",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class),
                                    examples = @ExampleObject("{\n" +
                                            "  \"id\": 0,\n" +
                                            "  \"name\": \"John\",\n" +
                                            "  \"surname\": \"Doe\",\n" +
                                            "  \"email\": \"john.doe@example.com\",\n" +
                                            "  \"is_active\": true,\n" +
                                            "  \"is_superuser\": true,\n" +
                                            "  \"is_staff\": true,\n" +
                                            "  \"createdAt\": \"2023-10-05T18:02:59.024Z\",\n" +
                                            "  \"updatedAt\": \"2023-10-05T18:02:59.024Z\",\n" +
                                            "  \"lastLogin\": \"2023-10-05T18:02:59.024Z\"\n" +
                                            "}"))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(
                                            value = "manager not exist or is ADMIN")))})
    @PatchMapping("/users/{id}/ban")
    @JsonView(Views.Level2.class)
    public ResponseEntity<User> banManager(@PathVariable Integer id) {
        return adminService.banManager(id);
    }

    @Operation(summary = "unban manager",
            description = "Unban manager",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(
                                            value = "value not present")))})
    @PatchMapping("/users/{id}/unban")
    @JsonView(Views.Level2.class)
    public ResponseEntity<User> unbanManager(@PathVariable Integer id) {
        return adminService.unbanManager(id);
    }

    @Operation(summary = "get all users",
            description = "Get all users",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserPaginated.class)))})
    @GetMapping("/users")
    @JsonView(Views.Level2.class)
    public ResponseEntity<UserPaginated> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        return adminService.getAll(page, size);
    }

    @Operation(summary = "get all orders statistic",
            description = "Get all orders statistic",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class),
                                    examples = @ExampleObject("{\n" +
                                            "    \"total_count\": 500,\n" +
                                            "    \"statuses\": [\n" +
                                            "        {\n" +
                                            "            \"status\": \"In work\",\n" +
                                            "            \"count\": 1\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"status\": \"Agree\",\n" +
                                            "            \"count\": 1\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"status\": \"New\",\n" +
                                            "            \"count\": 495\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"status\": \"Disagree\",\n" +
                                            "            \"count\": 2\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"status\": \"Dubbing\",\n" +
                                            "            \"count\": 1\n" +
                                            "        }\n" +
                                            "    ]\n" +
                                            "}")))})
    @GetMapping("/statistic/orders")
    public ResponseEntity<Map<String, Object>> getOrderStatistics() {
        return adminService.getOrderStatistics();
    }

    @Operation(summary = "get all orders statistic by manager",
            description = "Get all orders statistic by manager",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class),
                                    examples = @ExampleObject("{\n" +
                                            "    \"total_count\": 5,\n" +
                                            "    \"statuses\": [\n" +
                                            "        {\n" +
                                            "            \"status\": \"In work\",\n" +
                                            "            \"count\": 0\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"status\": \"Agree\",\n" +
                                            "            \"count\": 1\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"status\": \"New\",\n" +
                                            "            \"count\": 1\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"status\": \"Disagree\",\n" +
                                            "            \"count\": 2\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"status\": \"Dubbing\",\n" +
                                            "            \"count\": 1\n" +
                                            "        }\n" +
                                            "    ]\n" +
                                            "}")))})
    @GetMapping("/statistic/users/{id}")
    public ResponseEntity<Map<String, Object>> getManagerStatistics(@PathVariable Integer id) {
        return adminService.getManagerStatistics(id);
    }
}
