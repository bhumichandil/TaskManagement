package com.task.DTO;

import com.task.ENUM.Role;
import lombok.*;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequestDTO {
	
	public String userName;
	public String userEmail;
	public String password;
	public Role role;
	
	

}