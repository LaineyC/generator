package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.tool.FXMLLoaderTool;
import com.lite.generator.framework.ui.Dialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class QuickSettingDialog extends Dialog<QuickSettingDialogController> {

    public QuickSettingDialog(){
        FXMLLoader loader = FXMLLoaderTool.load(QuickSettingDialogController.class.getResourceAsStream("quick-setting.fxml"));
        this.setTitleGraphic(new ImageView(Icons.quickSetting));
        this.setTitle("快捷设置");
        AnchorPane root = loader.getRoot();
        this.setContent(root);
        this.setWidth(root.getPrefWidth() + 16);
        this.setHeight(root.getPrefHeight() + 76);
        this.setController(loader.getController());
    }
}
