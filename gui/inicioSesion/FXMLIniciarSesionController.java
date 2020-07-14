package gui.inicioSesion;

import gui.administrador.FXMLMenuAdministradorController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import logica.ValidacionGeneral;
import accesoDatos.CuentaDAOImpl;
import dominio.Numero;
import gui.FXMLGeneralController;


public class FXMLIniciarSesionController extends FXMLGeneralController implements Initializable {
	@FXML private TextField tfNombreUsuario;
	@FXML private PasswordField pfContraseña;
	@FXML private Button btnIniciarSesion;
	@FXML private Button btnCrearCliente;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		limitTextField(tfNombreUsuario,50);
		limitTextField(pfContraseña,20);
	}

	public void iniciarSesion() {
		removerEstilo();
		boolean esValidoInformacion = validateInformation();
		if(esValidoInformacion) {
			String correo = tfNombreUsuario.getText();
			String contrasenia = pfContraseña.getText();
			CuentaDAOImpl cuentaDAO= new CuentaDAOImpl();
			int esCuenta = cuentaDAO.buscarCuentaUsuario(correo,contrasenia);
			if(esCuenta== Numero.CERO.getNumero()){
				generarError("La cuenta no existe. Registrese");
			}else {
				if(esCuenta== Numero.UNO.getNumero()){
					String tipo = cuentaDAO.tipoUsuario(correo,contrasenia);
					switch (tipo) {
						case "Administrador":
							abrirVentana("/gui/administrador/ FXMLMenuAdministrador.fxml", btnIniciarSesion);
							break;
						case "Docente":
							abrirVentana("/gui/docente/FXMLMenuDocente.fxml", btnIniciarSesion);
							break;
						case "Cliente":
							abrirVentana("/gui/cliente/FXMLMenuCliente.fxml", btnIniciarSesion);
							break;
						default: generarInformation(tipo);
					}
				}else {
					generarError("Error en la conexion. Intente más tarde");
				}
			}
		}else {
			generarAlerta("Ingresar datos válidos");
		}
	}

	public void crearCuentaCliente () {
		abrirVentana("/gui/cliente/FXMLRegistrarse.fxml", btnCrearCliente);
	}

	private boolean validateInformation() {
		boolean validacion = true;
		ValidacionGeneral validacionGeneral = new ValidacionGeneral();
		boolean esValidoCuenta = validacionGeneral.validarPalabra(tfNombreUsuario.getText());
		esValidoCuenta = validacionGeneral.validarCorreo(tfNombreUsuario.getText());
		if (!esValidoCuenta) {
			tfNombreUsuario.getStyleClass().add("error");
			validacion = false;
		}else {
			tfNombreUsuario.getStyleClass().add("ok");
		}
		boolean esValidoContrasenia = validacionGeneral.validarPalabra(pfContraseña.getText());
		if (!esValidoContrasenia) {
			pfContraseña.getStyleClass().add("error");
			validacion = false;
		}else {
			pfContraseña.getStyleClass().add("ok");
		}
		return  validacion;
	}

	private void removerEstilo() {
		tfNombreUsuario.getStyleClass().remove("error");
		pfContraseña.getStyleClass().remove("error");
		tfNombreUsuario.getStyleClass().remove("ok");
		pfContraseña.getStyleClass().remove("ok");
	}
}
