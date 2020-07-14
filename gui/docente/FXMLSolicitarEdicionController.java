package gui.docente;

import dominio.Materia;
import dominio.SolicitudModificacion;
import gui.FXMLGeneralController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML private TextField tfMotivo;
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
        tfMotivo.getStyleClass().remove("error");
        Materia materia = tvMaterias.getSelectionModel().getSelectedItem();
        if (materia == null) {
            generarAlerta("Por favor seleccione una fila de la tabla");
        } else{
            if(validateEmpty(tfMotivo.getText())) {
                boolean confirrmar  = generarConfirmacion("¿Seguro que desea solicitar el permiso?");
                if (confirrmar ) {
                    SolicitudModificacion solicitudModificacion = new SolicitudModificacion();
                    solicitudModificacion.setMotivoCambio(tfMotivo.getText());
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
            } else{
                tfMotivo.getStyleClass().add("error");
            }
        }
    }

    public void cerrarSesion() {
        cerrarSesionGeneral();
    }

}
