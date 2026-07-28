package com.careerpath.careerpathai.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CertificationResponseDTO {

    private Integer id;
    private Integer userId;
    private String name;
    private String issuer;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String credentialUrl;
    private LocalDateTime createdAt;

    public CertificationResponseDTO() {}

    public CertificationResponseDTO(Integer id, Integer userId, String name,
                                     String issuer, LocalDate issueDate,
                                     LocalDate expiryDate, String credentialUrl,
                                     LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.issuer = issuer;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.credentialUrl = credentialUrl;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public String getCredentialUrl() { return credentialUrl; }
    public void setCredentialUrl(String credentialUrl) { this.credentialUrl = credentialUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
