package com.saferoom.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {

    @FXML
    private TextField searchTextField;

    @FXML
    private VBox serverListContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterServers(newValue);
        });
    }

    private void filterServers(String searchText) {
        String lowerCaseSearchText = searchText.toLowerCase();
        for (Node node : serverListContainer.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane serverItem = (AnchorPane) node;
                Label serverNameLabel = findServerNameLabel(serverItem);

                if (serverNameLabel != null) {
                    String serverName = serverNameLabel.getText().toLowerCase();
                    if (serverName.contains(lowerCaseSearchText)) {
                        serverItem.setVisible(true);
                        serverItem.setManaged(true);
                    } else {
                        serverItem.setVisible(false);
                        serverItem.setManaged(false);
                    }
                }
            }
        }
    }

    private Label findServerNameLabel(AnchorPane serverItem) {
        for (Node node : serverItem.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                for (Node childNode : hbox.getChildren()) {
                    if (childNode.getStyleClass().contains("server-name")) {
                        return (Label) childNode;
                    }
                }
            }
        }
        return null;
    }
} 