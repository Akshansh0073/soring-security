package com.akshansh.learn_spring_security.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class HelloWorldResource {

	
	@GetMapping(value = "/get")
	public ResponseEntity<String> getMethodName() {
		return new ResponseEntity<String>("Akshansh is a good boy",HttpStatus.OK);
	}
	
	@PostMapping(value = "/post")
	public ResponseEntity<Todo> setMethod(@RequestBody Todo todo) {
		return new ResponseEntity<>(todo,HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/csrf-token")
	public CsrfToken getCsrf(HttpServletRequest http) {
		return (CsrfToken)http.getAttribute("_csrf");
	}
	
}

record Todo (String username, String password) {}
