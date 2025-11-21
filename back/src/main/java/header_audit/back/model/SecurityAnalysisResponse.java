package header_audit.back.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityAnalysisResponse {

    private String url;
    private int overallScore;
    private String securityLevel;
    private List<HeaderAnalysis> headers;
    private List<String> recommendations;
    private long analysisTimestamp;

}
