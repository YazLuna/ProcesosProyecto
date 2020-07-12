package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLGeneralController implements Initializable {
    @FXML private Button btnCerrarSesion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cerrarSesion() {
        boolean salir = generarConfirmacion("¿Desea Cerrar Sesión");
        if (salir){
            try {
                Stage principal = (Stage) btnCerrarSesion.getScene().getWindow();
                principal.close();
                FXMLLoader fxmlCarga = new FXMLLoader(getClass().getResource("/gui/inicioSesion/FXMLIniciarSesion.fxml"));
                Parent root = fxmlCarga.load();
                Stage escenario = new Stage();
                escenario.setResizable(false);
                escenario.setScene(new Scene(root));
                escenario.show();
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Fallo al crear ventana", e);
            }
        }
    }

    public void abrirVentana(String fxml, Button botonOrigen) {
        try {
            Stage stagePrincipal = (Stage) botonOrigen.getScene().getWindow();
            stagePrincipal.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void generarAlerta(String message) {
        Alert alerta = new Alert(Alert.AlertType.NONE);
        alerta.setAlertType(Alert.AlertType.WARNING);
        alerta.setHeaderText(message);
        alerta.setTitle("Advertencia");
        alerta.show();
    }

    public void generarError(String message) {
        Alert alerta = new Alert(Alert.AlertType.NONE);
        alerta.setAlertType(Alert.AlertType.ERROR);
        alerta.setHeaderText(message);
        alerta.setTitle("Error");
        alerta.showAndWait();
    }

    public boolean generarConfirmacion(String message) {
        boolean opcion = false;
        ButtonType YES = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, YES, NO);
        alert.setTitle("Confirmar");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.orElse(NO) == YES) {
            opcion = true;
        }
        return opcion;
    }

    public void generarInformation(String message) {
        Alert alerta = new Alert(Alert.AlertType.NONE);
        alerta.setAlertType(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(message);
        alerta.setTitle("Información");
        alerta.showAndWait();
    }

    public void generarCancelacion(String message, Button btnCancel, String fxml) {
        ButtonType YES = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert cancelar = new Alert(Alert.AlertType.CONFIRMATION, message, YES, NO);
        cancelar.setTitle("Cancelar");
        Optional<ButtonType> action = cancelar.showAndWait();
        if (action.orElse(NO) == YES) {
            abrirVentana(fxml, btnCancel);
        }
    }

    public static void limitTextField(TextField textField, int limit) {
        UnaryOperator<TextFormatter.Change> textLimitFilter = change -> {
            if (change.isContentChange()) {
                int newLength = change.getControlNewText().length();
                if (newLength > limit) {
                    String trimmedText = change.getControlNewText().substring(0, limit);
                    change.setText(trimmedText);
                    int oldLength = change.getControlText().length();
                    change.setRange(0, oldLength);
                }
            }
            return change;
        };
        textField.setTextFormatter(new TextFormatter(textLimitFilter));
    }

    public static void prohibitWordTextField(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public static void prohibitWordTextFieldAllowSpecialChar(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
                if (newValue.matches("^,")) {
                    textField.setText(newValue.replaceAll("[,]", ""));
                }else{
                    if (!newValue.matches("\\d*_,_\\s")) {
                        textField.setText(newValue.replaceAll("[^\\d_,_\\s]", ""));
                    }
                }
            }
        });
    }

    public static void prohibitNumberTextField(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
                if (newValue.matches("^\\s")) {
                    textField.setText(newValue.replaceAll("[\\s]", ""));
                } else {
                    if (!newValue.matches("[A-Za-z_\\s_ñ_á_é_í_ú_ó]")) {
                        textField.setText(newValue.replaceAll("[^A-Za-z_\\s_ñ_á_é_í_ú_ó]", ""));
                    }
                }
            }
        });
    }

    public static void prohibitNumberAllowSpecialCharTextField(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
                if (newValue.matches("^\\s")) {
                    textField.setText(newValue.replaceAll("[\\s]", ""));
                } else {
                    if (!newValue.matches("[A-Za-z_\\s_,_ñ_á_é_í_ú_ó]")) {
                        textField.setText(newValue.replaceAll("[^A-Za-z_\\s_,_ñ_á_é_í_ú_ó]", ""));
                    }
                }
            }
        });
    }


    public static void prohibitSpacesTextField(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
                if (newValue.matches("^\\s")) {
                    textField.setText(newValue.replaceAll("[\\s]", ""));
                }
            }
        });
    }
}