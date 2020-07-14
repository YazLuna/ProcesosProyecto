package gui.administrador;

import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMenuAdministradorController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnCerrarSesion;
    @FXML private Button btnRegistrarMateria;
    @FXML private Button btnRegistrarDocente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void cerrarSesion() { cerrarSesion(); }

    public void registrarMateria() {
        abrirVentana("/gui/administrador/FXMLRegistrarMateria.fxml", btnRegistrarMateria);
    }

    public void registrarDocente() {

    }
}
