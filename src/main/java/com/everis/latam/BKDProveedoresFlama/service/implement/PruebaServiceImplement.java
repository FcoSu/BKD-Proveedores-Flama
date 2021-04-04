package com.everis.latam.BKDProveedoresFlama.service.implement;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.everis.latam.BKDProveedoresFlama.URLs.URLs;
import com.everis.latam.BKDProveedoresFlama.dto.AreaDto;
import com.everis.latam.BKDProveedoresFlama.service.pruebaService;

@Service
public class PruebaServiceImplement  implements pruebaService{
	
	private HttpHeaders headers = new HttpHeaders();
	@Autowired
	RestTemplate rest;

	@Override
	public AreaDto obtenerarea(AreaDto area) {
		AreaDto nuevo =  new AreaDto();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<AreaDto> entity = new HttpEntity<>(area,headers);
		nuevo = rest.exchange(URLs.AreaInsert, HttpMethod.POST, entity, AreaDto.class).getBody();
		System.out.println(nuevo);
		
		return nuevo;
	}

}
