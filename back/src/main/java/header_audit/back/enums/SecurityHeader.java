package header_audit.back.enums;

import lombok.Getter;

@Getter
public enum SecurityHeader {
    STRICT_TRANSPORT_SECURITY(
        "Strict-Transport-Security",
        15,
        "critical",
        "Forces browsers to use HTTPS connections only, preventing man-in-the-middle attacks and protocol downgrade attacks",
        "Add header: Strict-Transport-Security: max-age=31536000; includeSubDomains; preload"
    ),
    X_FRAME_OPTIONS(
        "X-Frame-Options",
        10,
        "high",
        "Prevents clickjacking attacks by controlling whether the page can be displayed in frames, iframes, or objects",
        "Add header: X-Frame-Options: DENY or X-Frame-Options: SAMEORIGIN"
    ),
    CONTENT_SECURITY_POLICY(
        "Content-Security-Policy",
        15,
        "critical",
        "Mitigates XSS, clickjacking, and code injection attacks by specifying approved sources of content",
        "Add header: Content-Security-Policy: default-src 'self'; script-src 'self' 'unsafe-inline'; object-src 'none'"
    ),
    X_CONTENT_TYPE_OPTIONS(
        "X-Content-Type-Options",
        10,
        "medium",
        "Prevents MIME type sniffing, which can lead to security vulnerabilities when browsers interpret files differently than intended",
        "Add header: X-Content-Type-Options: nosniff"
    ),
    REFERRER_POLICY(
        "Referrer-Policy",
        10,
        "medium",
        "Controls how much referrer information is sent with requests, protecting user privacy and preventing data leakage",
        "Add header: Referrer-Policy: strict-origin-when-cross-origin or Referrer-Policy: no-referrer"
    ),
    PERMISSIONS_POLICY(
        "Permissions-Policy",
        10,
        "medium",
        "Controls which browser features and APIs can be used in the document and its iframes, reducing attack surface",
        "Add header: Permissions-Policy: geolocation=(), microphone=(), camera=()"
    ),
    X_XSS_PROTECTION(
        "X-XSS-Protection",
        5,
        "low",
        "Legacy header that enables browser's built-in XSS filter (deprecated but still useful for older browsers)",
        "Add header: X-XSS-Protection: 1; mode=block"
    ),
    CROSS_ORIGIN_EMBEDDER_POLICY(
        "Cross-Origin-Embedder-Policy",
        10,
        "medium",
        "Prevents documents from loading cross-origin resources that don't explicitly grant permission, enhancing isolation",
        "Add header: Cross-Origin-Embedder-Policy: require-corp"
    ),
    CROSS_ORIGIN_OPENER_POLICY(
        "Cross-Origin-Opener-Policy",
        10,
        "medium",
        "Isolates browsing context group, preventing cross-origin documents from opening in the same browsing context",
        "Add header: Cross-Origin-Opener-Policy: same-origin"
    ),
    CROSS_ORIGIN_RESOURCE_POLICY(
        "Cross-Origin-Resource-Policy",
        10,
        "medium",
        "Protects resources from being loaded by cross-origin or cross-site contexts, preventing certain types of attacks",
        "Add header: Cross-Origin-Resource-Policy: same-origin"
    );

    private final String headerName;
    private final int maxScore;
    private final String severity;
    private final String description;
    private final String recommendation;

    SecurityHeader(
        String headerName,
        int maxScore,
        String severity,
        String description,
        String recommendation
    ) {
        this.headerName = headerName;
        this.maxScore = maxScore;
        this.severity = severity;
        this.description = description;
        this.recommendation = recommendation ;
    }

}
