package gui.administrador;

import accesoDatos.DocenteDAO;
import dominio.Docente;
import gui.util.alerts.OperationAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import gui.util.Controller;
import javafx.scene.control.cell.PropertyValueFactory;
import logica.ValidacionGeneral;
import accesoDatos.CuentaDAOImpl;
import dominio.Genero;
import dominio.Materia;
import dominio.Numero;
import dominio.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.FXMLGeneralController;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logica.ValidacionGeneral;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static gui.FXMLGeneralController.*;


public class RegistrarDocenteController extends Controller implements Initializable {
    private ObservableList<Docente> docenteObservableList;
    private  Docente selectedDocente;
    @FXML private TableView<Docente> docenteTableView;
    @FXML private TableColumn<Docente, String> docenteNameColumn;
    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField lastnameTextField;
    @FXML private TextField rfcTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField altEmailTextField;
    @FXML private TextField perfilTextField;
    @FXML private TextField contraseniaTextField;
    @FXML private Button registerButton;
    @FXML private Button cancelButton;
    @FXML private Button clearComponents;
    @FXML private RadioButton masculinoRadioButton;
    @FXML private RadioButton femeninoRadioButton;
    @FXML private DatePicker fechaNacimientoDatePicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataToDocentetableView();
        setTableComponents();
        comenzarComponentes();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/gui/administrador/RegistrarDocenteVista.fxml"), this);
        stage.show();
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        stage.close();
    }

    @FXML
    void registerButtonPressed(ActionEvent event) {
        removerEstilos();
        if( notEmpty() ){
            if( validarDatos() ){
                
            }else{
                OperationAlert.showUnsuccessfullAlert("datos invalios", "La informacion ingresada no es valida");
            }
        }else{
            OperationAlert.showUnsuccessfullAlert("Campos de texto vacios", "No se ha ingresado informacion necesaria, favor de verificar");
        }
    }

    @FXML
    void crearComponents(ActionEvent event) {
        idTextField.setText("");
        nameTextField.setText("");
        lastnameTextField.setText("");
        rfcTextField.setText("");
        phoneNumberTextField.setText("");
        emailTextField.setText("");
        altEmailTextField.setText("");
        perfilTextField.setText("");
        registerButton.setDisable(false);
    }

    public void comenzarComponentes (){
        limitTextField(nameTextField,50);
        prohibitNumberTextField(nameTextField);
        limitTextField(lastnameTextField,50);
        prohibitNumberTextField(lastnameTextField);
        limitTextField(phoneNumberTextField,10);
        prohibitWordTextField(phoneNumberTextField);
        limitTextField(emailTextField,150);
        prohibitSpacesTextField(emailTextField);
        limitTextField(altEmailTextField,150);
        prohibitSpacesTextField(altEmailTextField);
        limitTextField(contraseniaTextField,28);
        prohibitSpacesTextField(contraseniaTextField);
        limitTextField(rfcTextField,15);
        prohibitSpacesTextField(rfcTextField);
        fechaNacimientoDatePicker.getEditor().getStyleClass().add("details");
    }

    private  boolean notEmpty(){
        if( idTextField.getText().length() == 0 || nameTextField.getText().length() == 0 || lastnameTextField.getText().length() == 0 || rfcTextField.getText().length() == 0 || phoneNumberTextField.getText().length() == 0 || emailTextField.getText().length() == 0 || altEmailTextField.getText().length() == 0 || perfilTextField.getText().length() == 0 ){
            return false;
        }
        return true;
    }

    public boolean validarDatos(){
        boolean esValidoDatos=true;
        ValidacionGeneral validacionGeneral = new ValidacionGeneral();
        boolean esValidoNombre = validacionGeneral.validarNombre(nameTextField.getText());
        if(esValidoNombre){
            nameTextField.getStyleClass().add("ok");
        }else{
            nameTextField.getStyleClass().add("error");
            esValidoDatos=false;
        }
        boolean esValidoApellidos = validacionGeneral.validarApellidos(lastnameTextField.getText());
        if(esValidoApellidos){
            lastnameTextField.getStyleClass().add("ok");
        }else {
            lastnameTextField.getStyleClass().add("error");
            esValidoDatos=false;
        }
        boolean esValidoRFC = validacionGeneral.validarRFC(rfcTextField.getText());
        if(esValidoRFC){
            rfcTextField.getStyleClass().add("ok");
        }else {
            rfcTextField.getStyleClass().add("error");
            esValidoDatos=false;
        }
        boolean esValidoCorreo = validacionGeneral.validarCorreo(emailTextField.getText());
        if(esValidoCorreo) {
            emailTextField.getStyleClass().add("ok");
        }else {
            emailTextField.getStyleClass().add("error");
            esValidoDatos=false;
        }

        boolean esValidoCorreoAlterno = validacionGeneral.validarCorreo(altEmailTextField.getText());
        if(esValidoCorreoAlterno) {
            altEmailTextField.getStyleClass().add("ok");
        }else {
            altEmailTextField.getStyleClass().add("error");
            esValidoDatos=false;
        }

        boolean esValidoContraseña = validacionGeneral.validarContrasenia(contraseniaTextField.getText());
        if(esValidoContraseña) {
            contraseniaTextField.getStyleClass().add("ok");
        }else {
            contraseniaTextField.getStyleClass().add("error");
            esValidoDatos=false;
        }

        boolean esValidoFechaNacimiento = validacionGeneral.validarPalabra(fechaNacimientoDatePicker.getEditor().getText());
        if(esValidoFechaNacimiento){
            boolean esValidoAñoNacimiento = validacionGeneral.validarAño(fechaNacimientoDatePicker.getEditor().getText());
            if(esValidoAñoNacimiento){
                fechaNacimientoDatePicker.getEditor().getStyleClass().add("ok");
            }else{
                fechaNacimientoDatePicker.getEditor().getStyleClass().add("error");
                esValidoDatos=false;
            }
        }else {
            fechaNacimientoDatePicker.getEditor().getStyleClass().add("error");
            esValidoDatos=false;
        }

        boolean esValidoTelefono = validacionGeneral.validarTelefono(phoneNumberTextField.getText());
        if (esValidoTelefono){
            phoneNumberTextField.getStyleClass().add("ok");
        } else{
            phoneNumberTextField.getStyleClass().add("error");
            esValidoDatos=false;
        }

        boolean seleccionMujer = femeninoRadioButton.isSelected();
        boolean seleccionHombre = masculinoRadioButton.isSelected();
        if(!seleccionHombre && !seleccionMujer){
            femeninoRadioButton.getStyleClass().add("error");
            masculinoRadioButton.getStyleClass().add("error");
            esValidoDatos=false;
        }

        return  esValidoDatos;
    }

    private void setDataToDocentetableView(){
        DocenteDAO docenteDAO = new DocenteDAO();
        docenteObservableList = FXCollections.observableArrayList();
        docenteObservableList.addAll( docenteDAO.getAllDocentes() );
        docenteTableView.setItems(docenteObservableList);
    }

    private void setTableComponents() {
        docenteNameColumn.setCellValueFactory( new PropertyValueFactory<>("nombre") );
        setListenerToDocenteTableView();

    }

    private void setListenerToDocenteTableView() {
        docenteTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedDocente = newValue;
            setDataToComponents( (Docente) selectedDocente );
        });
    }

    private void setDataToComponents(Docente selectedDocente) {
        registerButton.setDisable(true);
        idTextField.setText( selectedDocente.getNumeroPersonal() );
        nameTextField.setText( selectedDocente.getNombre() );
        lastnameTextField.setText( selectedDocente.getApellidos() );
        rfcTextField.setText( selectedDocente.getRFC() );
        phoneNumberTextField.setText( selectedDocente.getTelefono() );
        emailTextField.setText( selectedDocente.getCorreo() );
        altEmailTextField.setText( selectedDocente.getCorreoAlterno() );
        fechaNacimientoDatePicker.setValue( LocalDate.parse( selectedDocente.getFechaNacimiento() ) );
        contraseniaTextField.setText( selectedDocente.getContrasenia() );
        perfilTextField.setText( selectedDocente.getPerfil() );

    }

    public  void removerEstilos() {
        nameTextField.getStyleClass().remove("ok");
        rfcTextField.getStyleClass().remove("ok");
        lastnameTextField.getStyleClass().remove("ok");
        phoneNumberTextField.getStyleClass().remove("ok");
        emailTextField.getStyleClass().remove("ok");
        altEmailTextField.getStyleClass().remove("ok");
        contraseniaTextField.getStyleClass().remove("ok");
        fechaNacimientoDatePicker.getEditor().getStyleClass().remove("ok");
        nameTextField.getStyleClass().remove("error");
        rfcTextField.getStyleClass().remove("error");
        lastnameTextField.getStyleClass().remove("error");
        phoneNumberTextField.getStyleClass().remove("error");
        emailTextField.getStyleClass().remove("error");
        altEmailTextField.getStyleClass().remove("error");
        contraseniaTextField.getStyleClass().remove("error");
        fechaNacimientoDatePicker.getEditor().getStyleClass().remove("error");
        femeninoRadioButton.getStyleClass().remove("error");
        masculinoRadioButton.getStyleClass().remove("error");
    }

}