package com.everis.latam.BKDProveedoresFlama.dto;

import lombok.Data;

@Data
public class SolicitanteDto {
	
	private int solicitanteId;
	private String nombreSolicitante;
	private String email;
	private String cargo;
	private String idAprobador;
	private String aprovador;
}
