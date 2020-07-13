package gui.cliente;

import accesoDatos.CuentaDAOImpl;
import dominio.Numero;
import dominio.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import gui.FXMLGeneralController;
import logica.ValidacionGeneral;


public class FXMLRegistrarseController extends FXMLGeneralController implements Initializable {
    @FXML private TextField tfNombre;
    @FXML private TextField tfRFC;
    @FXML private TextField tfApellidos;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfCorreo;
    @FXML private TextField tfCorreoAlterno;
    @FXML private PasswordField pfContraseña;
    @FXML private PasswordField pfConfirmacion;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private Button btnElegirCursos;
    @FXML private Button btnRegistrar;
    @FXML private Button btnCancelar;
    private static List<String> listaMaterias;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comenzarComponentes();
    }

    public void comenzarComponentes (){
        limitTextField(tfNombre,50);
        prohibitNumberTextField(tfNombre);
        limitTextField(tfApellidos,50);
        prohibitNumberTextField(tfApellidos);
        limitTextField(tfTelefono,10);
        prohibitWordTextField(tfTelefono);
        limitTextField(tfCorreo,150);
        prohibitSpacesTextField(tfCorreo);
        limitTextField(tfCorreoAlterno,150);
        prohibitSpacesTextField(tfCorreoAlterno);
        limitTextField(pfContraseña,28);
        prohibitSpacesTextField(pfContraseña);
        limitTextField(pfConfirmacion,28);
        prohibitSpacesTextField(pfConfirmacion);
        limitTextField(tfRFC,28);
        prohibitSpacesTextField(tfRFC);
        dpFechaNacimiento.getEditor().getStyleClass().add("details");
    }

    public void elegirCursos () {

    }

    public void registrar (){
        boolean esValidoDatos = validarDatos();
        if(esValidoDatos){
            validarCorreos();
        }else {
            generarAlerta("Ingrese Datos validos");
        }
    }

    public void cancelar (){
        abrirVentana("/gui/inicioSesion/FXMLIniciarSesion.fxml", btnCancelar);
    }

    public void validarRepetidoCorreo (){
        CuentaDAOImpl cuentaDAO = new CuentaDAOImpl();
        int esRepetidoCorreo = cuentaDAO.validarCorreoUsuario(tfCorreo.getText());
        if(esRepetidoCorreo == Numero.CERO.getNumero()){
            validarRepetidoCorreoAlterno();
        }else {
            if(esRepetidoCorreo == Numero.UNO.getNumero()){
                tfCorreo.getStyleClass().remove("ok");
                tfCorreo.getStyleClass().add("error");
                generarError("El correo estan registrado");
            }else {
                generarError("Error en la conexion. Intente mas tarde");
            }
        }
    }

    public void ingresarDatos (){
        ValidacionGeneral validacionGeneral = new ValidacionGeneral();
            Usuario usuario = new Usuario();
            usuario.setNombre(validacionGeneral.eliminarEspacios(tfNombre.getText()));
            usuario.setApellidos(validacionGeneral.eliminarEspacios(tfApellidos.getText()));
            usuario.setTelefono(tfTelefono.getText());
            usuario.setTipo("Cliente");
            usuario.setRFC(tfRFC.getText());
            usuario.setCorreo(tfCorreo.getText());
            usuario.setCorreoAlterno(tfCorreoAlterno.getText());
            usuario.setContrasenia(pfContraseña.getText());
            usuario.getFechaNacimiento();
    }

    public void validarTelefonoRFCUsuario (){
        int esRepetidoUsuario = Usuario.validarRepetidoUsuario(tfTelefono.getText(),tfRFC.getText());
        if(esRepetidoUsuario == Numero.CERO.getNumero()){
            ingresarDatos();
        }else {
            if(esRepetidoUsuario == Numero.UNO.getNumero()){
                tfTelefono.getStyleClass().remove("ok");
                tfRFC.getStyleClass().remove("ok");
                tfTelefono.getStyleClass().add("error");
                tfRFC.getStyleClass().add("error");
                generarError("El telefono o RFC estan registrado");
            }else {
                generarError("Error en la conexion. Intente mas tarde");
            }
        }
    }

    public void validarRepetidoCorreoAlterno (){
        CuentaDAOImpl cuentaDAO = new CuentaDAOImpl();
        int esRepetidoCorreoAlterno = cuentaDAO.validarCorreoUsuario(tfCorreoAlterno.getText());
        if(esRepetidoCorreoAlterno == Numero.CERO.getNumero()){
            validarTelefonoRFCUsuario();
        }else {
            if(esRepetidoCorreoAlterno == Numero.UNO.getNumero()){
                tfCorreoAlterno.getStyleClass().remove("ok");
                tfCorreoAlterno.getStyleClass().add("error");
                generarError("El correo alterno estan registrado");
            }else {
                generarError("Error en la conexion. Intente mas tarde");
            }
        }
    }

    public void validarContrasenia (){
        if(pfContraseña.getText().equals(pfConfirmacion.getText())){
            validarRepetidoCorreo();
        }else {
            pfConfirmacion.getStyleClass().remove("ok");
            pfContraseña.getStyleClass().remove("ok");
            pfContraseña.getStyleClass().add("error");
            pfConfirmacion.getStyleClass().add("error");
            generarAlerta("La contraseña y la confirmacion deben se las mismas");
        }
    }

    public void validarCorreos (){
        if(tfCorreo.getText().equals(tfCorreoAlterno.getText())){
            tfCorreoAlterno.getStyleClass().remove("ok");
            tfCorreo.getStyleClass().remove("ok");
            tfCorreoAlterno.getStyleClass().add("error");
            tfCorreo.getStyleClass().add("error");
            generarAlerta("El correo y correo alterno son los mismos");
        }else {
            validarContrasenia();
        }
    }

    public  void removerEstilos () {
        tfNombre.getStyleClass().remove("ok");
        tfRFC.getStyleClass().remove("ok");
        tfApellidos.getStyleClass().remove("ok");
        tfTelefono.getStyleClass().remove("ok");
        tfCorreo.getStyleClass().remove("ok");
        tfCorreoAlterno.getStyleClass().remove("ok");
        pfContraseña.getStyleClass().remove("ok");
        pfConfirmacion.getStyleClass().remove("ok");
        dpFechaNacimiento.getEditor().getStyleClass().remove("ok");
        tfNombre.getStyleClass().remove("error");
        tfRFC.getStyleClass().remove("error");
        tfApellidos.getStyleClass().remove("error");
        tfTelefono.getStyleClass().remove("error");
        tfCorreo.getStyleClass().remove("error");
        tfCorreoAlterno.getStyleClass().remove("error");
        pfContraseña.getStyleClass().remove("error");
        pfConfirmacion.getStyleClass().remove("error");
        dpFechaNacimiento.getEditor().getStyleClass().remove("error");
    }

    public boolean validarDatos (){
         boolean esValidoDatos=true;
        ValidacionGeneral validacionGeneral = new ValidacionGeneral();
         boolean esValidoNombre = validacionGeneral.validarNombre(tfNombre.getText());
         if(esValidoNombre){
             tfNombre.getStyleClass().add("ok");
         }else{
             tfNombre.getStyleClass().add("error");
             esValidoDatos=false;
         }
         boolean esValidoApellidos = validacionGeneral.validarApellidos(tfApellidos.getText());
         if(esValidoApellidos){
             tfApellidos.getStyleClass().add("ok");
         }else {
             tfApellidos.getStyleClass().add("error");
             esValidoDatos=false;
         }
         boolean esValidoRFC = validacionGeneral.validarRFC(tfRFC.getText());
         if(esValidoRFC){
             tfRFC.getStyleClass().add("ok");
         }else {
             tfRFC.getStyleClass().add("error");
             esValidoDatos=false;
         }
         boolean esValidoCorreo = validacionGeneral.validarCorreo(tfCorreo.getText());
         if(esValidoCorreo) {
             tfCorreo.getStyleClass().add("ok");
         }else {
             tfCorreo.getStyleClass().add("error");
             esValidoDatos=false;
         }

        boolean esValidoCorreoAlterno = validacionGeneral.validarCorreo(tfCorreoAlterno.getText());
        if(esValidoCorreoAlterno) {
            tfCorreoAlterno.getStyleClass().add("ok");
        }else {
            tfCorreoAlterno.getStyleClass().add("error");
            esValidoDatos=false;
        }

        boolean esValidoContraseña = validacionGeneral.validarContrasenia(pfContraseña.getText());
        if(esValidoContraseña) {
            pfContraseña.getStyleClass().add("ok");
        }else {
            pfContraseña.getStyleClass().add("error");
            esValidoDatos=false;
        }

        boolean esValidoConfirmacion = validacionGeneral.validarContrasenia(pfConfirmacion.getText());
        if(esValidoConfirmacion) {
            pfConfirmacion.getStyleClass().add("ok");
        }else {
            pfConfirmacion.getStyleClass().add("error");
            esValidoDatos=false;
        }

        boolean esValidoFechaNacimiento = validacionGeneral.validarPalabra(dpFechaNacimiento.getEditor().getText());
        if(esValidoFechaNacimiento){
            boolean esValidoFAñoNacimiento = validacionGeneral.validarAño(dpFechaNacimiento.getEditor().getText());
            if(esValidoFAñoNacimiento){
                dpFechaNacimiento.getEditor().getStyleClass().add("ok");
            }else{
                dpFechaNacimiento.getEditor().getStyleClass().add("error");
                esValidoDatos=false;
            }
        }else {
            dpFechaNacimiento.getEditor().getStyleClass().add("error");
            esValidoDatos=false;
        }

        boolean esValidoTelefono = validacionGeneral.validarTelefono(tfTelefono.getText());
        if (esValidoTelefono){
            tfTelefono.getStyleClass().add("ok");
        } else{
            tfTelefono.getStyleClass().add("error");
            esValidoDatos=false;
        }
         return  esValidoDatos;
    }
}
