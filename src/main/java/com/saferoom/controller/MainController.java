package com.saferoom.controller;

import com.jfoenix.controls.JFXButton;
import com.saferoom.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * MainView.fxml dosyasının kontrolcüsü.
 * Ana uygulama arayüzündeki navigasyonu ve sayfa yüklemelerini yönetir.
 */
public class MainController {

    // YENİ: Bu kontrolcünün tek bir örneğine statik erişim sağlamak için.
    private static MainController instance;

    @FXML private Label viewTitleLabel;
    @FXML private ScrollPane contentArea;
    @FXML private VBox navBox;
    @FXML private JFXButton dashboardButton;
    @FXML private JFXButton roomsButton;
    @FXML private JFXButton messagesButton;
    @FXML private JFXButton friendsButton;
    @FXML private JFXButton fileVaultButton;
    @FXML private JFXButton settingsButton;

    @FXML
    public void initialize() {
        // YENİ: Statik 'instance' alanını bu nesneye ayarla.
        instance = this;

        // Uygulama açıldığında Dashboard'u yükle ve aktif butonu ayarla
        handleDashboard();

        // Menü butonlarının tıklama olaylarını ilgili metodlara yönlendir
        dashboardButton.setOnAction(event -> handleDashboard());
        roomsButton.setOnAction(event -> handleRooms());
        messagesButton.setOnAction(event -> handleMessages());
        friendsButton.setOnAction(event -> handleFriends());
        fileVaultButton.setOnAction(event -> handleFileVault());
        settingsButton.setOnAction(event -> handleSettings());
    }

    /**
     * YENİ: Bu kontrolcünün örneğini döndüren statik metod.
     */
    public static MainController getInstance() {
        return instance;
    }

    private void handleDashboard() {
        setActiveButton(dashboardButton);
        viewTitleLabel.setText("Dashboard");
        loadView("DashboardView.fxml");
    }

    /**
     * GÜNCELLENDİ: Metod 'public' yapıldı.
     */
    public void handleRooms() {
        setActiveButton(roomsButton);
        viewTitleLabel.setText("Rooms");
        // TODO: "RoomsView.fxml" dosyasını yükle
        // loadView("RoomsView.fxml");
    }

    private void handleMessages() {
        setActiveButton(messagesButton);
        viewTitleLabel.setText("Messages");
        loadView("MessagesView.fxml");
    }

    private void handleFriends() {
        setActiveButton(friendsButton);
        viewTitleLabel.setText("Friends");
        loadView("FriendsView.fxml");
    }

    /**
     * GÜNCELLENDİ: Metod 'public' yapıldı.
     */
    public void handleFileVault() {
        setActiveButton(fileVaultButton);
        viewTitleLabel.setText("File Vault");
        loadView("FileVaultView.fxml");
    }

    private void handleSettings() {
        setActiveButton(settingsButton);
        viewTitleLabel.setText("Settings");
        loadView("SettingsView.fxml");
    }

    private Stage getStage() {
        return (Stage) viewTitleLabel.getScene().getWindow();
    }

    private void loadView(String fxmlFile) {
        try {
            // DİKKAT: Bu kısım, DashboardController'a referans vermek için değiştirilebilir.
            // Ancak şimdilik basit tutuyoruz.
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("view/" + fxmlFile)));
            contentArea.setContent(root);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Görünüm yüklenemedi: " + fxmlFile + "\nLütfen dosya yolunu ve içeriğini kontrol edin.");
            errorLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 16px; -fx-alignment: center;");
            errorLabel.setWrapText(true);
            VBox errorBox = new VBox(errorLabel);
            errorBox.setAlignment(Pos.CENTER);
            contentArea.setContent(errorBox);
        }
    }

    private void setActiveButton(JFXButton activeButton) {
        navBox.getChildren().forEach(node -> node.getStyleClass().remove("active"));
        activeButton.getStyleClass().add("active");
    }
}
