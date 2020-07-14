package gui.docente;

import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FXMLMenuDocenteController extends FXMLGeneralController {
	@FXML private Button btnSolicitarEdicion;

	public void cerrarSesion() {
		cerrarSesionGeneral();
	}

	public void solicitarEdicion() {
		abrirVentana("/gui/docente/FXMLSolicitarEdicion.fxml", btnSolicitarEdicion);
	}
}
