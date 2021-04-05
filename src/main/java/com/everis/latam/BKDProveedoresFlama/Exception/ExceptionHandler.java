package com.everis.latam.BKDProveedoresFlama.Exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(value= {BadRequestException.class})
	public ResponseEntity<Object> BadRequestException(BadRequestException e){
		HttpStatus br = HttpStatus.BAD_REQUEST;
		Exception exception = new Exception(e.getMessage(),br,ZonedDateTime.now(ZoneId.of("Z")));
		return new ResponseEntity<Object>(exception,br);
	}

}
