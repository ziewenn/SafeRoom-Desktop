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
    private VBox hubListContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterHubs(newValue);
        });
    }

    private void filterHubs(String searchText) {
        String lowerCaseSearchText = searchText.toLowerCase();
        for (Node node : hubListContainer.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane hubItem = (AnchorPane) node;
                Label hubNameLabel = findHubNameLabel(hubItem);

                if (hubNameLabel != null) {
                    String hubName = hubNameLabel.getText().toLowerCase();
                    if (hubName.contains(lowerCaseSearchText)) {
                        hubItem.setVisible(true);
                        hubItem.setManaged(true);
                    } else {
                        hubItem.setVisible(false);
                        hubItem.setManaged(false);
                    }
                }
            }
        }
    }

    private Label findHubNameLabel(AnchorPane hubItem) {
        for (Node node : hubItem.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                for (Node childNode : hbox.getChildren()) {
                    if (childNode.getStyleClass().contains("hub-name")) {
                        return (Label) childNode;
                    }
                }
            }
        }
        return null;
    }
} 