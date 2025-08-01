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

    @FXML private Label viewTitleLabel;
    @FXML private ScrollPane contentArea;
    @FXML private VBox navBox;
    @FXML private JFXButton dashboardButton;
    @FXML private JFXButton messagesButton;
    @FXML private JFXButton friendsButton;
    @FXML private JFXButton fileVaultButton;
    @FXML private JFXButton settingsButton;

    @FXML
    public void initialize() {
        // Uygulama açıldığında Dashboard'u yükle ve aktif butonu ayarla
        handleDashboard();

        // Menü butonlarının tıklama olaylarını ilgili metodlara yönlendir
        dashboardButton.setOnAction(event -> handleDashboard());
        messagesButton.setOnAction(event -> handleMessages());
        friendsButton.setOnAction(event -> handleFriends());
        fileVaultButton.setOnAction(event -> handleFileVault());
        settingsButton.setOnAction(event -> handleSettings());
    }

    private void handleDashboard() {
        setActiveButton(dashboardButton);
        viewTitleLabel.setText("Dashboard");
        loadView("DashboardView.fxml");
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

    private void handleFileVault() {
        setActiveButton(fileVaultButton);
        viewTitleLabel.setText("File Vault");
        loadView("FileVaultView.fxml");
    }

    private void handleSettings() {
        setActiveButton(settingsButton);
        viewTitleLabel.setText("Settings");
        // DÜZELTME: "SettingsView.fxml" dosyasını yükle
        loadView("SettingsView.fxml");
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
            // FXML dosyasını resources klasöründen bulup yükler
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("view/" + fxmlFile)));
            // Yüklenen arayüzü ScrollPane'in içeriği olarak ayarlar
            contentArea.setContent(root);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            // Hata durumunda, kullanıcıya bir uyarı göster.
            Label errorLabel = new Label("Görünüm yüklenemedi: " + fxmlFile + "\nLütfen dosya yolunu ve içeriğini kontrol edin.");
            errorLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 16px; -fx-alignment: center;");
            errorLabel.setWrapText(true);
            VBox errorBox = new VBox(errorLabel);
            errorBox.setAlignment(Pos.CENTER);
            contentArea.setContent(errorBox);
        }
    }

    /**
     * Tıklanan butona "active" stilini ekler ve diğerlerinden kaldırır.
     * Bu sayede hangi menüde olduğumuz görsel olarak belli olur.
     * @param activeButton Aktif hale getirilecek buton.
     */
    private void setActiveButton(JFXButton activeButton) {
        // Önce, VBox içindeki tüm butonlardan 'active' stil sınıfını kaldır
        navBox.getChildren().forEach(node -> node.getStyleClass().remove("active"));
        // Sonra, sadece tıklanan butona 'active' stil sınıfını ekle
        activeButton.getStyleClass().add("active");
    }
}
