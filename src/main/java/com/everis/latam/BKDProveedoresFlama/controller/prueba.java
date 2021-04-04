package com.everis.latam.BKDProveedoresFlama.controller;


import org.springframework.http.HttpHeaders;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.everis.latam.BKDProveedoresFlama.URLs.URLs;
import com.everis.latam.BKDProveedoresFlama.dto.AreaDto;
import com.everis.latam.BKDProveedoresFlama.dto.ProveedorDto;
import com.everis.latam.BKDProveedoresFlama.dto.requestDTO;



@RestController
@RequestMapping("/prueba")
public class prueba {
	private HttpHeaders headers = new HttpHeaders();
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "/prueba", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> getData(@RequestBody requestDTO a) {
		AreaDto area = new AreaDto(); 
		AreaDto nuevo = new AreaDto();
		area = a.getArea();
		
		
		
		
	
			
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<AreaDto> entity = new HttpEntity<>(area,headers);
			nuevo = restTemplate.exchange(URLs.AreaInsert, HttpMethod.POST, entity, AreaDto.class).getBody();
			System.out.println(nuevo);
		
		return new ResponseEntity<>(nuevo, HttpStatus.OK);
	}

}
