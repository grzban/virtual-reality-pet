package pl.gb.edu.codecool.tamagotchi.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.gb.edu.codecool.tamagotchi.controller.GameController;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final int WIDTH = 850;
        final int HEIGHT = 800;

        setPrimaryStage(primaryStage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("virtual_reality_pet.fxml"));

        Parent root = loader.load();
        GameController controller = loader.getController();

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setOnCloseRequest(event -> {
            System.out.println(event);
            System.out.println("pressed close button");
            controller.getGame().getPet().setEnergy(0);
            controller.getGame().getPet().setSleeping(false);
        });

        primaryStage.setTitle("Tamagotchi - Virtual Reality Pet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}
