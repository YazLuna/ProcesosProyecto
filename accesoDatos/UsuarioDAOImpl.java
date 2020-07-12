package accesoDatos;

import dominio.Numero;
import dominio.Usuario;

import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAOImpl implements IUsuarioDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    public UsuarioDAOImpl (){
        connexion= new Connexion();
    }

    @Override
    public boolean agregarUsuario(Usuario usuario) {
        boolean seCreoCuenta= false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceCuenta = connection.prepareStatement("INSERT INTO Cuenta(telefono,nombre,apellido" +
                    ",correoAlternativo,tipo,RFC,fechaNacimiento,genero) VALUES (?,?,?,?,?,?,?,?)");
            sentenceCuenta.setString(1, usuario.getTelefono());
            sentenceCuenta.setString(2, usuario.getNombre());
            sentenceCuenta.setString(3, usuario.getApellidos());
            sentenceCuenta.setString(4, usuario.getCorreoAlterno());
            sentenceCuenta.setString(5, usuario.getTipo());
            sentenceCuenta.setString(6, usuario.getRFC());
            sentenceCuenta.setString(7, usuario.getFechaNacimiento());
            sentenceCuenta.setInt(8, usuario.getGenero());
            sentenceCuenta.executeUpdate();
            seCreoCuenta= true;
        }catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return seCreoCuenta;
    }

    @Override
    public int validarRepetirUsuario (String telefono, String RFC){
        int esValidoRepetidoUsuario = Numero.CERO.getNumero();
        try{
            connection = connexion.getConnection();
            String queryUsuario = "SELECT idUsuario FROM Usuario WHERE telefono=? OR RFC=?";
            PreparedStatement sentence = connection.prepareStatement(queryUsuario);
            sentence.setString(1, telefono);
            sentence.setString(2, RFC);
            results= sentence.executeQuery();
            if(results.next()){
                esValidoRepetidoUsuario = Numero.UNO.getNumero();
            }
        }catch (SQLException ex){
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            esValidoRepetidoUsuario= Numero.DOS.getNumero();
        }finally{
            connexion.closeConnection();
        }
        return esValidoRepetidoUsuario;
    }
}
