package ua.com.owu.crm_programming_school.models;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.owu.crm_programming_school.views.Views;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentResponse {
    @Schema(description = "Unique identifier for the comment", readOnly = true)
    private Integer id;

    @Size(min = 1, max = 255)
    @Schema(required = true, description = "Comment text", example = "This is a comment.")
    private String comment;

    @Schema(description = "Comment creation date", readOnly = true)
    private Date createdAt;


    @Schema(description = "The order id associated with the comment")
    private int orderId;

    private UserResponse manager;
}
