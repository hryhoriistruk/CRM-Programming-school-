package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    @Schema(description = "Unique identifier for the order", readOnly = true)
    private Long id;

    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 25)
    @Schema(description = "User's first name", example = "John")
    private String name;

    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 25)
    @Schema(description = "User's last name", example = "Doe")
    private String surname;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email address")
    @Size(min = 1, max = 100)
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Pattern(regexp = "^([+]?[\\s0-9]+)?(\\d{3}|[(]?[0-9]+[)])?([-]?[\\s]?[0-9])+$")
    @Size(min = 1, max = 12)
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String phone;

    @Min(16)
    @Max(90)
    @Schema(description = "User's age", example = "25")
    private Integer age;

    @Pattern(regexp = "FS|QACX|JCX|JSCX|FE|PCX")
    @Schema(description = "User's course", example = "QACX")
    private String course;

    @Pattern(regexp = "static|online|^$")
    @Schema(description = "User's course format", example = "online")
    private String courseFormat;

    @Pattern(regexp = "pro|minimal|premium|incubator|vip")
    @Schema(description = "User's course type", example = "pro")
    private String courseType;

    @Min(1)
    @Max(2147483647)
    @Schema(description = "Amount already paid by the user", example = "500")
    private Integer alreadyPaid;

    @Min(1)
    @Max(2147483647)
    @Schema(description = "Total amount for course payment", example = "1000")
    private Integer sum;

    @Schema(description = "User's message", example = "Thank you for registration!")
    private String msg;

    @Pattern(regexp = "In work|New|Aggre|Disaggre|Dubbing")
    @Schema(description = "Order status", example = "New")
    private String status;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserResponse manager;

    @Schema(description = "Order creation date")
    private LocalDateTime created;

    @Schema(description = "Order's UTM parameter", example = "utm_source=google&utm_medium=cpc")
    private String utm;

    private List<CommentResponse> comments;

    private Group group;
}
