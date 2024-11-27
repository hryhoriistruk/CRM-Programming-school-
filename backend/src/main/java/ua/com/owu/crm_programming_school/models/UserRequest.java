package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "name is a required field")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$", message = "Invalid name")
    @Size(min = 1, max = 20, message = "Max long name 20 character")
    @Schema(description = "User's first name", example = "John")
    private String name;

    @NotBlank(message = "name is a required field")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$", message = "Invalid surname")
    @Size(min = 1, max = 20, message = "Max long surname 20 character")
    @Schema(description = "User's last name", example = "Doe")
    private String surname;

    @NotBlank(message = "email is a required field")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email address")
    @Size(min = 1, max = 254)
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;
}
