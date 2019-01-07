package pl.gb.edu.codecool.tamagotchi.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.gb.edu.codecool.tamagotchi.view.Main;

import java.io.IOException;

public class EndGameController {
    @FXML
    private BorderPane end_modal_main;

    @FXML
    private void newGameButtonAction() {
        end_modal_main.getScene().getWindow().hide();

        Platform.runLater(() -> {
            try {
                run();
            } catch (IOException e) {
                System.err.println(e);
            }
        });
    }

    @FXML
    private void endGameButtonAction() {
        Platform.exit();
    }

    private void run() throws IOException {
        Stage stage = Main.getPrimaryStage();
        final double WIDTH = stage.getScene().getWidth();
        final double HEIGHT = stage.getScene().getHeight();
        Parent root = FXMLLoader.load(getClass().getResource("../view/virtual_reality_pet.fxml"));
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
    }
}
