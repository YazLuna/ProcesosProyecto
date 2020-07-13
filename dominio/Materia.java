package dominio;

import accesoDatos.MateriaDAOImpl;
import javafx.scene.control.RadioButton;

import java.util.List;

public class Materia {
    private int NRC;
    private String nombre;
    private String descripcion;
    private RadioButton rbSeleccion;

    public Materia (){
        rbSeleccion = new RadioButton("¿Desea llevar la Materia?");
    }

    public int getNRC() {
        return NRC;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public RadioButton getRbSeleccion() {
        return rbSeleccion;
    }

    public void setNRC(int NRC) {
        this.NRC = NRC;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRbSeleccion(RadioButton rbSeleccion) {
        this.rbSeleccion = rbSeleccion;
    }

    public static List<Materia> getListMateria (){
        List<Materia> listaMateria;
        MateriaDAOImpl materiaDAO = new MateriaDAOImpl();
        listaMateria = materiaDAO.getListMateria();
        return listaMateria;
    }

    public static boolean registrarMateriaUsuario (int NRC, int idUsuario){
        boolean esRegistroMateriaUsuario;
        MateriaDAOImpl materiaDAO = new MateriaDAOImpl();
        esRegistroMateriaUsuario =materiaDAO.registrarUsuarioMateria(NRC,idUsuario);
        return esRegistroMateriaUsuario;
    }
}
