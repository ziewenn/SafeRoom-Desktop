package com.saferoom.controller;

import com.jfoenix.controls.JFXButton;
import com.saferoom.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    @FXML private Label viewTitleLabel;
    @FXML private ScrollPane contentArea;
    @FXML private VBox navBox;
    @FXML private JFXButton dashboardButton;
    @FXML private JFXButton messagesButton;
    @FXML private JFXButton friendsButton;
    @FXML private JFXButton fileVaultButton;
    @FXML private JFXButton settingsButton;

    // Pencere kontrol butonları
    @FXML private JFXButton minimizeButton;
    @FXML private JFXButton maximizeButton;
    @FXML private JFXButton closeButton;


    @FXML
    public void initialize() {
        handleDashboard();

        dashboardButton.setOnAction(event -> handleDashboard());
        messagesButton.setOnAction(event -> handleMessages());
        friendsButton.setOnAction(event -> handleFriends());
        fileVaultButton.setOnAction(event -> handleFileVault());
        settingsButton.setOnAction(event -> handleSettings());

        // Pencere kontrol butonlarının olaylarını ayarla
        minimizeButton.setOnAction(event -> getStage().setIconified(true));
        maximizeButton.setOnAction(event -> {
            Stage stage = getStage();
            stage.setMaximized(!stage.isMaximized());
        });
        closeButton.setOnAction(event -> getStage().close());
    }

    private void handleDashboard() {
        setActiveButton(dashboardButton);
        viewTitleLabel.setText("Dashboard");
        loadView("DashboardView.fxml");
    }

    private void handleMessages() {
        setActiveButton(messagesButton);
        viewTitleLabel.setText("Messages");
        // loadView("MessagesView.fxml"); // Sonraki adımlarda eklenecek
    }

    private void handleFriends() {
        setActiveButton(friendsButton);
        viewTitleLabel.setText("Friends");
        // loadView("FriendsView.fxml"); // Sonraki adımlarda eklenecek
    }

    private void handleFileVault() {
        setActiveButton(fileVaultButton);
        viewTitleLabel.setText("File Vault");
        // loadView("FileVaultView.fxml"); // Sonraki adımlarda eklenecek
    }

    private void handleSettings() {
        setActiveButton(settingsButton);
        viewTitleLabel.setText("Settings");
        // loadView("SettingsView.fxml"); // Sonraki adımlarda eklenecek
    }

    /**
     * Mevcut sahnenin Stage'ini (penceresini) döndürür.
     */
    private Stage getStage() {
        return (Stage) viewTitleLabel.getScene().getWindow();
    }

    /**
     * Belirtilen FXML dosyasını ana içerik alanına yükler.
     * @param fxmlFile Yüklenecek FXML dosyasının adı
     */
    private void loadView(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("view/" + fxmlFile)));
            contentArea.setContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setActiveButton(JFXButton activeButton) {
        navBox.getChildren().forEach(node -> node.getStyleClass().remove("active"));
        activeButton.getStyleClass().add("active");
    }
}
