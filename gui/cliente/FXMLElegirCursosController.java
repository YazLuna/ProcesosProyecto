package gui.cliente;

import dominio.Materia;
import dominio.Numero;
import gui.FXMLGeneralController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLElegirCursosController extends FXMLGeneralController implements Initializable {
    @FXML TableView tvCursos;
    @FXML Button btnGuardar;
    @FXML Button btnCancelar;
    private List<Materia> allMateria;
    private static List<Integer> seleccionadosNRC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTableCursos();
    }

    public void cancelar (){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void startTableCursos() {
        allMateria = new ArrayList<>();
        allMateria = Materia.getListMateria();
        TableColumn<Materia, Integer> NRC = new TableColumn<>("NRC");
        NRC.setCellValueFactory(new PropertyValueFactory<Materia, Integer>("NRC"));
        TableColumn<Materia, String> nombre = new TableColumn<>("Nombre");
        nombre.setCellValueFactory(new PropertyValueFactory<Materia, String>("nombre"));
        TableColumn<Materia, RadioButton> elegirCursos = new TableColumn<>("Seleccion");
        elegirCursos.setCellValueFactory(new PropertyValueFactory<Materia, RadioButton>("rbSeleccion"));
        tvCursos.getColumns().addAll(NRC,nombre,elegirCursos);
        ObservableList<Materia> cursos = FXCollections.observableArrayList(allMateria);
        tvCursos.setItems(cursos);
        tvCursos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void guardar () {
        seleccionadosNRC = new ArrayList<>();
        List<Materia> listaMateria = new ArrayList<>();
        listaMateria = tvCursos.getItems();
        for(int index = Numero.CERO.getNumero(); index<listaMateria.size(); index++){
            RadioButton radioButton = new RadioButton();
            radioButton = listaMateria.get(index).getRbSeleccion();
            if(radioButton.isSelected()){
                seleccionadosNRC.add(listaMateria.get(index).getNRC());
            }
        }
    }

    public  List<Integer> getListNRC (){
        return seleccionadosNRC;
    }
}
