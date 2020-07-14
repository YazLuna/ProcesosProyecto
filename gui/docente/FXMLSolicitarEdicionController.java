package gui.docente;

import dominio.Materia;
import dominio.SolicitudModificacion;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLSolicitarEdicionController extends FXMLGeneralController implements Initializable {
    @FXML private TableView<Materia> tvMaterias;
    @FXML private TableColumn<Materia, String> tcMateria;
    @FXML private TableColumn<Materia, Integer> tcNRC;
    @FXML private Button btnSolicitar;
    @FXML private Button btnCancelar;
    @FXML private TextArea taMotivo;
    public static int numeroPersonal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colocarMaterias();
    }

    private void colocarMaterias() {
        List<Materia> listaMaterias = SolicitudModificacion.buscarMateria(numeroPersonal);
        tcMateria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcNRC.setCellValueFactory(new PropertyValueFactory<>("NRC"));
        tvMaterias.getItems().setAll(listaMaterias);
    }

    public void cancelar() {
        abrirVentana("/gui/docente/FXMLMenuDocente.fxml", btnCancelar);
    }

    public void solicitar() {
        Materia materia = tvMaterias.getSelectionModel().getSelectedItem();
        if (materia == null) {
            generarAlerta("Por favor seleccione una fila de la tabla");
        } else{
            SolicitudModificacion solicitudModificacion = new SolicitudModificacion();
            solicitudModificacion.setMotivoCambio(taMotivo.getText());
            solicitudModificacion.setNRC(materia.getNRC());
            solicitudModificacion.setNumeroPersonal(numeroPersonal);
            boolean resultado = SolicitudModificacion.guardarSolicitudModificacion(solicitudModificacion);
            if (resultado) {
                generarInformation("Permiso solicitado exitosamente");
            }else{
                generarError("No hay conexión con la base de datos, intente más tarde");
            }
            abrirVentana("/gui/docente/FXMLMenuDocente.fxml", btnSolicitar);
        }
    }

    public void cerrarSesion() {
        cerrarSesionGeneral();
    }

}
