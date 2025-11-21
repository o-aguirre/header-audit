package header_audit.back.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeaderAnalysis {

    private String headerName;
    private boolean present;
    private String value;
    private int score;
    private String status;
    private String severity;
    private String description;
    private String recommendation;

}
