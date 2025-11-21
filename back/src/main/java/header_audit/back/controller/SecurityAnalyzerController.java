package header_audit.back.controller;

import header_audit.back.dto.AnalysisRequest;
import header_audit.back.model.SecurityAnalysisResponse;
import header_audit.back.service.HeaderAnalyzerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/security-analyzer")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class SecurityAnalyzerController {

    private final HeaderAnalyzerService headerAnalyzerService;

    @PostMapping("/analyze")
    public ResponseEntity<SecurityAnalysisResponse> analyzeSecurityHeaders(
            @Valid @RequestBody AnalysisRequest request) {

        log.info("Received analysis request for URL: {}", request.getUrl());

        try {
            SecurityAnalysisResponse response = headerAnalyzerService.analyzerUrl(request);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.error("Invalid URL provided: {}", request.getUrl(), e);
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            log.error("Error analyzing URL: {}", request.getUrl(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Security Header Analyzer is running!");
    }

}
