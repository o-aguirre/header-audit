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
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/security-analyzer")
@RequiredArgsConstructor
@Slf4j
public class SecurityAnalyzerController {

    private final HeaderAnalyzerService headerAnalyzerService;

    @PostMapping("/analyze")
    public Mono<ResponseEntity<SecurityAnalysisResponse>> analyzeSecurityHeaders(
            @Valid @RequestBody AnalysisRequest request) {

        log.info("Received analysis request for URL: {}", request.getUrl());

        return headerAnalyzerService.analyzerUrl(request)
            .map(ResponseEntity::ok);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Security Header Analyzer is running!");
    }

}
