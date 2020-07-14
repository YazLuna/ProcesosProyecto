package accesoDatos;

import dominio.Materia;
import dominio.SolicitudModificacion;
import java.util.List;

public interface ISolicitudModificacionDAOImpl {
	List<Materia> buscarMateria (int numeroPersonal);
	int buscarDocente(String correo, String contrseña);
	boolean guardarSolicitudModificacion (SolicitudModificacion solicitudModificacion);
	boolean tienePlanCurso(int numeroPersonal);
}
