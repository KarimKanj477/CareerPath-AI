package com.careerpath.careerpathai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CertificationRequestDTO {

    @NotBlank(message = "Certification name is required")
    @Size(max = 150, message = "Certification name cannot exceed 150 characters")
    private String name;

    @Size(max = 150, message = "Issuer cannot exceed 150 characters")
    private String issuer;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    @Size(max = 255, message = "Credential URL cannot exceed 255 characters")
    private String credentialUrl;

    public CertificationRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }
}
