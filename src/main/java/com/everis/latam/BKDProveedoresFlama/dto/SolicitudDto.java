package com.everis.latam.BKDProveedoresFlama.dto;

import lombok.Data;

@Data
public class SolicitudDto {
	
	
	private int solicitudId;
	private	int proveedorIdRegistro;
	private int areaId;
	private int centroId;
	private int solicitanteId;
	private int estadoSolicitudId;
	private int numero;
	private int monto;
	private float iva;
	private int prioridad;
	private String descripcion;

}
