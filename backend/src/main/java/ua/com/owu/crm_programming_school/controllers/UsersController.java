package ua.com.owu.crm_programming_school.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.owu.crm_programming_school.models.OrderPaginated;
import ua.com.owu.crm_programming_school.models.User;
import ua.com.owu.crm_programming_school.services.usersService.UsersService;
import ua.com.owu.crm_programming_school.views.Views;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@Tag(name = "users")
public class UsersController {
    private UsersService usersService;

    public UsersController(@Qualifier("usersServiceImpl1") UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/me")
    @Operation(summary = "get current user",
            description = "Get current user",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class
                                    )
                            ))
            })
    @JsonView(Views.Level2.class)
    public ResponseEntity<User> getCurrentUser(Principal principal){
        return usersService.getCurrentUser(principal);
    }

}
