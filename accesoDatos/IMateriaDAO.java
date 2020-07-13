package accesoDatos;

import  java.util.List;
import dominio.Materia;

public interface IMateriaDAO {
    public List<Materia> getListMateria ();
    public boolean registrarUsuarioMateria(int NRC,int idUsuario);
}
