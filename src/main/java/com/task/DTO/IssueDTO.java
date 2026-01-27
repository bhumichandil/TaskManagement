package com.task.DTO;

import java.time.LocalDateTime;

import com.task.ENUM.IssuePriority;
import com.task.ENUM.IssueStatus;
import com.task.ENUM.IssueType;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueDTO {

    private Long id;
    private String issueKey;
    private String issueTitle;
    private String issueDescription;
    private IssueType issueType;
    private IssuePriority priority;
    private IssueStatus issueStatus;
    private String assignedEmail;
    private String reporterEmail;
    private Long sprintId;
    private Long epicId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
}
