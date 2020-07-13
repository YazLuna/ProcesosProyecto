package dominio;

import accesoDatos.UsuarioDAOImpl;

import java.util.Date;

public class Usuario {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private String correoAlterno;
    private String contrasenia;
    private String RFC;
    private String tipo;
    private String fechaNacimiento;
    private int genero;

    public Usuario () {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoAlterno() {
        return correoAlterno;
    }

    public void setCorreoAlterno(String correoAlterno) {
        this.correoAlterno = correoAlterno;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipo () {
        return tipo;
    }

    public void setTipo (String tipo) {
        this.tipo = tipo;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public static int validarRepetidoUsuario (String telefono, String RFC){
        UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
        int esRepetidoUsuario;
        esRepetidoUsuario = usuarioDAO.validarRepetirUsuario(telefono,RFC);
        return esRepetidoUsuario;
    }

    public static boolean registrarUsuario (Usuario usuario) {
        UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
        boolean esRegistroUsuario;
        esRegistroUsuario = usuarioDAO.agregarUsuario(usuario);
        return esRegistroUsuario;
    }

    public static int getUsuario (String RFC) {
        UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
        int idUsuario;
        idUsuario = usuarioDAO.buscarUsuario(RFC);
        return idUsuario;
    }
}
