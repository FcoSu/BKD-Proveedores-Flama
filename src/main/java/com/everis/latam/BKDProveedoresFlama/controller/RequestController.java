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
import com.everis.latam.BKDProveedoresFlama.dto.ResolucionResponseDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResponseAdminDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitanteDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitudDto;
import com.everis.latam.BKDProveedoresFlama.dto.requestDTO;
import com.everis.latam.BKDProveedoresFlama.service.BackEndService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v2")
public class RequestController {
	
	private HttpHeaders headers = new HttpHeaders();
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	BackEndService backendservice;
	
	@RequestMapping(value = "/RegistroDatos", method = RequestMethod.POST, consumes= "application/json")
	public ResponseEntity<ResponseAdminDto> ServicioPeticion(@RequestBody requestDTO req) throws BadRequestException{

		AreaDto area = new AreaDto();
		ProveedorDto prov = new ProveedorDto();
		SolicitanteDto solicitante = new SolicitanteDto();
		SolicitudDto solicitud = new SolicitudDto();
		String descripcion = null;
		SolicitudDto solicitudFinal = new SolicitudDto();
		
		boolean MontoEnRango = false;
		boolean AprobadorEnOrden = false;
		
		//mapeo
		prov = req.getProveedor();
		log.info("Mapeo de proveedor, proveedor: " + prov);
		area = req.getArea();
		log.info("Mapeo de area, area: " + area);
		solicitante= req.getSolicitante();
		log.info("Mapeo de Solicitante, solicitante: " + solicitante);
		solicitud = req.getSolicitud();
		log.info("Mapeo de solicitud, solicitud: "+ solicitud);
		descripcion = req.getDescripcion();
		log.info("Mapeo de Descripcion, descripcion: " + descripcion);
		
		
		
		
		solicitudFinal = backendservice.obtenerarea(prov, area, solicitante, solicitud, descripcion);
		
		MontoEnRango = backendservice.ConsultarMontoenRango(area, solicitudFinal);
		if (MontoEnRango == false && solicitudFinal.getPrioridad() ==1) {
			AprobadorEnOrden = backendservice.ConsultarAprobador(solicitante);
		}
		
		log.info("esta en rango: "+ MontoEnRango);
		log.info("waiver: " + AprobadorEnOrden);
		log.info("Solicitud completa = "+solicitudFinal);
		
		//comprobacion si no cumple con los requisitos se rechaza
		if(!MontoEnRango && !AprobadorEnOrden ) {
			ResolucionDto rechazada =  backendservice.RechazarSolicitud(solicitudFinal);
			//enviar rechazo a servicio, el RechazarSolicitud debe retornar un resolucion dto con el cual armar el response a servicio
		}
		
		
		
		/*
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
			
			ResolucionResponseDto rs = new ResolucionResponseDto();
			rs.setComentario("comentariozzz");
			rs.setIdResolucion(res.getIdResolucion());
			rs.setMontoTotal(res.getMontoTotal());
			
			ResponseAdminDto response = new ResponseAdminDto();
			response.setStatus(res.getEstatus());
			response.setResolucion(rs);
			
			log.info("RESPONDIDO = " + response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new BadRequestException(ExceptionPost.error);
		}
		*/
		
		
	return null;
		
		
		
	}

}
