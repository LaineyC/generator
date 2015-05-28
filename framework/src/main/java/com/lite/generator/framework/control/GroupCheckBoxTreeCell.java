package com.lite.generator.framework.control;

import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.model.Project;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GroupCheckBoxTreeCell extends TreeCell<Group> {

    private Group group;

    private CheckBox checkBox;

    public GroupCheckBoxTreeCell(){
        this.checkBox = new CheckBox();
    }

    @Override
    public void updateItem(Group item, boolean empty) {
        super.updateItem(item, empty);
        if(group != null) {
            checkBox.selectedProperty().unbindBidirectional(group.applyProperty());
        }
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (isEditing()) {

            }
            else {
                if(item != null) {
                    checkBox.selectedProperty().bindBidirectional(item.applyProperty());
                    graphic(item.getName());
                }
            }
        }
        group = item;
    }

    private void graphic(String name){
        TreeItem<Group> parent = getItem().getParent();
        int size = parent == null ? 0 : parent.getChildren().size();
        String index = parent == null ? "" : (parent.getChildren().indexOf(getItem()) + 1) + " ";
        HBox hBox = new HBox();
        Label lineNumber = new Label();
        lineNumber.setAlignment(Pos.CENTER_RIGHT);
        lineNumber.getStyleClass().add("line-number");
        lineNumber.setText(index);
        lineNumber.setPrefWidth((int) Math.log10(size) * 10 + 10);
        Label className = new Label();
        className.setText(name);
        ImageView imageView = (ImageView)getItem().getGraphic();
        ImageView icon = new ImageView(imageView.getImage());
        if(getItem() instanceof Project){
            hBox.getChildren().addAll(icon, lineNumber, className);
        }
        else{
            hBox.getChildren().addAll(checkBox, icon, lineNumber, className);
        }
        setGraphic(hBox);
    }

}
