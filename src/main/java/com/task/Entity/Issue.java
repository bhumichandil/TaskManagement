package com.task.Entity;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.task.ENUM.IssuePriority;
import com.task.ENUM.IssueStatus;
import com.task.ENUM.IssueType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String issueTitle;

    @Column(unique = true, nullable = false)
    private String issueKey;

    @Column(length = 2000)
    private String issueDescription;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    private IssueStatus  issueStatus;

    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    private String assignedEmail;
    private String reporterEmail;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime dueDate;

    private Long sprintId;
    private Long epicId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIssueTitle() {
		return issueTitle;
	}
	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}
	public String getIssueKey() {
		return issueKey;
	}
	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}
	public String getIssueDescription() {
		return issueDescription;
	}
	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}
	public IssueType getIssueType() {
		return issueType;
	}
	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}
	public IssueStatus getIssueStatus() {
		return issueStatus;
	}
	public void setIssueStatus(IssueStatus issueStatus) {
		this.issueStatus = issueStatus;
	}
	public IssuePriority getPriority() {
		return priority;
	}
	public void setPriority(IssuePriority priority) {
		this.priority = priority;
	}
	public String getAssignedEmail() {
		return assignedEmail;
	}
	public void setAssignedEmail(String assignedEmail) {
		this.assignedEmail = assignedEmail;
	}
	public String getReporterEmail() {
		return reporterEmail;
	}
	public void setReporterEmail(String reporterEmail) {
		this.reporterEmail = reporterEmail;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public LocalDateTime getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	public Long getSprintId() {
		return sprintId;
	}
	public void setSprintId(Long sprintId) {
		this.sprintId = sprintId;
	}
	public Long getEpicId() {
		return epicId;
	}
	public void setEpicId(Long epicId) {
		this.epicId = epicId;
	}
    
}
