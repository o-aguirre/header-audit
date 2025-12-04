package header_audit.back.controller;

import header_audit.back.dto.AnalysisRequest;
import header_audit.back.model.SecurityAnalysisResponse;
import header_audit.back.service.HeaderAnalyzerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/security-analyzer")
@RequiredArgsConstructor
@Slf4j
public class SecurityAnalyzerController {

    private final HeaderAnalyzerService headerAnalyzerService;

    @PostMapping("/analyze")
    public ResponseEntity<SecurityAnalysisResponse> analyzeSecurityHeaders(
            @Valid @RequestBody AnalysisRequest request) {

        log.info("Received analysis request for URL: {}", request.getUrl());

        SecurityAnalysisResponse response = headerAnalyzerService.analyzerUrl(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Security Header Analyzer is running!");
    }

}
