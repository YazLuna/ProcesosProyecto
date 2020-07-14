package gui.docente;

import dominio.SolicitudModificacion;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FXMLMenuDocenteController extends FXMLGeneralController {
	@FXML private Button btnSolicitarEdicion;
	public static int numeroPersonal;

	public void cerrarSesion() {
		cerrarSesionGeneral();
	}

	public void solicitarEdicion() {
		boolean tienePlanCurso = SolicitudModificacion.tienePlanCurso(numeroPersonal);
		if (tienePlanCurso) {
			abrirVentana("/gui/docente/FXMLSolicitarEdicion.fxml", btnSolicitarEdicion);
		} else{
			generarAlerta("No tienes planes de curso");
		}
	}
}
