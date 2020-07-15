package gui.administrador;

import accesoDatos.CuentaDAOImpl;
import accesoDatos.DocenteDAO;
import dominio.Docente;
import dominio.Genero;
import dominio.Numero;
import dominio.Usuario;
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
import org.apache.commons.codec.binary.Hex;
import org.omg.PortableInterceptor.AdapterManagerIdHelper;

import javax.print.Doc;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        fechaNacimientoDatePicker.setEditable(false);
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/gui/administrador/RegistrarDocenteVista.fxml"), this);
        stage.show();
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        stage.close();
        MenuAdministradorController menuAdministradorController = new MenuAdministradorController();
        menuAdministradorController.showStage();
    }

    @FXML
    void cerrarSesion(ActionEvent event) {
        stage.close();
    }

    @FXML
    void registerButtonPressed(ActionEvent event) {
        removerEstilos();
        if( notEmpty() ){
            if( validarDatos() ){
                validarCorreos();
            }else{
                OperationAlert.showUnsuccessfullAlert("datos invalios", "La informacion ingresada no es valida");
            }
        }else{
            OperationAlert.showUnsuccessfullAlert("Campos de texto vacios", "No se ha ingresado informacion necesaria, favor de verificar");
        }
    }

    @FXML
    void clearComponents(ActionEvent event) {
        idTextField.setText("");
        nameTextField.setText("");
        lastnameTextField.setText("");
        rfcTextField.setText("");
        phoneNumberTextField.setText("");
        emailTextField.setText("");
        altEmailTextField.setText("");
        perfilTextField.setText("");
        contraseniaTextField.setText("");
        habilitarComponentes();
    }

    public void comenzarComponentes (){
        limitTextField(nameTextField,50);
        prohibitNumberTextField(nameTextField);
        limitTextField(lastnameTextField,50);
        prohibitNumberTextField(lastnameTextField);
        limitTextField(phoneNumberTextField,10);
        prohibitWordTextField(phoneNumberTextField);
        limitTextField(idTextField,10);
        prohibitWordTextField(idTextField);
        limitTextField(emailTextField,150);
        prohibitSpacesTextField(emailTextField);
        limitTextField(altEmailTextField,150);
        prohibitSpacesTextField(altEmailTextField);
        limitTextField(contraseniaTextField,28);
        prohibitSpacesTextField(contraseniaTextField);
        limitTextField(rfcTextField,15);
        prohibitSpacesTextField(rfcTextField);
        limitTextField(perfilTextField,30);
        prohibitNumberTextField(perfilTextField);
        fechaNacimientoDatePicker.getEditor().getStyleClass().add("details");
    }

    public void ingresarDatos (){
        ValidacionGeneral validacionGeneral = new ValidacionGeneral();
        Docente docente = new Docente();
        docente.setNumeroPersonal(idTextField.getText());
        docente.setNombre(validacionGeneral.eliminarEspacios(nameTextField.getText()));
        docente.setApellidos(validacionGeneral.eliminarEspacios(lastnameTextField.getText()));
        docente.setTelefono(phoneNumberTextField.getText());
        docente.setTipo("Docente");
        docente.setRFC(rfcTextField.getText());
        docente.setCorreo(emailTextField.getText());
        docente.setCorreoAlterno(altEmailTextField.getText());
        String contraseniaEncriptada = encryptPassword(contraseniaTextField.getText());
        docente.setContrasenia(contraseniaEncriptada);
        String fecha = fechaNacimientoDatePicker.getEditor().getText();
        String[] fechaNacimiento = fecha.split("/");
        String formatoFecha = fechaNacimiento[2]+"-"+fechaNacimiento[1]+"-"+fechaNacimiento[0];
        docente.setFechaNacimiento(formatoFecha);
        if(femeninoRadioButton.isSelected()){
            docente.setGenero(Genero.MUJER.getGenero());
        }else {
            docente.setGenero(Genero.HOMBRE.getGenero());
        }
        docente.setPerfil(perfilTextField.getText());
        registarDocente(docente);
    }

    public void registarDocente(Docente docente){
        DocenteDAO docenteDAO = new DocenteDAO();
        try {
            docenteDAO.agregarDocente(docente);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

        boolean esValidoPerfil = validacionGeneral.validarNombre(perfilTextField.getText());
        if(esValidoNombre){
            perfilTextField.getStyleClass().add("ok");
        }else{
            perfilTextField.getStyleClass().add("error");
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

        boolean esValidoNumeroPersonal = validacionGeneral.validarTelefono(idTextField.getText());
        if (esValidoTelefono){
            idTextField.getStyleClass().add("ok");
        } else{
            idTextField.getStyleClass().add("error");
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
        desabilitarComponentes();
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
        if( selectedDocente.getGenero() != 1 ){
            femeninoRadioButton.setSelected(true);
        }else{
            masculinoRadioButton.setSelected(true);
        }

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

    private void desabilitarComponentes(){
        idTextField.setDisable(true);
        nameTextField.setDisable(true);
        lastnameTextField.setDisable(true);
        rfcTextField.setDisable(true);
        phoneNumberTextField.setDisable(true);
        emailTextField.setDisable(true);
        altEmailTextField.setDisable(true);
        fechaNacimientoDatePicker.setDisable(true);
        contraseniaTextField.setDisable(true);
        perfilTextField.setDisable(true);
        registerButton.setDisable(true);
    }

    private void habilitarComponentes(){
        idTextField.setDisable(false);
        nameTextField.setDisable(false);
        lastnameTextField.setDisable(false);
        rfcTextField.setDisable(false);
        phoneNumberTextField.setDisable(false);
        emailTextField.setDisable(false);
        altEmailTextField.setDisable(false);
        fechaNacimientoDatePicker.setDisable(false);
        contraseniaTextField.setDisable(false);
        perfilTextField.setDisable(false);
        registerButton.setDisable(false);
    }

    public void validarCorreos(){
        if(emailTextField.getText().equals(altEmailTextField.getText())){
            altEmailTextField.getStyleClass().remove("ok");
            emailTextField.getStyleClass().remove("ok");
            altEmailTextField.getStyleClass().add("error");
            emailTextField.getStyleClass().add("error");
            OperationAlert.showUnsuccessfullAlert("Correos repetidos", "El correo y el correo alterno es el muismo");
        }else {
            concidenciaRFCFechaNacimiento();
        }
    }

    public void concidenciaRFCFechaNacimiento() {
        ValidacionGeneral validacionGeneral = new ValidacionGeneral();
        boolean esIgualRFC = validacionGeneral.validarConcidenciaRFC(rfcTextField.getText(), fechaNacimientoDatePicker.getEditor().getText());
        if(esIgualRFC){
            validarRepetidoCorreo();
        }else {
            rfcTextField.getStyleClass().remove("ok");
            fechaNacimientoDatePicker.getEditor().getStyleClass().remove("ok");
            rfcTextField.getStyleClass().add("error");
            fechaNacimientoDatePicker.getEditor().getStyleClass().add("error");
            OperationAlert.showUnsuccessfullAlert("Error con RFC y fecha de nacimiento", "El RFC no conincide con la fecha de nacimiento ingresada");
        }
    }

    public void validarRepetidoCorreo (){
        CuentaDAOImpl cuentaDAO = new CuentaDAOImpl();
        int esRepetidoCorreo = cuentaDAO.validarCorreoUsuario(emailTextField.getText());
        if(esRepetidoCorreo == Numero.CERO.getNumero()){
            validarRepetidoCorreoAlterno();
        }else {
            if(esRepetidoCorreo == Numero.UNO.getNumero()){
                emailTextField.getStyleClass().remove("ok");
                emailTextField.getStyleClass().add("error");
                OperationAlert.showUnsuccessfullAlert("Correo registrado", "El correo electronico ingresado ya esta registrado");
            }else {
                OperationAlert.showLostConnectionAlert();
            }
        }
    }

    public void validarRepetidoCorreoAlterno (){
        CuentaDAOImpl cuentaDAO = new CuentaDAOImpl();
        int esRepetidoCorreoAlterno = cuentaDAO.validarCorreoUsuario(altEmailTextField.getText());
        if(esRepetidoCorreoAlterno == Numero.CERO.getNumero()){
            validarTelefonoRFCUsuario();
        }else {
            if(esRepetidoCorreoAlterno == Numero.UNO.getNumero()){
                altEmailTextField.getStyleClass().remove("ok");
                altEmailTextField.getStyleClass().add("error");
                OperationAlert.showUnsuccessfullAlert("Correo alterno registrado", "El correo electronico alterno ingresado ya esta registrado");
            }else {
                OperationAlert.showLostConnectionAlert();
            }
        }
    }

    public void validarTelefonoRFCUsuario (){
        int esRepetidoUsuario = Usuario.validarRepetidoUsuario(phoneNumberTextField.getText(), rfcTextField.getText());
        if(esRepetidoUsuario == Numero.CERO.getNumero()){
            ingresarDatos();
        }else {
            if(esRepetidoUsuario == Numero.UNO.getNumero()){
                phoneNumberTextField.getStyleClass().remove("ok");
                rfcTextField.getStyleClass().remove("ok");
                phoneNumberTextField.getStyleClass().add("error");
                rfcTextField.getStyleClass().add("error");
                OperationAlert.showUnsuccessfullAlert("RFC o Telefono registrado", "El RFC o el Telefono ingresado ya estan registrados en el sistema");
            }else {
                OperationAlert.showLostConnectionAlert();
            }
        }
    }

    public String encryptPassword(String password) {
        String passwordEncrypt = null;
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte[] mb = md.digest();
            passwordEncrypt = String.valueOf(Hex.encodeHex(mb));
        } catch (NoSuchAlgorithmException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create an encrypt Password", e);
        }
        return passwordEncrypt;
    }

}