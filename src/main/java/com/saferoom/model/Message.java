package com.saferoom.model;

/**
 * Sohbet ekranındaki her bir mesajın verilerini tutan sınıf (Model).
 */
public class Message {
    private final String text;
    private final boolean sentByMe;
    private final String avatarChar;

    public Message(String text, boolean sentByMe, String avatarChar) {
        this.text = text;
        this.sentByMe = sentByMe;
        this.avatarChar = avatarChar;
    }

    // FXML ve Controller tarafından erişilecek olan Getter metodları
    public String getText() { return text; }
    public boolean isSentByMe() { return sentByMe; }
    public String getAvatarChar() { return avatarChar; }
}
