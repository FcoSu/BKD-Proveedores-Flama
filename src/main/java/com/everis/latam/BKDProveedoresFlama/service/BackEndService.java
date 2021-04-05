package com.everis.latam.BKDProveedoresFlama.service;

import org.springframework.stereotype.Service;

import com.everis.latam.BKDProveedoresFlama.dto.AreaDto;
import com.everis.latam.BKDProveedoresFlama.dto.ProveedorDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResolucionDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResolucionResponseDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResponseDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitanteDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitudDto;

@Service
public interface BackEndService {
	
	
	
	
	SolicitudDto obtenerarea(ProveedorDto prov, AreaDto area, SolicitanteDto solicitante, SolicitudDto solicitud,
			String descripcion);

	boolean ConsultarMontoenRango(AreaDto AreaConsultaCentro, SolicitudDto solicitud);

	boolean ConsultarAprobador(SolicitanteDto solicitante);

	ResponseDto RechazarSolicitud(SolicitudDto solicitudFinal);

}
