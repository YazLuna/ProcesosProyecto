package gui.cliente;

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
    @FXML private RadioButton rbSeleccionMujer;
    @FXML private RadioButton rbSeleccionHombre;
    private static List<Integer> seleccionadosNRC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comenzarComponentes();
    }

    public void comenzarComponentes (){
        seleccionadosNRC = new ArrayList<>();
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/cliente/FXMLElegirCursos.fxml"));
        Stage stage = new Stage();
        try {
            Parent root1 = fxmlLoader.load();
            stage.setScene(new Scene(root1));
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        FXMLElegirCursosController fxmlElegirCursosController = new FXMLElegirCursosController();
        seleccionadosNRC = fxmlElegirCursosController.getListNRC();
    }

    public void registrar (){
        removerEstilos();
        boolean esValidoDatos = validarDatos();
        if(esValidoDatos){
            validarCorreos();
        }else {
            generarAlerta("Ingrese Datos validos");
        }
    }

    public void cancelar (){
        generarCancelacion("¿Seguro desea cancelar?",btnCancelar,"/gui/inicioSesion/FXMLIniciarSesion.fxml");
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

    public void concidenciaRFCFechaNacimiento () {
        ValidacionGeneral validacionGeneral = new ValidacionGeneral();
        boolean esIgualRFC = validacionGeneral.validarConcidenciaRFC(tfRFC.getText(), dpFechaNacimiento.getEditor().getText());
        if(esIgualRFC){
            validarRepetidoCorreo();
        }else {
            tfRFC.getStyleClass().remove("ok");
            dpFechaNacimiento.getEditor().getStyleClass().remove("ok");
            tfRFC.getStyleClass().add("error");
            dpFechaNacimiento.getEditor().getStyleClass().add("error");
            generarError("El RFC no coincide con el Fecha de Nacimiento");
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
        String contraseniaEncriptada = encryptPassword(pfContraseña.getText());
        usuario.setContrasenia(contraseniaEncriptada);
        String fecha = dpFechaNacimiento.getEditor().getText();
        String[] fechaNacimiento = fecha.split("/");
        String formatoFecha = fechaNacimiento[2]+"-"+fechaNacimiento[1]+"-"+fechaNacimiento[0];
        usuario.setFechaNacimiento(formatoFecha);
        if(rbSeleccionMujer.isSelected()){
            usuario.setGenero(Genero.MUJER.getGenero());
        }else {
            usuario.setGenero(Genero.HOMBRE.getGenero());
        }
        registrarUsuarioCompleto(usuario);
    }

    public void registrarUsuarioCompleto (Usuario usuario){
        CuentaDAOImpl cuentaDAO = new CuentaDAOImpl();
        boolean esRegistroUsuario = Usuario.registrarUsuario(usuario);
        if(esRegistroUsuario){
            boolean esRegistroCuenta = cuentaDAO.crearCuenta(usuario.getCorreo(),usuario.getCorreoAlterno(),usuario.getContrasenia(),usuario.getRFC());
            if(esRegistroCuenta){
                if(seleccionadosNRC.size()>Numero.CERO.getNumero()){
                    for (int index=Numero.CERO.getNumero(); index<seleccionadosNRC.size();index++){
                        Materia.registrarMateriaUsuario(seleccionadosNRC.get(index),usuario.getRFC());
                    }
                    generarInformation("Registró exitosamente");
                }else {
                    generarInformation("Registró exitosamente");
                }
                abrirVentana("/gui/inicioSesion/FXMLIniciarSesion.fxml",btnRegistrar);
            }else {
                generarError("No pudo registrarse");
            }
        }else {
            generarError("No pudo registrarse");
        }
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
            concidenciaRFCFechaNacimiento();
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
        rbSeleccionHombre.getStyleClass().remove("error");
        rbSeleccionMujer.getStyleClass().remove("error");
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
            boolean esValidoAñoNacimiento = validacionGeneral.validarAño(dpFechaNacimiento.getEditor().getText());
            if(esValidoAñoNacimiento){
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

        boolean seleccionMujer = rbSeleccionMujer.isSelected();
        boolean seleccionHombre = rbSeleccionHombre.isSelected();
        if(!seleccionHombre && !seleccionMujer){
            rbSeleccionHombre.getStyleClass().add("error");
            rbSeleccionMujer.getStyleClass().add("error");
            esValidoDatos=false;
        }

         return  esValidoDatos;
    }
}
