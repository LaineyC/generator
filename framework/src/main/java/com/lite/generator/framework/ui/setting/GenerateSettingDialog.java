package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.tool.FXMLLoaderTool;
import com.lite.generator.framework.ui.Dialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GenerateSettingDialog extends Dialog<GenerateSettingDialogController> {

    public GenerateSettingDialog(){
        FXMLLoader loader = FXMLLoaderTool.load(GenerateSettingDialogController.class.getResourceAsStream("generate-setting.fxml"));
        this.setTitleGraphic(new ImageView(Icons.generateSetting));
        this.setTitle("生成设置");
        AnchorPane root = loader.getRoot();
        this.setContent(root);
        this.setWidth(root.getPrefWidth() + 16);
        this.setHeight(root.getPrefHeight() + 76);
        this.setController(loader.getController());
    }
}
