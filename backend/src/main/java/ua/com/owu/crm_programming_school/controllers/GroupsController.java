package ua.com.owu.crm_programming_school.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.owu.crm_programming_school.models.Group;
import ua.com.owu.crm_programming_school.models.GroupListResponse;
import ua.com.owu.crm_programming_school.models.ResponseError;
import ua.com.owu.crm_programming_school.services.groupsService.GroupsService;

import java.util.List;

@RestController
@Tag(name = "groups")
@RequestMapping("/groups")
public class GroupsController {
private GroupsService groupsService;

 public GroupsController (@Qualifier("groupsServiceImpl1") GroupsService groupsService){
     this.groupsService = groupsService;
 }

 @GetMapping("")
 @Operation(summary = "get all groups",
         description = "Get all groups",
         responses = {
                 @ApiResponse(
                         description = "success",
                         responseCode = "200",
                         content = @Content(
                                 mediaType = "application/json",
                                 schema = @Schema(implementation = GroupListResponse.class)))})
    public ResponseEntity<List<Group>> getAllGroups() {
     return groupsService.getAllGroups();
 }


    @PostMapping("")
    @Operation(summary = "create group",
            description = "Create group",
            responses = {
                    @ApiResponse(
                            description = "created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Group.class))),
                    @ApiResponse(
                            description = "Bad request",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseError.class),
                                    examples = @ExampleObject(value = "{\"error\": \"Group is not valid\", \"code\": 400, \"details\": [\"Field 'group': Group already exists.\"]}")))
            })
    public ResponseEntity<Group> createGroup(@RequestBody @Valid Group group, HttpServletResponse response) {
        return groupsService.createGroup(group, response);
    }

}
