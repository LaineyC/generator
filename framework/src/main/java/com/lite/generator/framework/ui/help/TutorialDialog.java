package com.lite.generator.framework.ui.help;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.tool.FXMLLoaderTool;
import com.lite.generator.framework.ui.Dialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TutorialDialog extends Dialog<TutorialDialogController> {

    public TutorialDialog() {
        FXMLLoader loader = FXMLLoaderTool.load(TutorialDialogController.class.getResourceAsStream("tutorial.fxml"));
        this.setTitleGraphic(new ImageView(Icons.tutorial));
        this.setTitle("教程");
        AnchorPane root = loader.getRoot();
        this.setWidth(root.getPrefWidth() + 16);
        this.setHeight(root.getPrefHeight() + 76);
        this.setContent(root);
        this.setController(loader.getController());
    }

}
