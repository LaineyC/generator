package com.lite.generator.framework.control;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

public class LineNumberTableCell<S, T> extends TableCell<S, T> {

    public LineNumberTableCell(){
        setAlignment(Pos.CENTER_RIGHT);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (isEditing()) {

            }
            else {
                Label lineNumber = new Label();
                lineNumber.getStyleClass().add("line-number");
                lineNumber.setText("" + (getIndex() + 1));
                setGraphic(lineNumber);
            }
        }
    }
}
