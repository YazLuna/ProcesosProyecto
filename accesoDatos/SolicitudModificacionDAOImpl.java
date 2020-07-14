package accesoDatos;

import dominio.Materia;
import dominio.Numero;
import dominio.SolicitudModificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolicitudModificacionDAOImpl {
	private final Connexion connexion;
	private Connection connection;
	private ResultSet results;

	public SolicitudModificacionDAOImpl (){
		connexion= new Connexion();
	}

	public List<Materia> buscarMateria (int numeroPersonal){
		List<Materia> materias = new ArrayList<>();
		try{
			connection = connexion.getConnection();
			String queryMateria = "SELECT nombre, NRC FROM Materia, Docente, PlanCurso WHERE Materia.numeroPersonal = Docente.numeroPersonal AND" +
					" Docente.numeroPersonal =? AND PlanCurso.NRC = Materia.NRC";
			PreparedStatement sentence = connection.prepareStatement(queryMateria);
			sentence.setInt(1, numeroPersonal);
			results= sentence.executeQuery();
			while(results.next()){
				Materia materia = new Materia();
				materia.setNombre(results.getString("nombre"));
				materia.setNRC(results.getInt("NRC"));
				materias.add(materia);
			}
		}catch (SQLException ex){
			Logger.getLogger(SolicitudModificacionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}finally{
			connexion.closeConnection();
		}
		return materias;
	}

	public int buscarDocente(String correo, String contrseña){
		int numeroPersonal = Numero.CERO.getNumero();
		try{
			connection = connexion.getConnection();
			String queryDocente = "SELECT numeroPersonal FROM Cuenta INNER JOIN Docente WHERE Cuenta.RFC = Docente.RFC AND" +
					" Cuenta.correo =? AND Cuenta.contrasenia =?";
			PreparedStatement sentence = connection.prepareStatement(queryDocente);
			sentence.setString(1, correo);
			sentence.setString(2, contrseña);
			results= sentence.executeQuery();
			while(results.next()){
				numeroPersonal = results.getInt("numeroPersonal");
			}
		}catch (SQLException ex){
			Logger.getLogger(SolicitudModificacionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}finally{
			connexion.closeConnection();
		}
		return numeroPersonal;
	}

	public boolean guardarSolicitudModificacion (SolicitudModificacion solicitudModificacion){
		boolean result = false;
		try{
			connection = connexion.getConnection();
			String querySaveSolicitud = "INSERT INTO SolicitudModificacion (NRC, numeroPersonal, estado, motivo) VALUES (?, ?, ?, ?)";
			PreparedStatement sentence = connection.prepareStatement(querySaveSolicitud);
			sentence.setInt(1, solicitudModificacion.getNRC());
			sentence.setInt(2, solicitudModificacion.getNumeroPersonal());
			sentence.setString(3, solicitudModificacion.getEstado() );
			sentence.setString(4, solicitudModificacion.getMotivoCambio());
			results= sentence.executeQuery();
			result = true;
		}catch (SQLException ex){
			Logger.getLogger(SolicitudModificacionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}finally{
			connexion.closeConnection();
		}
		return result;
	}

	public boolean tienePlanCurso(int numeroPersonal){
		boolean tiene = false;
		try{
			connection = connexion.getConnection();
			String queryPlan = "SELECT nombre FROM Materia, Academico, PlanCurso WHERE Materia.numeroPersonal = Academico.numeroPersonal AND" +
					" Academico.numeroPersonal =? AND PlanCurso.NRC = Materia.NRC";
			PreparedStatement sentence = connection.prepareStatement(queryPlan);
			sentence.setInt(1, numeroPersonal);
			results= sentence.executeQuery();
			while(results.next()){
				tiene = true;
			}
		}catch (SQLException ex){
			Logger.getLogger(SolicitudModificacionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}finally{
			connexion.closeConnection();
		}
		return tiene;
	}
}