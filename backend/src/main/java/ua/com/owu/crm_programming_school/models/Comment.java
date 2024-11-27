package ua.com.owu.crm_programming_school.models;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import ua.com.owu.crm_programming_school.views.Views;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the comment", readOnly = true)
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private Long id;

    @Column(name = "comment", nullable = false, length = 255)
    @Size(min = 1, max = 255)
    @Schema(required = true, description = "Comment text", example = "This is a comment.")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String comment;

    @Column(name = "created_at")
    @Schema(description = "Comment creation date", readOnly = true)
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private LocalDateTime createdAt;


    @Schema(description = "The order id associated with the comment")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private int orderId;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private User manager;
}
