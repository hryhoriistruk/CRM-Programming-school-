package ua.com.owu.crm_programming_school.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.owu.crm_programming_school.filter.EmptyStringFilter;
import ua.com.owu.crm_programming_school.views.Views;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the order", readOnly = true)
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private Long id;

    @Column(name = "name", nullable = true, length = 25)
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]*$")
    @Size( max = 25)
    @Nullable
    @Schema(description = "User's first name", example = "John")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String name;

    @Column(name = "surname", nullable = true, length = 25)
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]*$")
    @Size( max = 25)
    @Nullable
    @Schema(description = "User's last name", example = "Doe")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String surname;

    @Column(name = "email", nullable = true, unique = true, length = 100)
    @Email
    @Size( max = 100)
    @Nullable
    @Schema(description = "User's email address", example = "john.doe@example.com")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String email;

    @Column(name = "phone", nullable = true, length = 12)
    @Pattern(regexp = "^([+]?[\\s0-9]+)?(\\d{3}|[(]?[0-9]+[)])?([-]?[\\s]?[0-9])*$")
    @Size( max = 12)
    @Nullable
    @Schema(description = "User's email address", example = "john.doe@example.com")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String phone;

    @Column(name = "age", nullable = true)
    @Max(90)
    @Nullable
    @PositiveOrZero
    @Schema(description = "User's age", example = "25")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private Integer age;

    @Column(name = "course", nullable = true)
    @Pattern(regexp = "FS|QACX|JCX|JSCX|FE|PCX|^$")
    @Nullable
    @Schema(description = "User's course", example = "QACX")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String course;

    @Column(name = "course_format", nullable = true)
    @Pattern(regexp = "static|online|^$")
    @Nullable
    @Schema(description = "User's course format", example = "online")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String courseFormat;

    @Column(name = "course_type", nullable = true)
    @Pattern(regexp = "pro|minimal|premium|incubator|vip|^$")
    @Schema(description = "User's course type", example = "pro")
    @Nullable
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String courseType;

    @Column(name = "alreadyPaid", nullable = true)
    @Max(2147483647)
    @Nullable
    @Schema(description = "Amount already paid by the user", example = "500")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private Integer alreadyPaid;

    @Column(name = "sum", nullable = true)
    @Max(2147483647)
    @Nullable
    @Schema(description = "Total amount for course payment", example = "1000")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private Integer sum;


    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = EmptyStringFilter.class)
    @Column(name = "msg", nullable = true)
    @Nullable
    @Schema(description = "User's message", example = "Thank you for registration!")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String msg;

    @Column(name = "status", nullable = true)
    @Pattern(regexp = "In work|New|Agree|Disagree|Dubbing|^$")
    @Nullable
    @Schema(description = "Order status", example = "New")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Nullable
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private User manager;

    @Column(name = "created_at", nullable = true)
    @Schema(description = "Order creation date")
    @Nullable
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private LocalDateTime created;

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = EmptyStringFilter.class)
    @Column(name = "utm", nullable = true)
    @Nullable
    @Schema(description = "Order's UTM parameter", example = "utm_source=google&utm_medium=cpc")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String utm;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_comment",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private List<Comment> comments;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private Group group;
}
