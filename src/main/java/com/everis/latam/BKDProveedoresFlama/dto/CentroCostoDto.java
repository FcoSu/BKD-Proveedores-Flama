package com.everis.latam.BKDProveedoresFlama.dto;

import lombok.Data;

@Data
public class CentroCostoDto {
	
	
	private Integer centroId;
	private Integer idAprobador;
	private String centroNombre;
	private Integer montoMaximo;
	private String moneda;

}
