package dominio;

import accesoDatos.SolicitudModificacionDAOImpl;
import java.util.ArrayList;
import java.util.List;

public class SolicitudModificacion {
	private String motivoCambio;
	private String estado;
	private int NRC;
	private int numeroPersonal;

	public SolicitudModificacion () {
		setMotivoCambio("Solicitado");
	}

	public void setMotivoCambio (String motivoCambio) {
		this.motivoCambio = motivoCambio;
	}

	public String getMotivoCambio () {
		return motivoCambio;
	}

	public void setEstado (String estado) {
		this.estado = estado;
	}

	public String getEstado () {
		return estado;
	}

	public int getNRC () {
		return NRC;
	}

	public void setNRC (int NRC) {
		this.NRC = NRC;
	}

	public int getNumeroPersonal () {
		return numeroPersonal;
	}

	public void setNumeroPersonal (int numeroPersonal) {
		this.numeroPersonal = numeroPersonal;
	}

	public static int buscarDocente (String correo, String constraseña) {
		SolicitudModificacionDAOImpl solicitudModificacionDAO = new SolicitudModificacionDAOImpl();
		int staffNumber = solicitudModificacionDAO.buscarDocente(correo, constraseña);
		return staffNumber;
	}

	public static List<Materia> buscarMateria (int numeroPersonal) {
		List<Materia> materias = new ArrayList<>();
		SolicitudModificacionDAOImpl solicitudModificacionDAO = new SolicitudModificacionDAOImpl();
		materias = solicitudModificacionDAO.buscarMateria(numeroPersonal);
		return materias;
	}

	public static boolean guardarSolicitudModificacion (SolicitudModificacion solicitudModificacion) {
		SolicitudModificacionDAOImpl solicitudModificacionDAO = new SolicitudModificacionDAOImpl();
		boolean resultado = solicitudModificacionDAO.guardarSolicitudModificacion(solicitudModificacion);
		return resultado;
	}
}
