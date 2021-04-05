package com.everis.latam.BKDProveedoresFlama.controller;

import java.util.Arrays;import org.apache.catalina.valves.StuckThreadDetectionValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.everis.latam.BKDProveedoresFlama.Exception.BadRequestException;
import com.everis.latam.BKDProveedoresFlama.Exception.ExceptionPost;
import com.everis.latam.BKDProveedoresFlama.URLs.URLs;
import com.everis.latam.BKDProveedoresFlama.dto.AreaDto;
import com.everis.latam.BKDProveedoresFlama.dto.FinanzaRequestDto;
import com.everis.latam.BKDProveedoresFlama.dto.FinanzaResponseDto;
import com.everis.latam.BKDProveedoresFlama.dto.ProveedorDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResolucionDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitanteDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitudDto;
import com.everis.latam.BKDProveedoresFlama.dto.requestDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class RequestController {
	
	private HttpHeaders headers = new HttpHeaders();
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = URLs.inputURL, method = RequestMethod.POST, consumes= "application/json")
	public ResponseEntity<FinanzaResponseDto> ServicioPeticion(@RequestBody requestDTO req) throws BadRequestException{

			
		ProveedorDto p = new ProveedorDto();
		p = req.getProveedor();
		log.info("proveedor: "+p);
		
		
		AreaDto a = new AreaDto();
		a = req.getArea();
		log.info("area: "+a);
		
		SolicitanteDto ste = new SolicitanteDto();
		ste = req.getSolicitante();
		log.info("solicitante: "+ste);
		

		SolicitudDto stud = new SolicitudDto();
		stud = req.getSolicitud();
		log.info("solicitud: "+stud);
		
		//objeto completo para enviarlo a dal
		requestDTO reqDto = new requestDTO();
		reqDto.setArea(a);
		reqDto.setDescripcion(req.getDescripcion());
		reqDto.setProveedor(p);
		reqDto.setSolicitante(ste);
		reqDto.setSolicitud(stud);
		
		log.info("Request completa = "+reqDto);
	
		//objeto a enviar a finanzas
		FinanzaRequestDto finanzareqDto = new FinanzaRequestDto();
		
		finanzareqDto.setIdProveedor(p.getIdProveedor());
		
		finanzareqDto.setNombreArea(a.getNombreArea());
		finanzareqDto.setResponsable(a.getResponsable());
		finanzareqDto.setCeco(a.getCeco());
		finanzareqDto.setEmail(a.getEmail());
		
		finanzareqDto.setIdAprovador(ste.getIdAprobador());
		finanzareqDto.setNumero(stud.getNumero());
		finanzareqDto.setMonto(stud.getMonto());
		finanzareqDto.setIva(stud.getIva());
		finanzareqDto.setPrioridad(stud.getPrioridad());
		
		//id solicitud debe ser autoincremental segun se vaya guardando en bd
		finanzareqDto.setIdSolicitud(1);
		
		
	
		try {
			
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<FinanzaRequestDto> entity = new HttpEntity<>(finanzareqDto,headers);
			
			log.info("SOLICITANDO");
			
			
			FinanzaResponseDto res = restTemplate.exchange(URLs.finanzaAPI, HttpMethod.POST, entity, FinanzaResponseDto.class).getBody();
			
			
			
			log.info("RESPONDIDO = " + res);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			throw new BadRequestException(ExceptionPost.error);
		}
		
		
	
		
		
		
	}

}
