package com.lite.generator.framework.control;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.model.Module;
import com.lite.generator.framework.model.Project;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GroupTreeViewTableCell<S> extends TableCell<S, Group> {

    private TreeView<Group> groupTreeView;

    public GroupTreeViewTableCell(){

    }

    @Override
    public void startEdit() {
        if (! isEditable() || ! getTableView().isEditable() || ! getTableColumn().isEditable()) {
            return;
        }
        super.startEdit();
        if (isEditing()) {
            if (groupTreeView == null) {
                groupTreeView = createGroupTreeView();
            }
            setText(null);
            setGraphic(groupTreeView);
            String text = getItemText();
            if(text != null) {
                groupTreeView.getSelectionModel().select(getItem());
            }
            groupTreeView.requestFocus();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItemText());
        setGraphic(null);
    }

    @Override
    public void updateItem(Group item, boolean empty) {
        super.updateItem(item, empty);
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (isEditing()) {
                if (groupTreeView != null) {
                    String text = getItemText();
                    if(text != null) {
                        groupTreeView.getSelectionModel().select(getItem());
                    }
                }
                setText(null);
                setGraphic(groupTreeView);
            } else {
                setText(getItemText());
                setGraphic(null);
            }
        }
    }

    private TreeView<Group> createGroupTreeView() {
        TreeView<Group> groupTreeView = new TreeView<>();
        MultipleSelectionModel<TreeItem<Group>> selectionModel = groupTreeView.getSelectionModel();
        ContextMenu contextMenu = new ContextMenu();
        MenuItem select = new MenuItem();
        select.setOnAction(e ->
            GroupTreeViewTableCell.this.commitEdit((Group)selectionModel.getSelectedItem())
        );
        MenuItem cancelEdit = new MenuItem();
        cancelEdit.setText("取消编辑");
        cancelEdit.setOnAction(e ->
            GroupTreeViewTableCell.this.cancelEdit()
        );
        MenuItem cancel = new MenuItem();
        cancel.setText("取消选择");
        cancel.setOnAction(e ->
            GroupTreeViewTableCell.this.commitEdit(null)
        );
        contextMenu.getItems().addAll(select, cancelEdit, cancel);
        groupTreeView.setContextMenu(contextMenu);
        groupTreeView.setOnContextMenuRequested(e -> {
            TreeItem<Group> group = selectionModel.getSelectedItem();
            if(group instanceof Project){
                select.setText("选择项目");
                select.setGraphic(new ImageView(Icons.product));
            }
            else if(group instanceof Module){
                select.setText("选择模块");
                select.setGraphic(new ImageView(Icons.module));
            }
            else if(group instanceof Model){
                select.setText("选择模型");
                select.setGraphic(new ImageView(Icons.model));
            }
        });
        groupTreeView.setCellFactory(f -> {
            GroupTreeCell groupTreeCell = new GroupTreeCell();
            groupTreeCell.setOnMouseClicked(e -> {
                if(e.getClickCount() == 2){
                    GroupTreeViewTableCell.this.commitEdit((Group)selectionModel.getSelectedItem());
                }
            });
            return groupTreeCell;
        });
        groupTreeView.setRoot(Application.getController().getProject());
        return groupTreeView;
    }

    private String getItemText() {
        if(getItem() == null){
            return null;
        }
        /*Group root = getItem();
        while (root.getParent() != null){
            root = (Group)root.getParent();
        }
        if(!(root instanceof Project)){
            return null;
        }*/
        return getItem().getName();
    }

}
