package com.lite.generator.framework.tool;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.control.LineNumberTableCell;
import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.model.Module;
import com.lite.generator.framework.model.Project;
import com.lite.generator.framework.ui.Dialog;
import javafx.beans.value.WritableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Callback;

import java.util.Map;

public class ControlTool {

    public static TableColumn<Map<String, ? extends WritableValue>, ?> lineNumberTableCell(){
        TableColumn<Map<String, ? extends WritableValue>, ?> column = new TableColumn<>();
        column.setText("#");
        column.setSortable(false);
        column.setPrefWidth(20);
        column.setCellFactory(f -> new LineNumberTableCell<>());
        return column;
    }

    private static Tooltip messageTooltip;

    public static void messageTooltip(String message, Window tooltipWindow){
        if(messageTooltip == null){
            messageTooltip = new Tooltip();
            messageTooltip.setAutoHide(true);
            Image image = new Image(Application.class.getResourceAsStream("/logo.jpg"));
            ImageView imageView = new ImageView();
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setImage(image);
            messageTooltip.setGraphic(imageView);
            messageTooltip.setStyle("-fx-font-size:12px;");
        }
        messageTooltip.setText(message);
        double x = tooltipWindow.getX() + (tooltipWindow.getWidth() - messageTooltip.getWidth()) / 2.0;
        double y = tooltipWindow.getY() + (tooltipWindow.getHeight() - 250) / 2.0;
        messageTooltip.show(tooltipWindow, x, y);
    }

    private static Popup progressPopup;

    private static int loadTime = 0;

    public static void progressStart(String message, Window progressWindow){
        if(progressPopup == null){
            progressPopup = new Popup();
            ProgressBar progressBar = new ProgressBar();
            progressBar.getStyleClass().add("root");
            progressBar.getStylesheets().add(Application.class.getResource("/theme.css").toExternalForm());
            progressBar.setPrefWidth(200);
            Label label = new Label();
            VBox vBox = new VBox();
            vBox.setSpacing(5);
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(label);
            vBox.getChildren().add(progressBar);
            progressPopup.getContent().add(vBox);
        }
        VBox vBox = (VBox)progressPopup.getContent().get(0);
        ((Label)vBox.getChildren().get(0)).setText(message);
        progressWindow.getScene().getRoot().setDisable(true);
        double x = progressWindow.getX() + (progressWindow.getWidth() - 200) / 2.0;
        double y = progressWindow.getY() + (progressWindow.getHeight() - 200) / 2.0;
        progressPopup.show(progressWindow, x, y);
        loadTime++;
    }

    public static void progressForceStop(Window progressWindow){
        if(progressPopup != null){
            loadTime = 0;
            progressPopup.hide();
            progressWindow.getScene().getRoot().setDisable(false);
        }
    }

    public static void progressStop(Window progressWindow){
        if(progressPopup != null){
            loadTime--;
            if(loadTime == 0){
                progressPopup.hide();
                progressWindow.getScene().getRoot().setDisable(false);
            }
        }
    }

    private static Dialog confirmDialog;

    public static void confirmDialog(String message, Callback<ActionEvent, Object> callback){
        confirmDialog(message, callback, Application.getWindow());
    }

    public static  void confirmDialog(String message, Callback<ActionEvent, Object> callback, Window parentWindow){
        if(confirmDialog == null){
            confirmDialog = new Dialog();
            Label messageLabel = new Label();
            messageLabel.setWrapText(true);
            AnchorPane.setTopAnchor(messageLabel, 5.0);
            AnchorPane.setRightAnchor(messageLabel, 5.0);
            AnchorPane.setLeftAnchor(messageLabel, 5.0);
            confirmDialog.setTitle("确认");
            confirmDialog.setContent(messageLabel);
            confirmDialog.setWidth(400);
            confirmDialog.setHeight(200);
            Button confirm = new Button("确定");
            confirm.getStyleClass().add("btn-primary");
            Button cancel = new Button("取消");
            cancel.getStyleClass().add("btn-warning");
            cancel.setOnAction(e -> confirmDialog.hide());
            confirmDialog.getToolBar().getItems().addAll(cancel, confirm);
        }
        ((Label)confirmDialog.getContent()).setText(message);
        ((Button)confirmDialog.getToolBar().getItems().get(1)).setOnAction(e -> {
            callback.call(e);
            confirmDialog.hide();
        });
        confirmDialog.centerOnScreen();
        confirmDialog.show();
    }

    public static Project generateProject(Project projectSource){
        Project project = new Project();
        project.setId(projectSource.getId());
        project.setName(projectSource.getName());
        project.setComment(projectSource.getComment());
        project.setDescription(projectSource.getDescription());
        ObservableList<TreeItem<Group>> children = project.getChildren();
        projectSource.getChildren().forEach(child -> {
            Group group = (Group)child;
            if(group.getApply()){
                if(group instanceof Module){
                    children.add(generateModule((Module)group));
                }
                else{
                    children.add(generateModel((Model)group));
                }
            }
        });
        return project;
    }

    public static Module generateModule(Module moduleSource){
        Module module = new Module();
        module.setId(moduleSource.getId());
        module.setName(moduleSource.getName());
        module.setComment(moduleSource.getComment());
        module.setDescription(moduleSource.getDescription());
        ObservableList<TreeItem<Group>> children = module.getChildren();
        moduleSource.getChildren().forEach(child -> {
            Group group = (Group)child;
            if(group.getApply()){
                if(group instanceof Module){
                    children.add(generateModule((Module)group));
                }
                else{
                    children.add(generateModel((Model)group));
                }
            }
        });
        return module;
    }

    public static Model generateModel(Model modelSource){
        Model model = new Model();
        model.setId(modelSource.getId());
        model.setName(modelSource.getName());
        model.setComment(modelSource.getComment());
        model.setDescription(modelSource.getDescription());
        model.setProperties(modelSource.getProperties());
        return model;
    }

}
