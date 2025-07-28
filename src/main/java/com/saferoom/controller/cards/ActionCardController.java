package com.saferoom.controller.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.SVGPath;

public class ActionCardController {

    @FXML
    private SVGPath iconPath;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    /**
     * Bu metod, DashboardController tarafından çağrılarak
     * her bir kartın ikonunu, başlığını ve açıklamasını ayarlar.
     * @param svgContent SVG ikonunun path verisi
     * @param title Kartın başlığı
     * @param description Kartın açıklaması
     */
    public void setData(String svgContent, String title, String description) {
        iconPath.setContent(svgContent);
        titleLabel.setText(title);
        descriptionLabel.setText(description);
    }
}
