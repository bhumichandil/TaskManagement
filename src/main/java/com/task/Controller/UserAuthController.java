package com.task.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.DTO.AuthResponseDTO;
import com.task.DTO.ForgotPasswordDTO;
import com.task.DTO.LoggedRequestDTO;
import com.task.DTO.LoginRequestDTO;
import com.task.DTO.RegisterRequestDTO;
import com.task.DTO.ResetPasswordDTO;
import com.task.Service.UserAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/Authentication")
@RequiredArgsConstructor
public class UserAuthController {

@Autowired
	private UserAuthService userService;

	

	@PostMapping("/register")

	public ResponseEntity<String>register(@RequestBody RegisterRequestDTO register){

		userService.register(register);

	return ResponseEntity.ok("Register successful");

	}

	

	@PostMapping("/login")

	public ResponseEntity<AuthResponseDTO>login(@RequestBody LoginRequestDTO login){

		return ResponseEntity.ok(userService.login(login));

	}
	
	@PostMapping("/forgot_password")
	public ResponseEntity<String>forgotPassword(@RequestBody ForgotPasswordDTO forgotpassword){
		userService.forgotPassword(forgotpassword.userEmail);
		return ResponseEntity.ok("Reset password Email sent on your Email");
		
	}
	
	@PostMapping("/reset_password")
	public ResponseEntity<String>resetPassword(@RequestBody ResetPasswordDTO resetpassword){
		userService.resetPassword(resetpassword.token, resetpassword.newPassword);
		return ResponseEntity.ok("Password reset Successful");
	}
	
	
	@PostMapping("/loggedOut")
	public ResponseEntity<String>loggedOut(@RequestBody LoggedRequestDTO loogedOut,@RequestHeader("Authorization") String authHeader){
		String token = authHeader.substring(7);
		
		userService.loggedout(loogedOut);
		
		return ResponseEntity.ok("Logged out Successful");
	}

}





