package gui.administrador;

import javafx.event.ActionEvent;
import gui.util.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MenuAdministradorController extends Controller {

    @FXML
    void agregarDocente(ActionEvent event) {
        stage.close();
        RegistrarDocenteController registrarDocenteController = new RegistrarDocenteController();
        registrarDocenteController.showStage();
    }

    @FXML
    void agregarMateria(ActionEvent event) {
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/administrador/FXMLRegistrarMateria.fxml"));
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
    }

    @FXML
    void closeStage(ActionEvent event) {
        stage.close();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/gui/administrador/MenuAdministrador.fxml"), this);
        stage.show();
    }

}
