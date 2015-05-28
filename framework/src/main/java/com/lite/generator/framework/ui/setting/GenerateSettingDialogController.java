package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.control.GroupCheckBoxTreeCell;
import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.model.Config;
import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.ui.BaseDialogController;
import com.lite.generator.framework.ui.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class GenerateSettingDialogController extends BaseDialogController<MainController, GenerateSettingDialogControllerInterface> implements GenerateSettingDialogControllerInterface {

    @FXML
    private TextField generatePathTextField;

    @FXML
    private Button generatePathBrowseButton;

    @FXML
    private TreeView<Group> projectTreeView;

    @Override
    public void init(){
        generatePathBrowseButton.setGraphic(new ImageView(Icons.browse));
        generatePathBrowseButton.setOnAction((e) -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("选择代码生成路径");
            File file = directoryChooser.showDialog(Application.getWindow());
            getProxy().generatePathBrowse(file);
        });

        Button confirm = new Button("确定");
        confirm.getStyleClass().add("btn-primary");
        confirm.setOnAction((evt) -> getProxy().confirm());
        getDialog().getToolBar().getItems().add(confirm);

        Config generateConfig = getParent().getConfig();
        generatePathTextField.textProperty().bindBidirectional(generateConfig.generatePathProperty());

        projectTreeView.setCellFactory(p -> new GroupCheckBoxTreeCell());
        projectTreeView.setRoot(getParent().getProject());
    }

    @Override
    public void generatePathBrowse(File file){
        if (file != null) {
            generatePathTextField.setText(file.getPath());
        }
    }

    @Override
    public void show(){
        getDialog().show();
        //projectTreeView.setRoot(null);
        //projectTreeView.setRoot(getParent().getProject());
    }

    @Override
    public void confirm(){
        getDialog().hide();
    }

}
