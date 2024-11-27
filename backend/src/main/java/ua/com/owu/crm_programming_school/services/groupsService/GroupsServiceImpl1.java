package ua.com.owu.crm_programming_school.services.groupsService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.com.owu.crm_programming_school.dao.GroupDAO;
import ua.com.owu.crm_programming_school.models.Group;
import ua.com.owu.crm_programming_school.models.ResponseError;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupsServiceImpl1 implements GroupsService {
    private GroupDAO groupDAO;

    @Override
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupDAO.findAll();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Group> createGroup(Group group, HttpServletResponse response) {
        if (group.getName().isEmpty()) {

            response.setHeader("CreateGroupError", "Group is not valid");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            ResponseError responseError = ResponseError
                    .builder()
                    .error("Group is not valid")
                    .code(400)
                    .details(Collections.singletonList("Field 'group': Group name cannot by empty."))
                    .build();
            try {
                response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

        List<String> singleGroup = groupDAO.findAll().stream().map(Group::getName).toList();

        if (singleGroup.contains(group.getName())) {
            response.setHeader("CreateGroupError", "Group is not valid");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            ResponseError responseError = ResponseError
                    .builder()
                    .error("Group is not valid")
                    .code(400)
                    .details(Collections.singletonList("Field 'group': Group already exists."))
                    .build();
            try {
                response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


        } else {
            Group savedGroup = groupDAO.save(new Group(group.getName()));
            return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
        }
    }

}
