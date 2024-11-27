package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response error details")
public class ResponseError {
    @Schema(description = "Error message", example = "invalid token")
    private String error;

    @Schema(description = "Error code", example = "401")
    private int code;

    @Schema(description = "Error message", example = "One or more fields are not valid")
    private List<String> details;

    public ResponseError(String error, int code) {
        this.error = error;
        this.code = code;
    }
}