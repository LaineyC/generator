package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.tool.FXMLLoaderTool;
import com.lite.generator.framework.ui.Dialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TemplateSettingDialog extends Dialog<TemplateSettingDialogController> {

    public TemplateSettingDialog(){
        FXMLLoader loader = FXMLLoaderTool.load(TemplateSettingDialogController.class.getResourceAsStream("template-setting.fxml"));
        this.setTitleGraphic(new ImageView(Icons.templateSetting));
        this.setTitle("模板设置");
        AnchorPane root = loader.getRoot();
        this.setContent(root);
        this.setWidth(root.getPrefWidth() + 16);
        this.setHeight(root.getPrefHeight() + 76);
        this.setController(loader.getController());
    }
}
