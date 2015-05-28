package com.lite.generator.framework.control;

import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.*;

import java.io.Serializable;

public class DraggableTableRow<T> extends TableRow<T> implements Serializable {

    private static final DataFormat dragRow = new DataFormat("dragRow");

    private static final DataFormat dropRow = new DataFormat("dropRow");

    private static final ClipboardContent content = new ClipboardContent();

    private DraggableTableRow<T> dropTableRow;

    private int fromIndex;

    private int toIndex = -1;

    public DraggableTableRow() {
        fromIndex = this.getIndex();

        this.setOnDragDetected((MouseEvent event) -> {
            if(DraggableTableRow.this.getItem() != null){
                fromIndex = this.getIndex();
                toIndex = -1;
                dropTableRow = null;
                content.clear();
                content.put(dragRow, DraggableTableRow.this);
                Dragboard db = DraggableTableRow.this.startDragAndDrop(TransferMode.MOVE);
                db.setContent(content);
            }
            event.consume();
        });

        this.setOnDragEntered((DragEvent event) -> {
            if (event.getGestureSource() != DraggableTableRow.this && content.containsKey(dragRow)) {
                DraggableTableRow<T> drag = (DraggableTableRow<T>)content.get(dragRow);
                DraggableTableRow<T> drop = DraggableTableRow.this;
                if(drag.getIndex() > drop.getIndex()){
                    drop.getStyleClass().add("draggable-up");
                }
                else{
                    drop.getStyleClass().add("draggable-down");
                }
            }
            event.consume();
        });

        this.setOnDragOver((DragEvent event) -> {
            if (event.getGestureSource() != DraggableTableRow.this && content.containsKey(dragRow)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        this.setOnDragExited((DragEvent event) -> {
            if (event.getGestureSource() != DraggableTableRow.this && content.containsKey(dragRow)) {
                DraggableTableRow<T> drag = (DraggableTableRow<T>)content.get(dragRow);
                DraggableTableRow<T> drop = DraggableTableRow.this;
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
            if (event.getGestureSource() != DraggableTableRow.this && content.containsKey(dragRow)) {
                event.setDropCompleted(true);
                content.put(dropRow, DraggableTableRow.this);
            }
            event.consume();
        });

        this.setOnDragDone((DragEvent event) -> {
            DraggableTableRow<T> drag = (DraggableTableRow<T>)content.get(dragRow);
            DraggableTableRow<T> drop = (DraggableTableRow<T>)content.get(dropRow);
            if (drop != null && drop.getItem() != null && event.getTransferMode() == TransferMode.MOVE) {
                TableView.TableViewSelectionModel<T> selectionModel = drop.getTableView().getSelectionModel();
                ObservableList items = drop.getTableView().getItems();
                Object item = drag.getItem();
                fromIndex = drag.getIndex();
                dropTableRow = drop;
                items.remove(item);
                int index = drop.getIndex();
                toIndex = index;
                items.add(index, item);
                selectionModel.clearSelection();
                selectionModel.select(index);
            }
            event.consume();
        });
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public DraggableTableRow<T> getDropTableRow() {
        return dropTableRow;
    }

}
