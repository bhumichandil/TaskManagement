package com.task.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.DTO.IssueDTO;
import com.task.ENUM.IssueStatus;
import com.task.Entity.Issue;
import com.task.Entity.IssueComment;
import com.task.Entity.Sprint;
import com.task.Repository.IssueCommentRepository;
import com.task.Repository.IssueRepository;
import com.task.Repository.SprintRepository;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private IssueCommentRepository issueCommentRepo;

    @Autowired
    private SprintRepository sprintRepo;

    private String generateIssueKey(Long id) {
        return "PROJECT_" + id;
    }

    @Transactional
    public IssueDTO createIssue(IssueDTO dto) {

        Issue issue = new Issue();

        issue.setIssueTitle(dto.getIssueTitle());
        issue.setIssueDescription(dto.getIssueDescription());
        issue.setIssueType(dto.getIssueType());
        issue.setPriority(dto.getPriority());
        issue.setIssueStatus(dto.getIssueStatus());
        issue.setAssignedEmail(dto.getAssignedEmail());
        issue.setReporterEmail(dto.getReporterEmail());
        issue.setDueDate(dto.getDueDate());

        // Save first to get ID
        issue = issueRepo.save(issue);

        // Generate key using ID
        issue.setIssueKey(generateIssueKey(issue.getId()));

        // Save again with key
        issue = issueRepo.save(issue);

        return toDTO(issue);
    }

    @Transactional
    public IssueComment addComment(Long issueId, String authorEmail, String body) {
        issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));

        IssueComment comment = new IssueComment();
        comment.setIssueId(issueId);
        comment.setAuthorEmail(authorEmail);
        comment.setBody(body);

        return issueCommentRepo.save(comment);
    }

    public IssueDTO getById(Long id) {
        Issue issue = issueRepo.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));
        return toDTO(issue);
    }

    public List<IssueDTO> getByAssignedEmail(String userOfficialEmail) {
        return issueRepo.findByAssignedEmail(userOfficialEmail)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<IssueDTO> getBySprint(Long sprintId) {
        return issueRepo.findBySprintId(sprintId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public IssueDTO updateIssueStatus(Long id, IssueStatus status, String performBy) {
        Issue issue = issueRepo.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setIssueStatus(status);
        issue.setUpdatedAt(LocalDateTime.now());

        return toDTO(issueRepo.save(issue));
    }

    @Transactional
    public Sprint createSprint(Sprint sprint) {
        return sprintRepo.save(sprint);
    }

    public List<IssueDTO> search(Map<String, String> filters) {

        if (filters.containsKey("assignee")) {
            return getByAssignedEmail(filters.get("assignee"));
        }

        if (filters.containsKey("sprint")) {
            Long sprintId = Long.valueOf(filters.get("sprint"));
            return issueRepo.findBySprintId(sprintId)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        if (filters.containsKey("status")) {
            IssueStatus status = IssueStatus.valueOf(filters.get("status"));
            return issueRepo.findByIssueStatus(status)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        return issueRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private IssueDTO toDTO(Issue issue) {
        IssueDTO dto = new IssueDTO();

        dto.setId(issue.getId());
        dto.setIssueKey(issue.getIssueKey());
        dto.setIssueTitle(issue.getIssueTitle());
        dto.setIssueDescription(issue.getIssueDescription());
        dto.setIssueType(issue.getIssueType());
        dto.setPriority(issue.getPriority());
        dto.setIssueStatus(issue.getIssueStatus());
        dto.setAssignedEmail(issue.getAssignedEmail());
        dto.setReporterEmail(issue.getReporterEmail());
        dto.setSprintId(issue.getSprintId());
        dto.setEpicId(issue.getEpicId());
        dto.setCreatedAt(issue.getCreatedAt());
        dto.setUpdatedAt(issue.getUpdatedAt());
        dto.setDueDate(issue.getDueDate());

        return dto;
    }
}
