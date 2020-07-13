package accesoDatos;

import dominio.Numero;
import dominio.Unidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AvanceDAOImpl {
	private final Connexion connexion;
	private Connection connection;
	private Statement consultation;
	private ResultSet results;

	public AvanceDAOImpl (){
		connexion= new Connexion();
	}

	public List<String> buscarMateria (int numeroPersonal){
		List<String> materias = new ArrayList<>();
		try{
			connection = connexion.getConnection();
			String queryMateria = "SELECT nombre FROM Materia, Docente, PlanCurso WHERE Materia.numeroPersonal = Docente.numeroPersonal AND" +
					" Docente.numeroPersonal =? AND PlanCurso.NRC = Materia.NRC;";
			PreparedStatement sentence = connection.prepareStatement(queryMateria);
			sentence.setInt(1, numeroPersonal);
			results= sentence.executeQuery();
			while(results.next()){
				materias.add(results.getString("nombre"));
			}
		}catch (SQLException ex){
			Logger.getLogger(AvanceDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}finally{
			connexion.closeConnection();
		}
		return materias;
	}

	public List<Unidad> buscarUnidades (String nombreMateria){
		List<Unidad> unidades = new ArrayList<>();
		try{
			connection = connexion.getConnection();
			String queryMateria = "SELECT nombreUnidad, temas, fechas, tareasPracticas, porcentajeAvanceTotal FROM" +
					" Materia, Unidad, PlanCurso WHERE Materia.NRC =PlanCurso.NRC AND Unidad.idPlanCurso =" +
					" PlanCurso.idPlanCurso AND Materia.nombre =?";
			PreparedStatement sentence = connection.prepareStatement(queryMateria);
			sentence.setString(1, nombreMateria);
			results= sentence.executeQuery();
			while(results.next()){
				Unidad unidad = new Unidad();
				unidad.setNombreUnidad(results.getString("nombreUnidad"));
				unidad.setTemas(results.getString("temas"));
				unidad.setFechas(results.getString("fechas"));
				unidad.setTareasPracticas(results.getString("tareasPracticas"));
				unidad.setPorcentajeAvance(results.getInt("porcentajeAvancetotal"));
				unidades.add(unidad);
			}
		}catch (SQLException ex){
			Logger.getLogger(AvanceDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}finally{
			connexion.closeConnection();
		}
		return unidades;
	}

}
