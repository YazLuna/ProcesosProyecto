package accesoDatos;

public interface ICuentaDAO {
    public int buscarCuentaUsuario (String correo, String contrasenia);
    public int validarCorreoUsuario (String correo);
    public String tipoUsuario (String correo, String contrasenia);
    public boolean crearCuenta (String correo, String correoAlterno,String contrasenia, int idUsuario);
}
