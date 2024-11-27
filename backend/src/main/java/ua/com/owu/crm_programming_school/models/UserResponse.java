package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    @Schema(description = "Unique identifier for the user", readOnly = true)
    private int id;

    @Schema(description = "User's first name", example = "John")
    private String name;

    @Schema(description = "User's last name", example = "Doe")
    private String surname;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;
}
