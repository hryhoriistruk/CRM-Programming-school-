package ua.com.owu.crm_programming_school.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentListResponse {
    List<CommentResponse> comments;
}
