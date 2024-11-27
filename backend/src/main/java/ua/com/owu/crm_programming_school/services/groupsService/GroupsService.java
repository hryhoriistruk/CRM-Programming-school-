package ua.com.owu.crm_programming_school.services.groupsService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import ua.com.owu.crm_programming_school.models.Group;

import java.util.List;

public interface GroupsService {
    ResponseEntity<List<Group>> getAllGroups();

    ResponseEntity<Group> createGroup(Group group, HttpServletResponse response);
}
