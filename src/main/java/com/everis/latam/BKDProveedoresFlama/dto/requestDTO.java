package com.everis.latam.BKDProveedoresFlama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class requestDTO {
	
	private ProveedorDto proveedor;
	   private AreaDto area;
	   private SolicitanteDto solicitante;
	   private SolicitudDto solicitud;
	   private String descripcion;
	   
}
