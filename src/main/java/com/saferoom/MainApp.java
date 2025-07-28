package com.saferoom;

import com.saferoom.utils.ResizeHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("view/LoginView.fxml")));
        primaryStage.setTitle("SafeRoom");




        Scene scene = new Scene(root);

        scene.getStylesheets().add(Objects.requireNonNull(MainApp.class.getResource("styles/styles.css")).toExternalForm());

        // Pencereyi fare ile sürüklenebilir hale getiriyoruz.
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        // DÜZELTME: Sahneyi pencereye atadıktan sonra yeniden boyutlandırma özelliğini ekliyoruz.
        primaryStage.setScene(scene);
        ResizeHelper.addResizeListener(primaryStage); // Bu satır setScene'den sonraya taşındı.

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
