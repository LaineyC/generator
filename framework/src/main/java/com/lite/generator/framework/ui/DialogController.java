package com.lite.generator.framework.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DialogController extends BaseDialogController{

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private ToolBar titleBar;

    @FXML
    private Label titleLabel;

    @FXML
    private ToolBar toolBar;

    public DialogController(){

    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    public void setContent(Pane pane) {
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        ObservableList<Node> children = contentPane.getChildren();
        children.clear();
        children.add(pane);
    }

    public Label getTitleLabel(){
        return titleLabel;
    }

    public void setContent(Node node) {
        ObservableList<Node> children = contentPane.getChildren();
        children.clear();
        children.add(node);
    }

    public Node getContent(){
        ObservableList<Node> children = contentPane.getChildren();
        if(children.isEmpty()){
            return null;
        }
        return contentPane.getChildren().get(0);
    }

    private double x;

    private double y;

    @FXML
    private void dialogDraggd(MouseEvent event) {
        Dialog dialog = (Dialog)root.getScene().getWindow();
        dialog.setX(event.getScreenX() - x);
        dialog.setY(event.getScreenY() - y);
        titleBar.setCursor(Cursor.OPEN_HAND);
    }

    @FXML
    private void dialogPressd(MouseEvent event) {
        Dialog dialog = (Dialog)root.getScene().getWindow();
        x = event.getScreenX() - dialog.getX();
        y = event.getScreenY() - dialog.getY();
    }

    @FXML
    private void dialogReleased(MouseEvent event) {
        titleBar.setCursor(Cursor.HAND);
    }

}
