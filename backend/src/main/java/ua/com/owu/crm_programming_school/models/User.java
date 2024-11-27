package ua.com.owu.crm_programming_school.models;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.com.owu.crm_programming_school.views.Views;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    @Schema(description = "Unique identifier for the user", readOnly = true)
    private int id;

    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    @NotBlank(message = "name is a required field")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 20)
    @Schema(required = true, description = "User's first name", example = "John")
    private String name;

    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    @NotBlank(message = "name is a required field")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 254)
    @Schema(required = true, description = "User's last name", example = "Doe")
    private String surname;

    @Column(unique = true)
    @JsonView(value = {Views.Level1.class, Views.Level2.class})
    @NotBlank(message = "email is a required field")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email address")
    @Size(min = 1, max = 254)
    @Schema(required = true, description = "User's email address", example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "password cannot be empty")
    @JsonView(value = {Views.Level1.class})
    @Size(min = 5, max = 128, message = "The password must be between 5 and 128 characters long.")
    @Hidden
    private String password;

    @JsonView(value = {Views.Level1.class})
    @Hidden
    private String refreshToken;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JsonView(value = Views.Level1.class)
    @Hidden
    private List<Role> roles;

    @JsonView(value = {Views.Level1.class, Views.Level2.class})
    @Schema(description = "Is user active", readOnly = true)
    private boolean is_active;

    @JsonView(value = {Views.Level1.class, Views.Level2.class})
    @Schema(description = "Is user a superuser", readOnly = true)
    private Boolean is_superuser;

    @JsonView(value = {Views.Level1.class, Views.Level2.class})
    @Schema(description = "Is user staff", readOnly = true)
    private Boolean is_staff;

    @JsonView(value = {Views.Level1.class, Views.Level2.class})
    @Schema(description = "User create date", readOnly = true)
    private LocalDateTime createdAt;

    @JsonView(value = {Views.Level1.class, Views.Level2.class})
    @Schema(description = "User update date", readOnly = true)
    private LocalDateTime updatedAt;

    @JsonView(value = {Views.Level1.class, Views.Level2.class})
    @Schema(description = "User last login date", readOnly = true)
    private LocalDateTime lastLogin;

    @JsonView(value = Views.Level1.class)
    @Hidden
    private int tokenVersion = 0;

    @JsonView(value = Views.Level1.class)
    @Hidden
    private String activationToken;

    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public User(UserDetails loadUserByEmail) {
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    @Hidden
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
        return authorities;
    }

    @Override
    @Hidden
    public String getUsername() {
        return this.email;
    }

    @Override
    @Hidden
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Hidden
    public boolean isAccountNonLocked() {
        return this.is_active;
    }

    @Override
    @Hidden
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Hidden
    public boolean isEnabled() {
        return true;
    }

}