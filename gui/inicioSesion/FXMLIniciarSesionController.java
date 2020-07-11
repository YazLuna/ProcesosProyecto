package gui.inicioSesion;

import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class FXMLIniciarSesionController extends FXMLGeneralController {
	@FXML private TextField tfNombreUsuario;
	@FXML private PasswordField pfContraseña;
	@FXML private Button btnIniciarSesion;

	public void iniciarSesion() {

		String usuario = tfNombreUsuario.getText();
		switch (usuario) {
			case "Docente":
					abrirVentana("/gui/docente/FXMLMenuDocente.fxml", btnIniciarSesion);
				break;
		}
	}

}
