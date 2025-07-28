package com.saferoom.controller;

import com.saferoom.controller.cards.ActionCardController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class DashboardController {

    // FXML'deki fx:id'ler ile bu değişkenler birbirine bağlanır.
    // Bu, iç içe FXML'lerin kontrolcülerine erişmemizi sağlar.
    @FXML private ActionCardController newMeetingCardController;
    @FXML private ActionCardController joinRoomCardController;
    @FXML private ActionCardController scheduleRoomCardController;
    @FXML private ActionCardController encryptedFilesCardController;

    @FXML
    private ListView<String> activityListView;

    @FXML
    public void initialize() {
        // Kartların içeriğini ayarla
        newMeetingCardController.setData("M12 5v14m-7-7h14", "New Meeting", "Instant Secure Room");
        joinRoomCardController.setData("M5 12h14m-7-7 7 7-7 7", "Join Room", "Connect to Tunnel");
        scheduleRoomCardController.setData("M12 6v6l4 2 M12 22A10 10 0 1 1 12 2a10 10 0 0 1 0 20z", "Schedule Room", "Programmatic Sync");
        encryptedFilesCardController.setData("M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z M14 2v6h6 M12 18v-6 M10 14h4", "Encrypted Files", "File Vault");

        // Örnek aktivite verileri
        activityListView.getItems().addAll(
                "You started a secure room 'Project Phoenix'",
                "Zeynep Kaya joined 'Project Phoenix'",
                "You shared 'design_final_v3.zip' in File Vault",
                "New message from Ahmet Çelik"
        );
    }
}
