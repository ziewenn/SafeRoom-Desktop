package com.saferoom.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class SecureRoomController {

    @FXML private VBox rootPane;
    @FXML private JFXButton backButton;
    @FXML private Button initiateButton;
    @FXML private JFXComboBox<String> audioInputBox;
    @FXML private JFXComboBox<String> audioOutputBox;
    @FXML private JFXComboBox<String> cameraSourceBox;
    @FXML private JFXToggleButton cameraToggle;
    @FXML private JFXToggleButton micToggle;
    @FXML private ProgressBar micTestBar;
    @FXML private JFXSlider inputVolumeSlider;
    @FXML private JFXSlider outputVolumeSlider;
    @FXML private JFXCheckBox autoDestroyCheck;
    @FXML private JFXCheckBox noLogsCheck;

    private Timeline micAnimation;

    @FXML
    public void initialize() {
        // Örnek verilerle ComboBox'ları doldur
        audioInputBox.getItems().addAll("Default - MacBook Pro Microphone", "External USB Mic");
        audioOutputBox.getItems().addAll("Default - MacBook Pro Speakers", "Bluetooth Headphones");
        cameraSourceBox.getItems().addAll("FaceTime HD Camera", "External Webcam");

        // Başlangıç değerleri ata
        audioInputBox.getSelectionModel().selectFirst();
        audioOutputBox.getSelectionModel().selectFirst();
        cameraSourceBox.getSelectionModel().selectFirst();

        backButton.setOnAction(event -> handleBackToMain());
        initiateButton.setOnAction(event -> handleInitiate());

        // Mikrofon test animasyonunu başlat
        startMicTestAnimation();
    }

    private void handleInitiate() {
        System.out.println("Initiating Zero-Trace Room...");
        System.out.println("Camera On: " + cameraToggle.isSelected());
        System.out.println("Mic On: " + micToggle.isSelected());
        System.out.println("Auto-destroy: " + autoDestroyCheck.isSelected());
        System.out.println("No server-side logs: " + noLogsCheck.isSelected());
        // TODO: Odayı başlatma mantığı buraya eklenecek.
    }

    private void startMicTestAnimation() {
        micAnimation = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            Random random = new Random();
            micTestBar.setProgress(random.nextDouble() * 0.7);
        }));
        micAnimation.setCycleCount(Animation.INDEFINITE);
        micAnimation.play();
    }

    private void handleBackToMain() {
        try {
            if (micAnimation != null) {
                micAnimation.stop();
            }
            Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/saferoom/view/MainView.fxml")));
            if (rootPane.getScene() != null) {
                rootPane.getScene().setRoot(mainRoot);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
