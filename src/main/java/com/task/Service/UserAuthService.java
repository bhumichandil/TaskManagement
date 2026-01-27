

package com.task.Service;


import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import com.task.Enum.Role;
import com.task.DTO.AuthResponseDTO;
import com.task.DTO.LoggedRequestDTO;
import com.task.DTO.LoginRequestDTO;
import com.task.DTO.RegisterRequestDTO;
import com.task.ENUM.Role;
import com.task.Entity.TokenBlockList;
import com.task.Entity.UserAuth;
import com.task.Repository.TokenBlockListRepository;
import com.task.Repository.UserAuthRepository;
import com.task.Security.JWTUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthService {
	
	@Autowired
	private UserAuthRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private TokenBlockListRepository tokenBlockRepo;
	
	
	@Autowired
	private EmailService emailService;
	
	
	
	public void register(RegisterRequestDTO register) {
		
//		 if(userRepo.findByUserOfficialEmail(register.userOfficialEmail).isPresent()) {
//			  throw new RuntimeException("User already exist");
//		  }
		UserAuth user = new UserAuth();
		
		user.setUserName(register.userName);
		user.setUserEmail(register.userEmail);
		user.setPassword(passwordEncoder.encode(register.password));
		user.setRole(Role.valueOf(register.role.name()));
//		user.setRole(register.role);
		
	  userRepo.save(user);
	  
	  if(userRepo.findByUserEmail(register.userEmail).isPresent()) {
		  throw new RuntimeException("User already exist");
	  }
	  
	}
	
	
	
	public AuthResponseDTO login(LoginRequestDTO login ) {
		UserAuth user = userRepo.findByUserEmail(login.userEmail).orElseThrow(()-> new RuntimeException("User not found"));
		
		if(!passwordEncoder.matches(login.password, user.getPassword())) {
			throw new RuntimeException("Invalid credential");
			
		}
		
		String token = jwtUtil.generateToken(user);
		
		return new AuthResponseDTO(token,"Login successful");
	}
	
	

	public void forgotPassword(String userEmail) {
		
		UserAuth user = userRepo.findByUserEmail(userEmail).orElseThrow(()-> new RuntimeException("User not found"));
		
		String token = UUID.randomUUID().toString();
		
		user.setResetToken(token);
		user.setResetTokenExpiry(new Date(System.currentTimeMillis()+10*60*1000));
		userRepo.save(user);
		
		String resetToken="http://localhost:5050//api/Authentication/rest_password?token="+token;
		
//		System.out.println("Reset Token"+token);
		
		emailService.sentResetEMail(user.getUserEmail(), resetToken);
		
		
	}
	
	public void resetPassword(String token, String newPassword) {
		
		UserAuth user = userRepo.findByResetToken(token).orElseThrow(()-> new RuntimeException("Invalid token"));
		
		if(user.getResetTokenExpiry().before(new Date())) {
			
			user.setPassword(passwordEncoder.encode(newPassword));
			user.setResetToken(token);
			user.setResetTokenExpiry(null);
			
			userRepo.save(user);
		}
	}
	
	
	public void loggedout(LoggedRequestDTO loggedOut) {
		
		Claims claims = jwtUtil.getClaims(loggedOut.token);
		
		TokenBlockList killToken = new TokenBlockList();
		killToken.setToken(loggedOut.token);
		killToken.setExpiry(claims.getExpiration());
		
		tokenBlockRepo.save(killToken);
		
	}
}