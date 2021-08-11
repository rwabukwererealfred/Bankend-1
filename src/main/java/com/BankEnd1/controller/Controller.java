package com.BankEnd1.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.BankEnd1.dto.ResponseMessage;
import com.BankEnd1.model.Users;
import com.BankEnd1.service.IUserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value="api/")
public class Controller {

	@Autowired
	private IUserService userService;
	
	
	@PostMapping(value="createToken")
	public ResponseEntity<?>createToken(@RequestParam("username")String username){
		try {
			String token = getJWTToken(username);
			return new ResponseEntity<>(token, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
	
	@PostMapping("uploadFile")
	public ResponseEntity<?>saveToList(@RequestParam("file")MultipartFile file){
		try {
			userService.saveToList(file);
			return new ResponseEntity<>(new ResponseMessage("well uploaded"),HttpStatus.OK);
		} catch (Exception e) {
			//e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("users")
	public List<Users> userList(){
		return userService.usersList();
	}
	
	@PostMapping(value="saveUser")
	public ResponseEntity<?>saveIntoDB(){
		try {
			userService.saveIntoDB();
			return new ResponseEntity<>(new ResponseMessage("well successfull saved"),HttpStatus.OK);
		}catch(Exception e) {
			//e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
