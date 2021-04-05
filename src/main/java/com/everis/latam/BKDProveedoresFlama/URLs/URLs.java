package com.everis.latam.BKDProveedoresFlama.URLs;

public class URLs {
	
	public final static String inputURL = "/api/v1/";
	public final static String finanzaAPI = "http://localhost:8084/finanza";
	
    public final static String baseDal = "http://localhost:8083/";
	
	public final static String AreaInsert = baseDal + "area/AreaIngresar";
	public final static String AreaList = baseDal + "area/AreasListar";
	public final static String AreaSearch = baseDal + "area/AreaBuscarporId";
	
	public final static String CentroList = baseDal + "CentroCosto/CentroListar";
	public final static String CentroSearch =baseDal + "CentroCosto/CentroBuscarPorNombre?nombre=";
	
	
	public final static String EstadoSolicitudList= baseDal + "EstadoSolicitud/EstadoSolicitudListar";
	public final static String EstadoSolicitudSearch = baseDal +  "EstadoSolicitud/EstadoSolicitudBuscarId";
	
	public final static String ProveedorInsert = baseDal + "Proveedor/ProveedorIngresar";
	public final static String ProveedorList = baseDal + "Proveedor/ProveedorListar";
	public final static String ProveedorSearch = baseDal + "Proveedor/ProveedorBuscarId";
	
	public final static String ResolucionInsert = baseDal + "Resolucion/ResolucionIngresar";
	public final static String ResolucionSearch = baseDal + "Resolucion/ResolucionBuscarId";
	
	public final static String SolicitanteInsert = baseDal + "Solicitante/SolicitanteIngresar";
	public final static String SolicitanteSearch = baseDal + "Solicitante/SolicitanteBuscarId";
	
	public final static String SolicitudInsert = baseDal + "Solicitud/SolicitudIngresar";
	public final static String SolicitudUpdate = baseDal + "Solicitud/SolicitudModificar";
	public final static String SolicitudList = baseDal + "Solicitud/SolicitudListar";
	public final static String SolicitudSearch = baseDal + "Solicitud/SolicitudBuscarId";
	public final static String SolicitudListDate = baseDal + "Solicitud/SolicitudListarPorFechaAnterior";
	
	public final static String WaiverSearch = baseDal + "Waiver/WaiverSearch?id=";
	
	
	
	
	public final static String BackEndRegristro = "/RegistroDatos";
	
}
