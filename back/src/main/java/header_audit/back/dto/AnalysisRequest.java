package header_audit.back.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AnalysisRequest {

    @NotBlank(message = "URL cannot be empty")
    @Pattern(
        regexp = "^(https?://)?([\\w\\-]+\\.)+[\\w\\-]+(/[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=]*)?$",
        message = "Invalid URL format"
    )
    private String url;
    
}
