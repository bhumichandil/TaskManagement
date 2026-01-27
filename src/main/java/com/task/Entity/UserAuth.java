package com.task.Entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import com.task.ENUM.Role;

import lombok.*;

@Entity
@Table(name="user-auth")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class UserAuth {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long id;
	@Column(nullable=false)
	private String userName;
	
	@Column(unique=true,nullable=false)
	private String userEmail;
	
	@Column(nullable=false)
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String resetToken;
	private Date resetTokenExpiry;
	
	
	
	
//	public UserAuth() {}//NoArgsConstructor
//	public UserAuth(Long id,String userName,String userOffialEmail,String password,Role role) {
//		this.id=id;
//		this.userName=userName;
//		this.userOffialEmail=userOffialEmail;
//		this.password=password;
//		this.role=role;
//		
//	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getResetToken() {
		return resetToken;
	}
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	public Date getResetTokenExpiry() {
		return resetTokenExpiry;
	}
	public void setResetTokenExpiry(Date resetTokenExpiry) {
		this.resetTokenExpiry = resetTokenExpiry;
	}
	
	
	
	
	

}