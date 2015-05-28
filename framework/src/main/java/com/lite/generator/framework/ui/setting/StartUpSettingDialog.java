package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.tool.FXMLLoaderTool;
import com.lite.generator.framework.ui.Dialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class StartUpSettingDialog extends Dialog<StartUpSettingDialogController> {

    public StartUpSettingDialog(){
        FXMLLoader loader = FXMLLoaderTool.load(StartUpSettingDialogController.class.getResourceAsStream("startup-setting.fxml"));
        this.setTitleGraphic(new ImageView(Icons.startSetting));
        this.setTitle("启动设置");
        AnchorPane root = loader.getRoot();
        this.setContent(root);
        this.setWidth(root.getPrefWidth() + 16);
        this.setHeight(root.getPrefHeight() + 76);
        this.setController(loader.getController());
    }
}
