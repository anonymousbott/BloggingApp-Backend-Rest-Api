package com.smk.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smk.blog.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,"false");
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
		
		Map<String,String> errorMap = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String field = ((FieldError) error).getField();
			String defaultMessage = error.getDefaultMessage();
			errorMap.put(field, defaultMessage);
		});
		
		return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ApiResponse> emailNotFoundExceptionHandler(EmailNotFoundException ex) {
		String message = ex.getMessage(); 
		ApiResponse apiResponse = new ApiResponse(message,"false");
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BearerMissingTokenExceptionOrEmpty.class)
	public ResponseEntity<ApiResponse> bearerMissingTokenExceptionOrEmptyHandler(BearerMissingTokenExceptionOrEmpty ex){
		String message = ex.getMessage(); 
		ApiResponse apiResponse = new ApiResponse(message,"false");
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	//InvalidTokenException
	
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ApiResponse> InvalidTokenExceptionHandler(InvalidTokenException ex){
		String message = ex.getMessage(); 
		ApiResponse apiResponse = new ApiResponse(message,"false");
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.UNAUTHORIZED);
	}
	
	//ApiException
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> ApiExceptionExceptionHandler(ApiException ex){
		String message = ex.getMessage(); 
		ApiResponse apiResponse = new ApiResponse(message,"false");
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.UNAUTHORIZED);
	}
	
}
