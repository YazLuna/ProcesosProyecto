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
        boolean seCreoUsuario= false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceUsuario = connection.prepareStatement("INSERT INTO Usuario(telefono,nombre,apellido" +
                    ",tipo,RFC,fechaNacimiento,genero) VALUES (?,?,?,?,?,?,?)");
            sentenceUsuario.setString(1, usuario.getTelefono());
            sentenceUsuario.setString(2, usuario.getNombre());
            sentenceUsuario.setString(3, usuario.getApellidos());
            sentenceUsuario.setString(4, usuario.getTipo());
            sentenceUsuario.setString(5, usuario.getRFC());
            sentenceUsuario.setString(6, usuario.getFechaNacimiento());
            sentenceUsuario.setInt(7, usuario.getGenero());
            sentenceUsuario.executeUpdate();
            seCreoUsuario= true;
        }catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return seCreoUsuario;
    }

    @Override
    public int validarRepetirUsuario (String telefono, String RFC){
        int esValidoRepetidoUsuario = Numero.CERO.getNumero();
        try{
            connection = connexion.getConnection();
            String queryUsuario = "SELECT RFC FROM Usuario WHERE telefono=? OR RFC=?";
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
