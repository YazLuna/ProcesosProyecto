package accesoDatos;

import dominio.Materia;
import dominio.Numero;

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
    public boolean registrarMateria(Materia materia){
        boolean registrar = false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceMateria = connection.prepareStatement("INSERT INTO Materia(NRC, nombre, decripcion, periodo, turno, area) VALUES(?,?,?,?,?,?)");
            sentenceMateria.setInt(1, materia.getNRC());
            sentenceMateria.setString(2, materia.getNombre());
            sentenceMateria.setString(3, materia.getDescripcion());
            sentenceMateria.setString(4, materia.getPeriodo());
            sentenceMateria.setString(5, materia.getTurno());
            sentenceMateria.setString(6, materia.getArea());
            sentenceMateria.executeUpdate();
            registrar = true;
        }catch (SQLException ex) {
            Logger.getLogger(MateriaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return registrar;
    }

    @Override
    public int validarNRC(String nrc){
        int esValidoNrc = Numero.CERO.getNumero();
        try{
            connection = connexion.getConnection();
            String queryCuenta = "SELECT NRC FROM Materia WHERE NRC=?";
            PreparedStatement sentence = connection.prepareStatement(queryCuenta);
            sentence.setString(1, nrc);
            results= sentence.executeQuery();
            if(results.next()){
                esValidoNrc = Numero.UNO.getNumero();
            }
        }catch (SQLException ex){
            Logger.getLogger(CuentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            esValidoNrc= Numero.DOS.getNumero();
        }finally{
            connexion.closeConnection();
        }
        return esValidoNrc;
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
