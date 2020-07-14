package gui.cliente;

import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMenuClienteController extends FXMLGeneralController implements Initializable {
    @FXML private Button btnCerrarSesion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cerrarSesion () {
        generarCancelacion("¿Seguro desea cerrar sesión?",btnCerrarSesion,"/gui/inicioSesion/FXMLIniciarSesion.fxml");
    }
}
