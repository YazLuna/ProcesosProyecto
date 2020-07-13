package dominio;

import accesoDatos.MateriaDAOImpl;
import javafx.scene.control.RadioButton;

import java.util.List;

public class Materia {
    private int NRC;
    private String nombre;
    private String descripcion;
    private String area;
    private String periodo;
    private String turno;
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

    public void setTurno(String turno) { this.turno = turno; }

    public String getTurno() { return turno; }

    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public String getPeriodo() { return periodo; }

    public void setArea(String area) { this.area = area; }

    public String getArea() { return area; }
    
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

    public static boolean registrarMateriaAdministrador(int NRC,String nombre,String descripcion,String area,String periodo,String turno){
        boolean registrar;
        MateriaDAOImpl materiaDAO = new MateriaDAOImpl();
        registrar = materiaDAO.registrarMateria(NRC, nombre, descripcion, area, periodo, turno);
        return registrar;
    }
}
