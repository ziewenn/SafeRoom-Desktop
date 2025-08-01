package com.saferoom.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class MessagesController {

    // FXML Dosyasındaki Bileşenler
    @FXML private SplitPane mainSplitPane;
    @FXML private BorderPane contactListPane; // YENİ: Sol panele erişim için eklendi
    @FXML private ListView<Contact> contactListView;
    @FXML private ListView<Message> messageListView;
    @FXML private Label chatPartnerName;
    @FXML private Label chatPartnerStatus;
    @FXML private Label chatPartnerAvatar;
    @FXML private TextField messageInputField;

    // Her bir sohbete ait mesajları saklamak için Map yapısı
    private final Map<String, ObservableList<Message>> messagesMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Ayırıcıyı başlangıç pozisyonuna ayarla
        mainSplitPane.setDividerPositions(0.30);

        // Ayırıcının hareketini sınırla
        limitSplitPaneDividerMovement();

        setupModelData();
        setupListViews();
        setupContactSelectionListener();

        // Başlangıçta ilk kişiyi seçili göster
        contactListView.getSelectionModel().selectFirst();
    }

    /**
     * SplitPane'in ayırıcısının belirli sınırlar arasında kalmasını sağlar.
     */
    private void limitSplitPaneDividerMovement() {
        // DÜZELTME: FXML'deki min/max genişlik kısıtlamalarını kaldırarak ayırıcının
        // serbestçe hareket etmesini sağla.
        if (contactListPane != null) {
            contactListPane.setMinWidth(0);
            contactListPane.setMaxWidth(Double.MAX_VALUE);
        }

        if (!mainSplitPane.getDividers().isEmpty()) {
            final SplitPane.Divider divider = mainSplitPane.getDividers().get(0);

            divider.positionProperty().addListener((obs, oldPos, newPos) -> {
                double minPosition = 0.25; // Sol panelin minimum genişliği (%25)
                double maxPosition = 0.40; // Sol panelin maksimum genişliği (%40)

                if (newPos.doubleValue() < minPosition) {
                    divider.setPosition(minPosition);
                } else if (newPos.doubleValue() > maxPosition) {
                    divider.setPosition(maxPosition);
                }
            });
        }
    }

    private void setupModelData() {
        // Örnek Kişi Verileri
        ObservableList<Contact> contacts = FXCollections.observableArrayList(
                new Contact("Zeynep Kaya", "Online", "Harika, teşekkürler!", "5m", 2),
                new Contact("Ahmet Çelik", "Offline", "Raporu yarın sabah gönderirim.", "1d", 0),
                new Contact("Proje Phoenix Grubu", "3 Online", "Toplantı 15:00'te.", "2h", 5)
        );
        contactListView.setItems(contacts);

        // Her kişi için örnek mesaj listeleri
        messagesMap.put("Zeynep Kaya", FXCollections.observableArrayList(
                new Message("Selam, projenin son durumu hakkında bilgi alabilir miyim?", false, "Z"),
                new Message("Tabii, raporu hazırlıyorum. Yarın sabah sende olur.", true, "U"),
                new Message("Harika, teşekkürler! Kolay gelsin.", false, "Z")
        ));
        messagesMap.put("Ahmet Çelik", FXCollections.observableArrayList(
                new Message("Raporu yarın sabah gönderirim.", false, "A")
        ));
        messagesMap.put("Proje Phoenix Grubu", FXCollections.observableArrayList(
                new Message("Toplantı 15:00'te.", false, "P")
        ));
    }

    private void setupListViews() {
        contactListView.setCellFactory(param -> new ContactCell());
        messageListView.setCellFactory(param -> new MessageCell());
    }

    private void setupContactSelectionListener() {
        contactListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                chatPartnerName.setText(newSelection.getName());
                chatPartnerStatus.setText(newSelection.getStatus());
                chatPartnerAvatar.setText(newSelection.getAvatarChar());

                // Duruma göre online/offline stilini ayarla
                chatPartnerStatus.getStyleClass().removeAll("status-online", "status-offline");
                chatPartnerStatus.getStyleClass().add(newSelection.isOnline() ? "status-online" : "status-offline");

                // Seçilen kişiye ait mesajları yükle
                messageListView.setItems(messagesMap.get(newSelection.getName()));
            }
        });
    }

    // =================================================================
    // MODEL SINIFLARI (Projenizde ayrı dosyalarda olmalı)
    // =================================================================
    public static class Contact {
        private final String name, status, lastMessage, time;
        private final int unreadCount;
        public Contact(String name, String status, String lastMessage, String time, int unreadCount) {
            this.name = name; this.status = status; this.lastMessage = lastMessage; this.time = time; this.unreadCount = unreadCount;
        }
        public String getName() { return name; }
        public String getStatus() { return status; }
        public String getLastMessage() { return lastMessage; }
        public String getTime() { return time; }
        public int getUnreadCount() { return unreadCount; }
        public String getAvatarChar() { return name.isEmpty() ? "" : name.substring(0, 1); }
        public boolean isOnline() { return status.equalsIgnoreCase("online"); }
    }

    public static class Message {
        private final String text, avatarChar;
        private final boolean sentByMe;
        public Message(String text, boolean sentByMe, String avatarChar) {
            this.text = text; this.sentByMe = sentByMe; this.avatarChar = avatarChar;
        }
        public String getText() { return text; }
        public boolean isSentByMe() { return sentByMe; }
        public String getAvatarChar() { return avatarChar; }
    }

    // =================================================================
    // ÖZEL HÜCRE SINIFLARI (ListCell)
    // =================================================================
    static class ContactCell extends ListCell<Contact> {
        private final HBox hbox = new HBox(15);
        private final Label avatar = new Label();
        private final Label nameLabel = new Label();
        private final Label lastMessageLabel = new Label();
        private final Label timeLabel = new Label();
        private final Label unreadBadge = new Label();
        private final VBox textVBox = new VBox(2);
        private final VBox timeVBox = new VBox(5);
        private final Pane spacer = new Pane();

        public ContactCell() {
            super();
            avatar.getStyleClass().add("contact-avatar");
            nameLabel.getStyleClass().add("contact-name");
            lastMessageLabel.getStyleClass().add("contact-last-message");
            timeLabel.getStyleClass().add("contact-time");
            unreadBadge.getStyleClass().add("unread-badge");
            timeVBox.setAlignment(Pos.TOP_RIGHT);
            HBox.setHgrow(spacer, Priority.ALWAYS);
            textVBox.getChildren().addAll(nameLabel, lastMessageLabel);
            timeVBox.getChildren().addAll(timeLabel, unreadBadge);
            hbox.getChildren().addAll(avatar, textVBox, spacer, timeVBox);
            hbox.setAlignment(Pos.CENTER_LEFT);
        }

        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);
            if (empty || contact == null) {
                setGraphic(null);
            } else {
                avatar.setText(contact.getAvatarChar());
                nameLabel.setText(contact.getName());
                lastMessageLabel.setText(contact.getLastMessage());
                timeLabel.setText(contact.getTime());
                unreadBadge.setVisible(contact.getUnreadCount() > 0);
                if (contact.getUnreadCount() > 0) {
                    unreadBadge.setText(String.valueOf(contact.getUnreadCount()));
                }
                setGraphic(hbox);
            }
        }
    }

    static class MessageCell extends ListCell<Message> {
        private final HBox hbox = new HBox(10);
        private final Label avatar = new Label();
        private final Label messageText = new Label();
        private final Pane spacer = new Pane(); // Mesajları itmek için boşluk

        public MessageCell() {
            super();
            avatar.getStyleClass().add("message-avatar");
            messageText.getStyleClass().add("message-bubble");
            messageText.setWrapText(true);
            HBox.setHgrow(spacer, Priority.ALWAYS); // Boşluğun esnek olmasını sağla

            listViewProperty().addListener((obs, oldListView, newListView) -> {
                if (newListView != null) {
                    messageText.maxWidthProperty().bind(
                            newListView.widthProperty().multiply(0.70)
                    );
                }
            });
        }

        @Override
        protected void updateItem(Message message, boolean empty) {
            super.updateItem(message, empty);
            if (empty || message == null) {
                setGraphic(null);
            } else {
                messageText.setText(message.getText());
                avatar.setText(message.getAvatarChar());

                if (message.isSentByMe()) {
                    // Gönderilen mesaj: [Boşluk] [Mesaj] [Avatar] -> Sağda görünür
                    hbox.getChildren().setAll(spacer, messageText, avatar);
                    messageText.getStyleClass().setAll("message-bubble", "sent");
                } else {
                    // Alınan mesaj: [Avatar] [Mesaj] [Boşluk] -> Solda görünür
                    hbox.getChildren().setAll(avatar, messageText, spacer);
                    messageText.getStyleClass().setAll("message-bubble", "received");
                }
                setGraphic(hbox);
            }
        }
    }
}
