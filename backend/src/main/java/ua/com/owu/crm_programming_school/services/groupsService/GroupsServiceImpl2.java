package ua.com.owu.crm_programming_school.services.groupsService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.com.owu.crm_programming_school.models.Group;

import java.util.List;

@Service
public class GroupsServiceImpl2 implements GroupsService{
    @Override
    public ResponseEntity<List<Group>> getAllGroups() {
        return null;
    }

    @Override
    public ResponseEntity<Group> createGroup(Group group, HttpServletResponse response) {
        return null;
    }
}
