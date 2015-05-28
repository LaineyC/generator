package com.lite.generator.framework.ui.tool;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.tool.FXMLLoaderTool;
import com.lite.generator.framework.ui.Dialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class ConsoleDialog extends Dialog<ConsoleDialogController> {

    public ConsoleDialog(){
        FXMLLoader loader = FXMLLoaderTool.load(ConsoleDialogController.class.getResourceAsStream("console.fxml"));
        this.setTitleGraphic(new ImageView(Icons.console));
        this.initModality(Modality.NONE);
        this.setTitle("控制台");
        AnchorPane root = loader.getRoot();
        this.setContent(root);
        this.setWidth(root.getPrefWidth() + 16);
        this.setHeight(root.getPrefHeight() + 76);
        this.setController(loader.getController());
    }
}
