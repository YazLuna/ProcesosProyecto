package accesoDatos;

import dominio.Docente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocenteDAO {
    private final Connexion connexion;
    private ResultSet results;

    public DocenteDAO (){
        connexion = new Connexion();
    }

    public List<Docente> getAllDocentes() {
        List<Docente> listaDocentes = new ArrayList<>();
        try( Connection conn = connexion.getConnection() ){
            String queryDocente = "SELECT ACA.numeroPersonal, ACA.perfil, ACA.RFC, US.nombre, US.apellido, US.telefono, US.fechaNacimiento, US.genero, CU.correo, CU.correoAlterno, CU.contrasenia FROM Academico AS ACA INNER JOIN Usuario AS US INNER JOIN Cuenta AS CU ON ACA.RFC = CU.RFC WHERE ACA.Tipo = ? AND US.tipo = ?";
            PreparedStatement sentencia = conn.prepareStatement(queryDocente);
            sentencia.setString(1, "Docente");
            sentencia.setString(2, "Docente");
            results = sentencia.executeQuery();
            while(results.next()){
                Docente docente = new Docente();
                docente.setNumeroPersonal( results.getString("ACA.numeroPersonal") );
                docente.setNombre( results.getString("US.nombre") );
                docente.setApellidos( results.getString("US.apellido") );
                docente.setRFC( results.getString("ACA.rfc") );
                docente.setTelefono( results.getString("US.telefono") );
                docente.setFechaNacimiento( results.getString("US.fechaNacimiento") );
                docente.setCorreo( results.getString("CU.correo") );
                docente.setCorreoAlterno( results.getString("CU.correoAlterno") );
                docente.setContrasenia( results.getString("CU.contrasenia") );
                docente.setGenero( results.getInt("US.genero") );
                listaDocentes.add(docente);
            }
        }catch (SQLException ex){
            Logger.getLogger(DocenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        System.out.println(listaDocentes.size());
        return listaDocentes;
    }

    public int agregarDocente(Docente docente) throws SQLException {
        int idDocente = 0;
        try( Connection conn = connexion.getConnection() ){
            String statement = "CALL agregarDocente(?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?, ?)";
            PreparedStatement agregarDocente = conn.prepareStatement(statement);
            agregarDocente.setString(1, docente.getNombre() );
            agregarDocente.setString(2, docente.getApellidos() );
            agregarDocente.setString(3, docente.getRFC() );
            agregarDocente.setString(4, docente.getTelefono() );
            agregarDocente.setString(5, docente.getCorreo() );
            agregarDocente.setString( 6, docente.getCorreoAlterno() );
            agregarDocente.setString( 7, docente.getCorreoAlterno() );
            agregarDocente.setString( 8, docente.getFechaNacimiento() );
            agregarDocente.setString( 9, docente.getNumeroPersonal() );
            agregarDocente.setString( 10, docente.getContrasenia() );
            agregarDocente.setInt( 10, docente.getGenero() );
            agregarDocente.setString( 11, docente.getTipo() );
            agregarDocente.executeUpdate();
            statement = "SELECT LAST_INSERT_ID()";
            agregarDocente = conn.prepareStatement(statement);
            ResultSet result = agregarDocente.executeQuery();
            result.next();
            idDocente = result.getInt(1);
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return idDocente;
    }
}
