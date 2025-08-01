package com.saferoom.controller;

import com.saferoom.controller.cards.ActionCardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class DashboardController {

    // FXML'deki <fx:include> etiketlerinin fx:id'lerine karşılık gelen VBox'lar
    @FXML private VBox newMeetingCard;
    @FXML private VBox joinRoomCard;
    @FXML private VBox scheduleRoomCard;
    @FXML private VBox encryptedFilesCard;

    // Dahil edilen FXML'lerin kontrolcüleri
    @FXML private ActionCardController newMeetingCardController;
    @FXML private ActionCardController joinRoomCardController;
    @FXML private ActionCardController scheduleRoomCardController;
    @FXML private ActionCardController encryptedFilesCardController;

    @FXML
    private ListView<String> activityListView;

    @FXML
    public void initialize() {
        // Kartların içeriğini ayarla
        newMeetingCardController.setData("fas-plus", "New Meeting", "Instant Secure Room");
        joinRoomCardController.setData("fas-arrow-right", "Join Meeting", "Connect to Tunnel");
        scheduleRoomCardController.setData("far-clock", "Schedule Room", "Programmatic Sync");
        encryptedFilesCardController.setData("fas-file-archive", "Encrypted Files", "File Vault");

        // Kartlara tıklama olaylarını ekle
        newMeetingCard.setOnMouseClicked(event -> handleNewMeeting());
        joinRoomCard.setOnMouseClicked(event -> handleJoinMeet());

        encryptedFilesCard.setOnMouseClicked(event -> {
            if (MainController.getInstance() != null) {
                MainController.getInstance().handleFileVault();
            }
        });

        // Örnek aktivite verileri
        activityListView.getItems().addAll(
                "You started a secure room 'Project Phoenix'",
                "Zeynep Kaya joined 'Project Phoenix'",
                "You shared 'design_final_v3.zip' in File Vault",
                "New message from Ahmet Çelik"
        );
    }

    /**
     * "New Meeting" kartına tıklandığında, mevcut sahnenin içeriğini değiştirir.
     */
    private void handleNewMeeting() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/saferoom/view/SecureRoomView.fxml")));
            if (newMeetingCard.getScene() != null) {
                newMeetingCard.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * "Join Meet" kartına tıklandığında, mevcut sahnenin içeriğini değiştirir.
     */
    private void handleJoinMeet() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/saferoom/view/JoinMeetView.fxml")));
            if (joinRoomCard.getScene() != null) {
                joinRoomCard.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
