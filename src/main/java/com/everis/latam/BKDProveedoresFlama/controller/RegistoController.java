package com.everis.latam.BKDProveedoresFlama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.everis.latam.BKDProveedoresFlama.URLs.URLs;
import com.everis.latam.BKDProveedoresFlama.dto.AreaDto;
import com.everis.latam.BKDProveedoresFlama.dto.ProveedorDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResponseAdminDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitanteDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitudDto;
import com.everis.latam.BKDProveedoresFlama.dto.requestDTO;
import com.everis.latam.BKDProveedoresFlama.service.BackEndService;
import com.everis.latam.BKDProveedoresFlama.service.pruebaService;

@RestController
@RequestMapping("/api/v2")
public class RegistoController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	pruebaService Pruebaservice;
	@Autowired
	BackEndService backendservice;

	@RequestMapping(value = URLs.BackEndRegristro, method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> getData(@RequestBody requestDTO a) {
		AreaDto area = new AreaDto();
		ProveedorDto prov = new ProveedorDto();
		SolicitanteDto solicitante = new SolicitanteDto();
		SolicitudDto solicitud = new SolicitudDto();
		String descripcion = null;
				
		area = a.getArea();
		prov = a.getProveedor();
		solicitante= a.getSolicitante();
		solicitud = a.getSolicitud();
		descripcion = a.getDescripcion();
		
		ResponseAdminDto respuesta = new ResponseAdminDto();

		respuesta = backendservice.obtenerarea(prov, area, solicitante, solicitud, descripcion);

		return new ResponseEntity<>(null, HttpStatus.OK);

	}
}
