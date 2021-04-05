package com.everis.latam.BKDProveedoresFlama.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FinanzaResponseDto {

	String estatus;
	int montoTotal;
	int idResolucion;
	@JsonFormat(pattern="yyyy-MM-dd")
	Date fechaResolucion;
	int idSolicitud;
}
