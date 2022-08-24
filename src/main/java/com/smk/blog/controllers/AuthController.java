package com.smk.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smk.blog.exceptions.ApiException;
import com.smk.blog.payload.JWTAuthRequest;
import com.smk.blog.payload.JWTAuthResponse;
import com.smk.blog.security.JWTTokenHelper;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JWTTokenHelper jWTTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception {

		String username = request.getUsername();
		String password = request.getPassword();

		authenticate(username, password);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		String token = jWTTokenHelper.generateToken(userDetails);

		JWTAuthResponse jWTAuthResponse = new JWTAuthResponse();
		jWTAuthResponse.setToken(token);
		return new ResponseEntity<JWTAuthResponse>(jWTAuthResponse, HttpStatus.OK);

	}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException ex) {
			throw new ApiException("Invalid username or password");
		}

	}
}
