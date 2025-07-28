package com.saferoom.controller;

import com.saferoom.utils.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * LoginView.fxml dosyasının kontrolcüsü.
 * Kullanıcı giriş işlemlerini ve arayüz etkileşimlerini yönetir.
 */
public class LoginController {

    // FXML dosyasındaki fx:id'ler ile bu değişkenler birbirine bağlanır.
    @FXML
    private VBox rootPane;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink signUpLink;

    /**
     * Bu metod, FXML dosyası yüklendiğinde JavaFX tarafından otomatik olarak çağrılır.
     * Buton tıklama gibi olayları ve başlangıç ayarlarını burada tanımlarız.
     */
    @FXML
    public void initialize() {
        // Butonun ve linkin genişliklerini, VBox'un genişliğine göre dinamik olarak ayarlıyoruz
        signInButton.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.8)); // Pencerenin %80'ine ayarla
        signUpLink.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.8));

        signInButton.setOnAction(event -> handleSignIn());
        signUpLink.setOnAction(event -> handleSignUp());
    }

    /**
     * "Sign In" butonuna tıklandığında çalışacak olan metod.
     */
    private void handleSignIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Giriş denendi. Kullanıcı Adı: " + username);

        // TODO: Backend ile iletişim kurarak giriş doğrulama mantığı buraya eklenecek.
        // Şimdilik, girişin başarılı olduğunu varsayarak ana ekrana geçiyoruz.

        try {
            // Mevcut pencereyi (Stage) alıyoruz. rootPane'in sahnesinden pencereye ulaşıyoruz.
            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            // ViewManager'ı kullanarak MainView.fxml'e geçiş yapıyoruz
            ViewManager.switchScene(currentStage, "MainView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Kullanıcıya bir hata mesajı göster (örneğin bir dialog ile).
        }
    }

    /**
     * "Sign Up" linkine tıklandığında çalışacak olan metod.
     */
    private void handleSignUp() {
        System.out.println("Kayıt ekranına geçiş yapılıyor...");
        // TODO: Mevcut sahneyi değiştirerek kayıt ekranını (RegisterView.fxml) yükleme mantığı buraya eklenecek.
    }
}
