package gui.docente;

import gui.FXMLGeneralController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FXMLMenuDocenteController extends FXMLGeneralController {
	@FXML private Button btnRegistrarAvance;

	public void cerrarSesion() {
		cerrarSesionGeneral();
	}

	public void registrarAvance() {
		abrirVentana("/gui/docente/FXMLRegistrarAvance.fxml", btnRegistrarAvance);
	}
}
