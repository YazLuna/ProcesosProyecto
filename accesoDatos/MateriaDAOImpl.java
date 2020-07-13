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
    public boolean registrarUsuarioMateria(int NRC,int idUsuario){
        boolean seRegistroMateriaUsuario= false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceCuenta = connection.prepareStatement("INSERT INTO Cliente_Materia VALUES (?,?)");
            sentenceCuenta.setInt(1, NRC);
            sentenceCuenta.setInt(2, idUsuario);
            sentenceCuenta.executeUpdate();
            seRegistroMateriaUsuario= true;
        }catch (SQLException ex) {
            Logger.getLogger(MateriaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return seRegistroMateriaUsuario;
    }
}
