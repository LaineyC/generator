package com.lite.generator.framework.ui.group;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.model.Module;
import com.lite.generator.framework.tool.ControlTool;
import com.lite.generator.framework.ui.BaseController;
import com.lite.generator.framework.ui.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class ModuleController extends BaseController<MainController, ModuleControllerInterface> implements ModuleControllerInterface {

    @FXML
    private TextField moduleNameTextfield;

    @FXML
    private TextField moduleCommentTextfield;

    @FXML
    private TextArea moduleDescriptionTextArea;

    @FXML
    private Button newModelButton;

    @FXML
    private Button newSubModuleButton;

    @FXML
    private Button deleteModuleButton;

    private Module module;

    @Override
    public void init(Module module){
        this.module = module;
        newModelButton.setGraphic(new ImageView(Icons.model));
        newModelButton.setOnAction(e -> getParent().getProxy().newModel(module));
        newSubModuleButton.setGraphic(new ImageView(Icons.module));
        newSubModuleButton.setOnAction(e -> getParent().getProxy().newModule(module));
        deleteModuleButton.setGraphic(new ImageView(Icons.delete));
        deleteModuleButton.setOnAction(e ->
            ControlTool.confirmDialog("确定删除模块 [ " + module.getName() + " ] ？", event -> {
                getParent().getProxy().deleteModule(module);
                return null;
            })
        );
        moduleNameTextfield.textProperty().bindBidirectional(module.nameProperty());
        moduleCommentTextfield.textProperty().bindBidirectional(module.commentProperty());
        moduleDescriptionTextArea.textProperty().bindBidirectional(module.descriptionProperty());
    }

}
