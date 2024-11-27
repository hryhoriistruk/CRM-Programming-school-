package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEdit {

    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]*$", message = "Enter a valid name")
    @Size( max = 25, message = "name can by max 25 symbols")
    @Nullable
    @Schema(description = "User's first name", example = "John")
    private String name;

    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]*$", message = "Enter a valid surname")
    @Size( max = 25, message = "surname can by max 25 symbols")
    @Nullable
    @Schema(description = "User's last name", example = "Doe")
    private String surname;

    @Email(message = "Enter a valid email address.")
    @Size( max = 100, message = "email can by max 100 symbols")
    @Nullable
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Pattern(regexp = "^([+]?[\\s0-9]+)?(\\d{3}|[(]?[0-9]+[)])?([-]?[\\s]?[0-9])*$", message = "Enter a valid phone number")
    @Size( max = 12, message = "phone size max 12")
    @Nullable
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String phone;

    @Min(value = 16, message = "min age 16")
    @Max(value = 90 , message = "max age 90")
    @Nullable
    @PositiveOrZero
    @Schema(description = "User's age", example = "25")
    private Integer age;

    @Pattern(regexp = "FS|QACX|JCX|JSCX|FE|PCX|^$", message = "Enter a valid course")
    @Nullable
    @Schema(description = "User's course", example = "QACX")
    private String course;

    @Pattern(regexp = "static|online|^$" , message = "Enter a valid courseFormat.")
    @Nullable
    @Schema(description = "User's course format", example = "online")
    private String courseFormat;

    @Pattern(regexp = "pro|minimal|premium|incubator|vip|^$", message = "Enter a valid courseType.")
    @Nullable
    @Schema(description = "User's course type", example = "pro")
    private String courseType;

    @Max(2147483647)
    @Nullable
    @Schema(description = "Amount already paid by the user", example = "500")
    private Integer alreadyPaid;

    @Max(2147483647)
    @Nullable
    @Schema(description = "Total amount for course payment", example = "1000")
    private Integer sum;

    @Nullable
    @Schema(description = "User's message", example = "Thank you for registration!")
    private String msg;

    @Pattern(regexp = "In work|New|Agree|Disagree|Dubbing|^$", message = "Enter a valid status.")
    @Nullable
    @Schema(description = "Order status", example = "New")
    private String status;

    @Column(name = "utm", nullable = true)
    @Nullable
    @Schema(description = "Order's UTM parameter", example = "utm_source=google&utm_medium=cpc")
    private String utm;

    @Nullable
    private String group;
}
