package com.lite.generator.framework.control;

import com.lite.generator.framework.model.Model;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.*;

import java.io.Serializable;

public class DraggableTreeCell<T> extends TreeCell<T> implements Serializable {

    private static final DataFormat dragCell = new DataFormat("dragCell");

    private static final DataFormat dropCell = new DataFormat("dropCell");

    private static final ClipboardContent content = new ClipboardContent();

    private DraggableTreeCell<T> dropTreeCell;

    private int fromIndex;

    private int toIndex = -1;

    public DraggableTreeCell(){
        fromIndex = this.getIndex();

        this.setOnDragDetected((MouseEvent event) -> {
            if(DraggableTreeCell.this.getItem() != null){
                fromIndex = this.getIndex();
                toIndex = -1;
                dropTreeCell = null;
                content.clear();
                content.put(dragCell, DraggableTreeCell.this);
                Dragboard db = DraggableTreeCell.this.startDragAndDrop(TransferMode.MOVE);
                db.setContent(content);
            }
            event.consume();
        });

        this.setOnDragEntered((DragEvent event) -> {
            if (event.getGestureSource() != DraggableTreeCell.this && content.containsKey(dragCell)) {
                DraggableTreeCell<T> drag = (DraggableTreeCell<T>)content.get(dragCell);
                DraggableTreeCell<T> drop = DraggableTreeCell.this;
                if(drag.getIndex() > drop.getIndex()){
                    drop.getStyleClass().add("draggable-up");
                }
                else{
                    drop.getStyleClass().add("draggable-down");
                }
                /*if(drop.getItem() != null) {
                    drop.getListView().scrollTo(drop.getItem());
                }*/
            }
            event.consume();
        });

        this.setOnDragOver((DragEvent event) -> {
            if (event.getGestureSource() != DraggableTreeCell.this && content.containsKey(dragCell)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        this.setOnDragExited((DragEvent event) -> {
            if (event.getGestureSource() != DraggableTreeCell.this && content.containsKey(dragCell)) {
                DraggableTreeCell<T> drag = (DraggableTreeCell<T>)content.get(dragCell);
                DraggableTreeCell drop = DraggableTreeCell.this;
                if(drag.getIndex() > drop.getIndex()){
                    drop.getStyleClass().remove("draggable-up");
                }
                else{
                    drop.getStyleClass().remove("draggable-down");
                }
            }
            event.consume();
        });

        this.setOnDragDropped((DragEvent event) -> {
            if (event.getGestureSource() != DraggableTreeCell.this && content.containsKey(dragCell)) {
                event.setDropCompleted(true);
                content.put(dropCell, DraggableTreeCell.this);
            }
            event.consume();
        });

        this.setOnDragDone((DragEvent event) -> {
            DraggableTreeCell<T> drag = (DraggableTreeCell<T>)content.get(dragCell);
            DraggableTreeCell drop = (DraggableTreeCell)content.get(dropCell);
            if (drop != null && drop.getItem() != null && event.getTransferMode() == TransferMode.MOVE &&
                    drop.getTreeItem().getParent() != null) {
                MultipleSelectionModel<Model> selectionModel = drop.getTreeView().getSelectionModel();
                ObservableList<TreeItem<T>> dragItems = drag.getTreeItem().getParent().getChildren();
                ObservableList<TreeItem<T>> dropItems = drop.getTreeItem().getParent().getChildren();
                TreeItem<T> item = drag.getTreeItem();
                fromIndex = dragItems.indexOf(item);
                toIndex = dropItems.indexOf(drop.getItem());
                dropTreeCell = drop;
                int index = drop.getIndex();
                dragItems.remove(item);
                dropItems.add(toIndex, item);
                selectionModel.clearSelection();
                selectionModel.select(index);
            }
            event.consume();
        });
    }

    public int getToIndex() {
        return toIndex;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public DraggableTreeCell<T> getDropTreeCell() {
        return dropTreeCell;
    }

}
