package com.everis.latam.BKDProveedoresFlama.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ResolucionDto {
	
	private int idResolucion;
	private int solicitudId;
	private	int	montoTotal;
	private String	comentario;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private	Date fechaResolucion;
}
