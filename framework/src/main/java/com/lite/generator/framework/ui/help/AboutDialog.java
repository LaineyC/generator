package com.lite.generator.framework.ui.help;


import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.tool.FXMLLoaderTool;
import com.lite.generator.framework.ui.Dialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AboutDialog  extends Dialog<AboutDialogController> {

    public AboutDialog() {
        FXMLLoader loader = FXMLLoaderTool.load(AboutDialogController.class.getResourceAsStream("about.fxml"));
        this.setTitleGraphic(new ImageView(Icons.about));
        this.setTitle("关于");
        AnchorPane root = loader.getRoot();
        this.setWidth(root.getPrefWidth() + 16);
        this.setHeight(root.getPrefHeight() + 76);
        this.setContent(root);
        this.setController(loader.getController());
    }

}