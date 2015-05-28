package com.lite.generator.framework.ui.group;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.model.Project;
import com.lite.generator.framework.ui.BaseController;
import com.lite.generator.framework.ui.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class ProjectController extends BaseController<MainController, ProjectControllerInterface> implements ProjectControllerInterface {


    @FXML
    private TextField projectNameTextfield;

    @FXML
    private TextField projectCommentTextfield;

    @FXML
    private TextArea projectDescriptionTextArea;

    @FXML
    private Button newModelButton;

    @FXML
    private Button newModuleButton;

    private Project project;

    public ProjectController(){

    }

    @Override
    public void init(Project project){
        this.project = project;
        newModelButton.setGraphic(new ImageView(Icons.model));
        newModelButton.setOnAction(e -> getParent().getProxy().newModel(project));
        newModuleButton.setGraphic(new ImageView(Icons.module));
        newModuleButton.setOnAction(e -> getParent().getProxy().newModule(project));
        projectNameTextfield.textProperty().bindBidirectional(project.nameProperty());
        projectCommentTextfield.textProperty().bindBidirectional(project.commentProperty());
        projectDescriptionTextArea.textProperty().bindBidirectional(project.descriptionProperty());
    }

}