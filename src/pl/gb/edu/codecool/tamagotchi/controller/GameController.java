package pl.gb.edu.codecool.tamagotchi.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pl.gb.edu.codecool.tamagotchi.model.Game;
import pl.gb.edu.codecool.tamagotchi.model.Pet;

import java.io.IOException;

public class GameController {

    private Game game;
    private Timeline timeline;
    private KeyFrame keyFrame;
    private int sleepTime = 30000;

    @FXML
    private HBox main_panel;

    @FXML
    private ProgressBar energy_progress_bar, hunger_progress_bar, hygiene_progress_bar;

    @FXML
    private ImageView pet_picture;

    @FXML
    private Label energy_progress_bar_value;

    @FXML
    private Label hunger_progress_bar_value;

    @FXML
    private Label hygiene_progress_bar_value;

    private Image image;

    public void initialize() {
        newGame();
    }

    public void newGame() {
        game = new Game();
        timeline = new Timeline();
        image = new Image(game.getPet().getPicture());
        pet_picture.setImage(image);
//        main_panel.styleProperty().set("-fx-background-color:" + game.getPet().getColor());

        energy_progress_bar.setProgress(1);
        energy_progress_bar_value.setText("100%");

        hygiene_progress_bar.setProgress(1);
        hygiene_progress_bar_value.setText("100%");

        hunger_progress_bar.setProgress(1);
        hunger_progress_bar_value.setText("100%");

        try {
            game.getGameThreat().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        keyFrame = new KeyFrame(new Duration(1000), event -> {

            if (game.isPetAlive()) {
                move();
                image = new Image(game.getPet().getPicture());
                pet_picture.setImage(image);
                main_panel.styleProperty().set("-fx-background-color:" + game.getPet().getBackground());

                energy_progress_bar.setProgress(game.getPet().getEnergy());
                energy_progress_bar_value.setText((int) (game.getPet().getEnergy() * 100) + "%");

                hygiene_progress_bar.setProgress(game.getPet().getHygiene());
                hygiene_progress_bar_value.setText((int) (game.getPet().getHygiene() * 100) + "%");

                hunger_progress_bar.setProgress(game.getPet().getHunger());
                hunger_progress_bar_value.setText((int) (game.getPet().getHunger() * 100) + "%");
            } else {
                timeline.stop();
                try {
                    showWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        game.getGameThreat().start();
        timeline.play();
    }

    private void showWindow() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/end_game_modal.fxml"));
        final Scene scene = new Scene(root, 250, 150);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    //TODO
//    zmienić nazwę funkcji
    public void move() {
        double energy = game.getPet().getEnergy();
        Pet pet = game.getPet();
        if (energy >= 0 && energy < 0.01) {
            pet.setBackground("black");
            pet.setPicture("Gollum_death.jpg");
        } else if (game.getPet().getEnergy() >= 0.01 && game.getPet().getEnergy() <= 0.2) {
            pet.setPicture("Gollum_angry.png");
            pet.setBackground("red");
        } else if (game.getPet().getEnergy() > 0.2 && game.getPet().getEnergy() < 0.5) {
            pet.setPicture("Gollum_imploring.png");
            pet.setBackground("orange");
        } else {
            pet.setPicture("Gollum_happy.png");
            pet.setBackground("green");
        }

        if (game.getPet().isEating()) {
            pet.setPicture("Gollum_eating.jpg");
        }

        if (game.getPet().isBathing()) {
            pet.setPicture("Gollum_bathing.jpg");
        }

        if (game.getPet().isSleeping()) {
            pet.setPicture("Gollum_sleeping.jpg");
        }

    }

    @FXML
    private void sleepButtonAction() {
        System.out.println("SLEEP");
        game.getPet().setSleeping(!game.getPet().isSleeping());
    }

    @FXML
    private void feedButtonAction() {
        System.out.println("FEED");
        game.getPet().setEating(true);
    }

    @FXML
    private void bathButtonAction() {
        System.out.println("BATH");
        game.getPet().setBathing(true);
    }

    public Game getGame() {
        return game;
    }
}
