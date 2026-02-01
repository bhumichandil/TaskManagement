package com.task.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


@Service
public abstract class LocalStorageService implements StorageService{
	
	@Autowired
	private Cloudinary clodinary;
	
	
	
	private final Path base= Paths.get("upload");
	
	public LocalStorageService() {
		try {
			Files.createDirectories(base);
		} catch (Exception e) {
			throw new RuntimeException("Clouldn't initialize local storage");
		}
	}
	
	@Override
	public String store(MultipartFile file,String folder) {
		try {
			Path dir = base.resolve(folder);
			Files.createDirectories(dir);
			
			String name= UUID.randomUUID()+"_"+file.getOriginalFilename();
			Path path=dir.resolve(name);
			
			Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
			return path.toString();
		} catch (Exception e) {
			throw new RuntimeException("file upload failed",e);
		}
	}
	
	@Override
	public byte[] read(String storagePath) {
		try {
			
			return Files.readAllBytes(Paths.get(storagePath));
		} catch (Exception e) {
			throw new RuntimeException("File read failed");
			
		}
	}
	
	@Override
	public void delete(String publicId) {
		try {
			clodinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
			
		} catch (IOException e) {
			
			throw new RuntimeException("Failed to delete from cloud");
		}
	}
}
