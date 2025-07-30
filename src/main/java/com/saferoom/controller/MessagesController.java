package com.saferoom.controller;

import com.saferoom.model.Contact;
import com.saferoom.model.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class MessagesController {

    @FXML private ListView<Contact> contactListView;
    @FXML private ListView<Message> messageListView;
    @FXML private Label chatPartnerNameLabel;
    @FXML private Label chatPartnerStatusLabel;
    @FXML private TextField messageInputField;

    private final Map<String, ObservableList<Message>> messagesMap = new HashMap<>();

    @FXML
    public void initialize() {
        setupModelData();
        setupListViews();
        setupContactSelectionListener();

        // Başlangıçta ilk kişiyi seçili göster
        contactListView.getSelectionModel().selectFirst();
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
        // ListView'lerin hücrelerini özel tasarımlarla doldurma
        contactListView.setCellFactory(param -> new ContactCell());
        messageListView.setCellFactory(param -> new MessageCell());
    }

    // Kişi listesinden birine tıklandığında çalışacak olan listener
    private void setupContactSelectionListener() {
        contactListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Başlığı güncelle
                chatPartnerNameLabel.setText(newSelection.getName());
                chatPartnerStatusLabel.setText(newSelection.getStatus());
                chatPartnerStatusLabel.getStyleClass().removeAll("status-online", "status-offline");
                chatPartnerStatusLabel.getStyleClass().add(newSelection.isOnline() ? "status-online" : "status-offline");

                // Mesaj listesini güncelle
                messageListView.setItems(messagesMap.get(newSelection.getName()));
            }
        });
    }

    // Kişi listesi için özel hücre (ListCell) sınıfı
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

                if (contact.getUnreadCount() > 0) {
                    unreadBadge.setText(String.valueOf(contact.getUnreadCount()));
                    unreadBadge.setVisible(true);
                } else {
                    unreadBadge.setVisible(false);
                }

                setGraphic(hbox);
            }
        }
    }

    // Mesaj listesi için özel hücre (ListCell) sınıfı
    static class MessageCell extends ListCell<Message> {
        private final HBox hbox = new HBox(10);
        private final Label avatar = new Label();
        private final Label messageText = new Label();

        public MessageCell() {
            super();
            avatar.getStyleClass().add("message-avatar");
            messageText.setWrapText(true); // Metinlerin alt satıra geçmesini sağlar
            hbox.setMaxWidth(450); // Mesaj balonlarının maksimum genişliği
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
                    hbox.setAlignment(Pos.CENTER_RIGHT);
                    hbox.getChildren().setAll(messageText, avatar);
                    messageText.getStyleClass().setAll("message-bubble", "sent");
                } else {
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    hbox.getChildren().setAll(avatar, messageText);
                    messageText.getStyleClass().setAll("message-bubble", "received");
                }
                setGraphic(hbox);
            }
        }
    }
}
