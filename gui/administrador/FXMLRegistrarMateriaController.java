package gui.administrador;

import accesoDatos.MateriaDAOImpl;
import dominio.Materia;
import dominio.Numero;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLRegistrarMateriaController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnCerrarSesion;
    @FXML private Button btnRegistrar;
    @FXML private Button btnCancelar;
    @FXML private TextField tftNRC;
    @FXML private TextField tftNombre;
    @FXML private TextArea tfaDescripcion;
    @FXML private TextField tftPeriodo;
    @FXML private TextField tftTurno;
    @FXML private TextField tftArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { comenzarComponentes(); }

    public void cerrarSesion() { cerrarSesion(); }

    public void cancelar() {
        generarCancelacion("¿Seguro que desea cancelar?", btnCancelar, "/gui/administrador/FXMLMenuAdministrador.fxml");
    }

    public void comenzarComponentes() {
        limitTextField(tftNRC, 5);
        prohibitWordTextField(tftNRC);
        prohibitSpacesTextField(tftNRC);
        limitTextField(tftNombre, 50);
        prohibitNumberTextField(tftNombre);
        limitTextField(tftPeriodo, 50);
        limitTextField(tftTurno, 20);
        prohibitNumberTextField(tftTurno);
        prohibitSpacesTextField(tftTurno);
        limitTextField(tftArea, 50);
        prohibitNumberTextField(tftArea);
    }

    public void registrar() {
        ingresarDatos();
    }

    public void ingresarDatos() {
        Materia materia = new Materia();
        int nrc = Integer.parseInt(tftNRC.getText());
        materia.setNRC(nrc);
        materia.setNombre(tftNombre.getText());
        materia.setDescripcion(tfaDescripcion.getText());
        materia.setPeriodo(tftPeriodo.getText());
        materia.setTurno(tftTurno.getText());
        materia.setArea(tftArea.getText());
        validarRepetidoRNC(materia);
    }

    public void registrarMateria(Materia materia) {
        boolean registroMateria = materia.registrarMateriaAdministrador(materia);
        if(registroMateria){
            generarInformation("La materia se registro correctamente.");
            abrirVentana("/gui/administrador/FXMLMenuAdministrador", btnRegistrar);
        }else{
            generarError("No se pudo registrar la materia.");
        }
    }

    public void validarRepetidoRNC(Materia materia) {
        MateriaDAOImpl materiaDAO = new MateriaDAOImpl();
        int esRepetidoNrc = materiaDAO.validarNRC(tftNRC.getText());
        if(esRepetidoNrc == Numero.UNO.getNumero()){
            generarError("Este NRC ya esta registrado.");
        }else{
            registrarMateria(materia);
        }
    }

}
