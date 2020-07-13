package gui.docente;

import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXMLRegistrarAvanceController extends FXMLGeneralController {
    @FXML private Button btnRegistrar;
    @FXML private Button btnCancelar;
    @FXML private Button btnCerrarSesion;
    @FXML private TextField tfAvanceUnidad1;
    @FXML private TextField tfAvanceUnidad2;
    @FXML private TextField tfAvanceUnidad3;
    @FXML private TextField tfAvanceUnidad4;
    @FXML private TextField tfAvanceUnidad5;
    @FXML private TextField tfAvanceUnidad6;
    @FXML private TextField tfAvanceUnidad7;

    public void cancelar() {
        boolean cancelar = generarConfirmacion("¿Seguro que desea cancelar?");
        if(cancelar) {
            abrirVentana("/gui/docente/FXMLMenuDocente.fxml", btnCancelar);
        }
    }

    public void registrar() {
        createObject();
    }

    private void createObject() {

    }

    public void cerrarSesion() {
        cerrarSesionGeneral();
    }
}
