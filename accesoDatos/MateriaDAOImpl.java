package accesoDatos;

import dominio.Materia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MateriaDAOImpl implements IMateriaDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    public MateriaDAOImpl (){
        connexion= new Connexion();
    }

    @Override
    public boolean registrarMateria(int NRC,String nombre,String descripcion,String area,String periodo,String turno){
        boolean registrar = false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceMateria = connection.prepareStatement("INSERT INTO Materia(NRC, nombre, descripcion, area, periodo, turno) VALUES(?,?,?,?,?,?,)");
            sentenceMateria.setInt(1, NRC);
            sentenceMateria.setString(2, nombre);
            sentenceMateria.setString(3, descripcion);
            sentenceMateria.setString(4, area);
            sentenceMateria.setString(5, periodo);
            sentenceMateria.setString(6, turno);
            sentenceMateria.executeQuery();
            registrar = true;
        }catch (SQLException ex) {
            Logger.getLogger(MateriaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return registrar;
    }

    public List<Materia> getListMateria () {
        List<Materia> listaMateria = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            String queryUsuario = "SELECT NRC,nombre FROM Materia";
            PreparedStatement sentencia = connection.prepareStatement(queryUsuario);
            results= sentencia.executeQuery();
            while(results.next()){
                Materia materia = new Materia();
                materia.setNRC(results.getInt("NRC"));
                materia.setNombre(results.getString("nombre"));
                listaMateria.add(materia);
            }
        }catch (SQLException ex){
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return listaMateria;
    }

    @Override
    public boolean registrarUsuarioMateria(int NRC, String RFC){
        boolean seRegistroMateriaUsuario= false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceMateria = connection.prepareStatement("INSERT INTO Cliente_Materia VALUES (?,?)");
            sentenceMateria.setString(1, RFC);
            sentenceMateria.setInt(2, NRC);
            sentenceMateria.executeUpdate();
            seRegistroMateriaUsuario= true;
        }catch (SQLException ex) {
            Logger.getLogger(MateriaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return seRegistroMateriaUsuario;
    }
}
