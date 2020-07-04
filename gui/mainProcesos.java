package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainProcesos extends Application {
    @Override
    public void start(Stage stageLogin) throws Exception {
        Parent rootLogin = FXMLLoader.load(getClass().getResource("/gui/Avance/FXMLRegistrarAvance.fxml")); /* Aqui pongan el nombre de su fxml si lo quieren probar */
        Scene sceneLogin = new Scene(rootLogin);
        stageLogin.setScene(sceneLogin);
        stageLogin.setResizable(false);
        stageLogin.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
