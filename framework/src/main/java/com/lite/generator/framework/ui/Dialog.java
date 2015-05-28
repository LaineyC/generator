package com.lite.generator.framework.ui;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.tool.FXMLLoaderTool;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Dialog<Controller extends BaseDialogController> extends Stage{

    private Controller controller;

    public Dialog(){
        FXMLLoader loader = FXMLLoaderTool.load(DialogController.class.getResourceAsStream("dialog.fxml"));
        AnchorPane root = loader.getRoot();
        DialogController dialogController = loader.getController();
        controller = loader.getController();
        controller.setDialogController(dialogController);
        this.setScene(new Scene(root, Color.TRANSPARENT));
        this.setResizable(false);
        this.initOwner(Application.getWindow());
        this.initStyle(StageStyle.TRANSPARENT);
        this.initModality(Modality.WINDOW_MODAL);
        this.titleProperty().bindBidirectional(dialogController.getTitleLabel().textProperty());
        /*this.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if(KeyCombination.keyCombination("Esc").match(event)){
                this.hide();
                event.consume();
            }
        });*/
    }

    public void setTitleGraphic(Node node){
        controller.getDialogController().getTitleLabel().setGraphic(node);
    }

    public ToolBar getToolBar() {
        return controller.getDialogController().getToolBar();
    }

    public void setContent(Node node) {
        controller.getDialogController().setContent(node);
    }

    public void setContent(Pane pane) {
        controller.getDialogController().setContent(pane);
    }

    public Node getContent(){
        return controller.getDialogController().getContent();
    }

    public Controller getController() {
        return controller;
    }

    protected void setController(Controller controller) {
        controller.setDialog(this);
        controller.setDialogController(this.getController().getDialogController());
        this.controller = controller;
    }

}
