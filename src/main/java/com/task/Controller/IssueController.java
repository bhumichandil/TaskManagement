package com.task.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//
//import com.TaskManage.Enum.IssueStatus;
//import com.TaskMange.DTO.IssueDTO;
//import com.TaskMange.Entity.IssueComment;
//import com.TaskMange.Service.IssueService;

import com.task.DTO.IssueDTO;
import com.task.ENUM.IssueStatus;
import com.task.Entity.IssueComment;
import com.task.Service.IssueService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/issues")
@AllArgsConstructor
public class IssueController {
	
	@Autowired
	private IssueService issueService;
	
	
	@PostMapping("/create")
	public ResponseEntity<IssueDTO>createIssue(@RequestBody IssueDTO issue){
		return ResponseEntity.ok(issueService.createIssue(issue));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<IssueDTO>getById(@PathVariable Long id){
		return ResponseEntity.ok(issueService.getById(id));
	}
	@GetMapping("/{email}")
	public ResponseEntity<List<IssueDTO>> getByEmail(@PathVariable String userOfficialEmail){
		return ResponseEntity.ok(issueService.getByAssignedEmail(userOfficialEmail));
	}
	
	@PostMapping("/{id}/comment")
	public ResponseEntity<IssueComment>addComment(@PathVariable Long id,
			                                      @PathVariable String author,
			                                      @PathVariable String body){
		return ResponseEntity.ok(issueService.addComment(id, author, body));
	}
	
	@GetMapping("/{sprintId}")
	public ResponseEntity<List<IssueDTO>>getBySprint(@PathVariable Long sprintid){
		return ResponseEntity.ok(issueService.getBySprint(sprintid));
	}
	
	@PutMapping("/{id}/updateStatus")
	public ResponseEntity<IssueDTO>updateStatus(@PathVariable Long id,
			                                    @RequestParam IssueStatus status,
			                                    @RequestParam String performBy){
		return ResponseEntity.ok(issueService.updateIssuStatus(id, status, performBy));
	}

	@GetMapping("/search")
	public ResponseEntity<List<IssueDTO>>search(@RequestParam Map<String ,String> filter){
		return ResponseEntity.ok(issueService.search(filter));
	}
}

