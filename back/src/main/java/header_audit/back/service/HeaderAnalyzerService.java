package header_audit.back.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import header_audit.back.dto.AnalysisRequest;
import header_audit.back.enums.SecurityHeader;
import header_audit.back.model.HeaderAnalysis;
import header_audit.back.model.SecurityAnalysisResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HeaderAnalyzerService {
    private final WebClient webClient;

    public HeaderAnalyzerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public SecurityAnalysisResponse analyzerUrl(AnalysisRequest request) {
        String url = normalizeUrl(request.getUrl());

        log.info("Starting security analysis for URL: {}", url);

        try {
            Map<String, List<String>> headers = fetchHeaders(url);

            List<HeaderAnalysis> headerAnalyses = analyzeSecurityHeaders(headers);

            int overallScore = calculateOverallScore(headerAnalyses);

            String securityLevel = determineSecurityLevel(overallScore);

            List<String> recommendations = generateRecommendations(headerAnalyses);

            return SecurityAnalysisResponse
                .builder()
                .url(url)
                .overallScore(overallScore)
                .securityLevel(securityLevel)
                .headers(headerAnalyses)
                .recommendations(recommendations)
                .analysisTimestamp(Instant.now().toEpochMilli())
                .build();
        } catch (WebClientResponseException e) {
            log.error("Error fetching headers from URL: {}", url, e);
            throw new RuntimeException("Failed to analyze URL: " + e.getStatusCode());
        } catch (Exception e) {
            log.error("Unexpected error analyzing URL: {}", url, e);
            throw new RuntimeException("Failed to analyze URL: " + e.getMessage());
        }
    }

    private String normalizeUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }

    private Map<String, List<String>> fetchHeaders(String url) {
        return webClient.head()
                .uri(url)
                .retrieve()
                .toBodilessEntity()
                .map(response -> {
                    Map<String, List<String>> headerMap = new java.util.HashMap<>();
                    response.getHeaders().forEach((key, values) -> 
                        headerMap.put(key, new ArrayList<>(values))
                    );
                    return headerMap;
                })
                .block();
    }

    private List<HeaderAnalysis> analyzeSecurityHeaders (Map<String, List<String>> headers) {
        List<HeaderAnalysis> analyses = new ArrayList<>();

        for (SecurityHeader securityHeader : SecurityHeader.values()) {
            HeaderAnalysis analysis = analyzeIndividualHeader(securityHeader, headers);
            analyses.add(analysis);
        }
        return analyses;
    }

    private HeaderAnalysis analyzeIndividualHeader(
        SecurityHeader securityHeader,
        Map<String, List<String>> headers
    ) {
        String headerName = securityHeader.getHeaderName();
        List<String> headerValues = findHeaderCaseInsensitive(headers, headerName);
        boolean present = headerValues != null && !headerValues.isEmpty();
        String value = present ? String.join(", ", headerValues) : null;
        int score = present ? securityHeader.getMaxScore() : 0;
        String status = present ? "Present" : "Missing";

        return HeaderAnalysis.builder()
            .headerName(headerName)
            .present(present)
            .value(value)
            .score(score)
            .status(status)
            .severity(securityHeader.getSeverity())
            .description(securityHeader.getDescription())
            .recommendation(securityHeader.getRecommendation())
            .build();
    
    }

    private List<String> findHeaderCaseInsensitive(Map<String, List<String>> headers, String headerName) {
        return headers
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().equalsIgnoreCase(headerName))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse(null);
    }

    private int calculateOverallScore(List<HeaderAnalysis> analyses) {
        int totalPossible = analyses.stream()
            .mapToInt(HeaderAnalysis::getScore)
            .sum();
        int maxPossible = SecurityHeader.values().length * 15;

        return (int) Math.round((totalPossible * 100.0) / maxPossible);
    }

    private String determineSecurityLevel(int score) {
        if (score >= 90) return "Excellent";
        if (score >= 70) return "Good";
        if (score >= 50) return "Fair";
        if (score >= 30) return "Poor";
        return "Critical";
    }

    private List<String> generateRecommendations(List<HeaderAnalysis> analyses) {
        List<String> recommendations = new ArrayList<>();

        analyses.stream()
            .filter(analysis -> !analysis.isPresent())
            .forEach(analysis -> recommendations.add(
                String.format("[%s] %s: %s",
                    analysis.getSeverity().toUpperCase(),
                    analysis.getHeaderName(),
                    analysis.getRecommendation()
                )
            ));
        
        return recommendations;
    }
}
