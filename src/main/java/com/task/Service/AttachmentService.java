package com.task.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.task.Storage.StorageService;
import com.task.Entity.Attachment;
import com.task.Repository.AttachmentRepository;

@Service
public class AttachmentService {
	
@Autowired
private AttachmentRepository attachRepo;

@Autowired
private StorageService storage;


@Transactional
public Attachment upload(Long issueId,MultipartFile file,String uploadBy) {
	
	String cloudURL = storage.store(file,"issues/"+issueId);
	Attachment attach= new Attachment();
	
	attach.setIssueId(issueId);
	attach.setFileName(file.getOriginalFilename());
	attach.setContentType(file.getContentType());
	attach.setSizeBytes(file.getSize());
	attach.setStoragePath(cloudURL);
	attach.setUpLoadedBy(uploadBy);
	
	return attachRepo.save(attach);
			
}

public String download(Long id) {
	throw new UnsupportedOperationException("User Clodinary URL directly for downloading files.");
}
}
