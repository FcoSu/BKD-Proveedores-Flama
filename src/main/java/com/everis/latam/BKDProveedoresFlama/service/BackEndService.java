package com.everis.latam.BKDProveedoresFlama.service;

import org.springframework.stereotype.Service;

import com.everis.latam.BKDProveedoresFlama.dto.AreaDto;
import com.everis.latam.BKDProveedoresFlama.dto.ProveedorDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResponseAdminDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitanteDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitudDto;

@Service
public interface BackEndService {
	
	
	
	
	ResponseAdminDto obtenerarea(ProveedorDto prov, AreaDto area, SolicitanteDto solicitante, SolicitudDto solicitud,
			String descripcion);

}
