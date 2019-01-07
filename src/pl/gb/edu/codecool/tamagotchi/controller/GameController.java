package pl.gb.edu.codecool.tamagotchi.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    @FXML
    private HBox main_panel;

    @FXML
    private ProgressBar energy_progress_bar, hunger_progress_bar, hygiene_progress_bar;

    @FXML
    private ImageView pet_picture;

    @FXML
    private Label energy_progress_bar_value, hunger_progress_bar_value, hygiene_progress_bar_value;

    @FXML
    private Button sleep_button, feed_button, bath_button;

    private Image image;

    public void initialize() {
        newGame();
    }

    public void newGame() {
        game = new Game();
        timeline = new Timeline();

        sleep_button.setText("SLEEP");
        image = new Image(game.getPet().getPicture());
        pet_picture.setImage(image);
        main_panel.styleProperty().set("-fx-background-color:" + game.getPet().getBackground());

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
                setVisualParameters();
                image = new Image(game.getPet().getPicture());
                pet_picture.setImage(image);
                main_panel.styleProperty().set("-fx-background-color:" + game.getPet().getBackground());

                setEnergyProgressBarValues();
                setHungerProgressBarValues();
                setHygieneProgressBarValues();
                setBathButtonActions();
                setFeedButtonActions();
                setSleepButtonActions();

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

    private void setEnergyProgressBarValues() {
        double energy = game.getPet().getEnergy();
        if (energy > 1) {
            energy = 1;
        }
        energy_progress_bar.setProgress(energy);
        energy_progress_bar_value.setText((int) (energy * 100) + "%");
    }

    private void setHygieneProgressBarValues() {
        double hygiene = game.getPet().getHygiene();
        if (hygiene > 1) {
            hygiene = 1;
        }
        hygiene_progress_bar.setProgress(hygiene);
        hygiene_progress_bar_value.setText((int) (hygiene * 100) + "%");
    }

    private void setHungerProgressBarValues() {
        double hunger = game.getPet().getHunger();
        if (hunger > 1) {
            hunger = 1;
        }
        hunger_progress_bar.setProgress(hunger);
        hunger_progress_bar_value.setText((int) (hunger * 100) + "%");
    }

    private void setBathButtonActions() {
        boolean activityButtonFlag = game.getPet().isBathing();
        feed_button.setDisable(activityButtonFlag);
        bath_button.setDisable(activityButtonFlag);
        sleep_button.setDisable(activityButtonFlag);
    }

    private void setFeedButtonActions() {
        boolean activityButtonFlag = game.getPet().isEating();
        feed_button.setDisable(activityButtonFlag);
        bath_button.setDisable(activityButtonFlag);
        sleep_button.setDisable(activityButtonFlag);
    }

    private void setSleepButtonActions() {
        boolean activityButtonFlag = game.getPet().isSleeping();
        if(activityButtonFlag) {
            sleep_button.setText("WAKE UP");
            feed_button.setDisable(activityButtonFlag);
            bath_button.setDisable(activityButtonFlag);
        } else {
            sleep_button.setText("SLEEP");
            feed_button.setDisable(activityButtonFlag);
            bath_button.setDisable(activityButtonFlag);
        }
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

    public void setVisualParameters() {
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
        game.getPet().setSleeping(!game.getPet().isSleeping());
    }

    @FXML
    private void feedButtonAction() {
        game.getPet().setEating(true);
    }

    @FXML
    private void bathButtonAction() {
        game.getPet().setBathing(true);
    }

    public Game getGame() {
        return game;
    }
}
