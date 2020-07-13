package accesoDatos;

import dominio.Numero;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaDAOImpl implements ICuentaDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    public CuentaDAOImpl (){
        connexion= new Connexion();
    }

    @Override
    public int buscarCuentaUsuario (String correo, String contrasenia){
        int esCuenta = Numero.CERO.getNumero();
        try{
            connection = connexion.getConnection();
            String queryCuenta = "SELECT correo FROM Cuenta WHERE correo=? AND contrasenia=?";
            PreparedStatement sentence = connection.prepareStatement(queryCuenta);
            sentence.setString(1, correo);
            sentence.setString(2, contrasenia);
            results= sentence.executeQuery();
            while(results.next()){
                esCuenta = Numero.UNO.getNumero();
            }
        }catch (SQLException ex){
            Logger.getLogger(CuentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            esCuenta= Numero.DOS.getNumero();
        }finally{
            connexion.closeConnection();
        }
        return esCuenta;
    }

    @Override
    public String tipoUsuario (String correo, String contrasenia){
        String tipo = "No se encontro el tipo de usuario";
        try{
            connection = connexion.getConnection();
            String queryTipo = "SELECT tipo FROM Cuenta INNER JOIN Usuario on Cuenta.idUsuario " +
                    "= Usuario.idUsuario WHERE correo=?";
            PreparedStatement sentence = connection.prepareStatement(queryTipo);
            sentence.setString(1, correo);
            results= sentence.executeQuery();
            while(results.next()){
                tipo=results.getString("tipo");
            }
        }catch (SQLException ex){
            Logger.getLogger(CuentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return tipo;
    }

    @Override
    public boolean crearCuenta (String correo, String correoAlterno,String contrasenia, int idUsuario){
        boolean seCreoCuenta= false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceCuenta = connection.prepareStatement("INSERT INTO Cuenta(correo,contrasenia,correoAlterno,idUsuario) VALUES (?,?,?,?)");
            sentenceCuenta.setString(1, correo);
            sentenceCuenta.setString(2, contrasenia);
            sentenceCuenta.setString(3, correoAlterno);
            sentenceCuenta.setInt(4, idUsuario);
            sentenceCuenta.executeUpdate();
            seCreoCuenta= true;
        }catch (SQLException ex) {
            Logger.getLogger(CuentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return seCreoCuenta;
    }

    @Override
    public int validarCorreoUsuario (String correo){
        int esValidoCuenta = Numero.CERO.getNumero();
        try{
            connection = connexion.getConnection();
            String queryCuenta = "SELECT correo FROM Cuenta WHERE correo=? OR correoAlterno=?";
            PreparedStatement sentence = connection.prepareStatement(queryCuenta);
            sentence.setString(1, correo);
            sentence.setString(2, correo);
            results= sentence.executeQuery();
            if(results.next()){
                esValidoCuenta = Numero.UNO.getNumero();
            }
        }catch (SQLException ex){
            Logger.getLogger(CuentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            esValidoCuenta= Numero.DOS.getNumero();
        }finally{
            connexion.closeConnection();
        }
        return esValidoCuenta;
    }
}
