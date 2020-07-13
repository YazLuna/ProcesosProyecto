package accesoDatos;
import dominio.Usuario;

public interface IUsuarioDAO {
    public boolean agregarUsuario (Usuario usuario);
    public int validarRepetirUsuario (String telefono, String RFC);
    public int buscarUsuario (String RFC);
}
