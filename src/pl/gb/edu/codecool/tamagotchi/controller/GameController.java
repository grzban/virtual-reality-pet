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
    private Thread gameThreat;
    private Pet pet;

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
        gameThreat.start();
    }

    private void loadInitialParameters(){
        pet = new Pet();
        game = new Game(pet);
        gameThreat = new Thread(game, "GAME");
        timeline = new Timeline();

        sleep_button.setText("SLEEP");
        image = new Image(pet.getPicture());
        pet_picture.setImage(image);
        main_panel.styleProperty().set("-fx-background-color:" + pet.getBackground());

        energy_progress_bar.setProgress(1);
        energy_progress_bar_value.setText("100%");

        hygiene_progress_bar.setProgress(1);
        hygiene_progress_bar_value.setText("100%");

        hunger_progress_bar.setProgress(1);
        hunger_progress_bar_value.setText("100%");
    }

    private void newGame() {
        loadInitialParameters();
        try {
            gameThreat.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        KeyFrame keyFrame = new KeyFrame(new Duration(500), event -> {
            pet_picture.setImage(image);
            main_panel.styleProperty().set("-fx-background-color:" + pet.getBackground());

            if (game.isPetAlive()) {
                setVisualParameters();
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
        timeline.play();
    }

    private void setProgressBarValues() {
        setEnergyProgressBarValues();
        setHungerProgressBarValues();
        setHygieneProgressBarValues();
    }

    private void setEnergyProgressBarValues() {
        double energy = pet.getEnergy();
        if (energy > 1) {
            energy = 1;
        }
        energy_progress_bar.setProgress(energy);
        energy_progress_bar_value.setText((int) (energy * 100) + "%");
    }

    private void setHygieneProgressBarValues() {
        double hygiene = pet.getHygiene();
        if (hygiene > 1) {
            hygiene = 1;
        }
        hygiene_progress_bar.setProgress(hygiene);
        hygiene_progress_bar_value.setText((int) (hygiene * 100) + "%");
    }

    private void setHungerProgressBarValues() {
        double hunger = pet.getHunger();
        if (hunger > 1) {
            hunger = 1;
        }
        hunger_progress_bar.setProgress(hunger);
        hunger_progress_bar_value.setText((int) (hunger * 100) + "%");
    }

    private void setButtonsStates() {
        setFeedButtonState();
        setSleepButtonState();
        setBathButtonState();
    }

    private void setBathButtonState() {
        boolean activityButtonFlag = pet.isBathing();
        feed_button.setDisable(activityButtonFlag);
        bath_button.setDisable(activityButtonFlag);
        sleep_button.setDisable(activityButtonFlag);
    }

    private void setFeedButtonState() {
        boolean activityButtonFlag = pet.isEating();
        feed_button.setDisable(activityButtonFlag);
        bath_button.setDisable(activityButtonFlag);
        sleep_button.setDisable(activityButtonFlag);
    }

    private void setSleepButtonState() {
        boolean activityButtonFlag = pet.isSleeping();
        feed_button.setDisable(activityButtonFlag);
        bath_button.setDisable(activityButtonFlag);
        if (activityButtonFlag) {
            sleep_button.setText("WAKE UP");
        } else {
            sleep_button.setText("SLEEP");
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

    private void setVisualParameters() {
        image = new Image(pet.getPicture());
        pet_picture.setImage(image);
        main_panel.styleProperty().set("-fx-background-color:" + pet.getBackground());

        double energy = pet.getEnergy();
        double hunger = pet.getHunger();

        setButtonsStates();
        setProgressBarValues();

        if (hunger >= 0 && hunger < 0.01) {
            pet.petIsAngry();
        } else if(energy <= 0) {
            pet.petIsDead();
        } else if ((energy >= 0.01 && energy <= 0.2)) {
            pet.petIsAngry();
        } else if ((energy > 0.2 && energy < 0.5) || (hunger > 0.2  && hunger < 0.5)) {
            pet.petIsSad();
        } else {
            pet.petIsHappy();
        }

        if (pet.isEating()) {
            pet.petIsEating();
        }

        if (pet.isBathing()) {
            pet.petIsBathing();
        }

        if (pet.isSleeping()) {
            pet.petIsSleeping();
        }
    }

    @FXML
    private void sleepButtonAction() {
        pet.setSleeping(!pet.isSleeping());
    }

    @FXML
    private void feedButtonAction() {
        pet.setEating(true);
    }

    @FXML
    private void bathButtonAction() {
        pet.setBathing(true);
    }

    public Pet getPet() {
        return pet;
    }
}
