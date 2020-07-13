package gui.docente;

import accesoDatos.AvanceDAOImpl;
import dominio.Unidad;
import gui.FXMLGeneralController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLRegistrarAvanceController extends FXMLGeneralController implements Initializable {
    @FXML private TableView<Unidad> tvUnidad;
    @FXML private TableColumn<Unidad, String> tcUnidad;
    @FXML private TableColumn<Unidad, String> tcFechas;
    @FXML private ComboBox cbMaterias;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colocarMaterias();
        AvanceDAOImpl daoAvance = new AvanceDAOImpl();
        /*String nombreMateria = "Diseño de Software";
        System.out.println("materia  "+nombreMateria);
        List<Unidad> unidad = daoAvance.buscarUnidades(nombreMateria);
        tcUnidad.setCellValueFactory(new PropertyValueFactory<>("nombreUnidad"));
        tcFechas.setCellValueFactory(new PropertyValueFactory<>("fechas"));
        tvUnidad.getItems().setAll(unidad);*/
    }

    private void colocarMaterias() {
        AvanceDAOImpl daoAvance = new AvanceDAOImpl();
        List<String> materias = daoAvance.buscarMateria(1);
        cbMaterias.getItems().addAll(materias);
    }

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

    public void seleccionarMateria() {

    }

    public void buscarPlanCurso() {
        if(!cbMaterias.getItems().isEmpty()) {
            AvanceDAOImpl daoAvance = new AvanceDAOImpl();
            String nombreMateria = (String) cbMaterias.getValue();
            System.out.println("materia  "+nombreMateria);
            List<Unidad> unidad = daoAvance.buscarUnidades(nombreMateria);
            tcUnidad.setCellValueFactory(new PropertyValueFactory<>("nombreUnidad"));
            tcFechas.setCellValueFactory(new PropertyValueFactory<>("fechas"));
            tvUnidad.refresh();
            tvUnidad.getItems().setAll(unidad);
            tfAvanceUnidad1.setText("ayuda :(");
        } else {
            generarAlerta("Selecciona una materia");
        }
    }
}
