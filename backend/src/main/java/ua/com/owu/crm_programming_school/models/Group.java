package ua.com.owu.crm_programming_school.models;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.owu.crm_programming_school.views.Views;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the group", readOnly = true)
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private int id;

    @Column(name = "name", nullable = true, length = 128)
    @Size( max = 128)
    @Nullable
    @Schema(description = "Group name", example = "Marketing Team")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String name;

    public Group(String name) {
        this.name = name;
    }
}
