package com.health.ge.jw.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "file_processing_status",schema = "public")
public class FileProcessingStatus {

    public enum ProcessStatus {
        PENDING, SUCCESS, FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "tenant_id")
    private String tenantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProcessStatus status;

    @Column(name = "error_message")
    private String errorMessage;

    public FileProcessingStatus() {
    }

    public FileProcessingStatus(String fileName, String tenantId, ProcessStatus status, String errorMessage) {
        this.fileName = fileName;
        this.tenantId = tenantId;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
