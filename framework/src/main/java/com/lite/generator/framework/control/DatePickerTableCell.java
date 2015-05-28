package com.lite.generator.framework.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePickerTableCell<S, T> extends TableCell<S, T> {

    private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty<>(this, "converter");

    private SimpleStringProperty pattern = new SimpleStringProperty();

    private DateTimeFormatter dateFormatter;

    private DatePicker datePicker;

    public DatePickerTableCell(StringConverter<T> converter){
        this(converter, "yyyy-MM-dd");
    }

    public DatePickerTableCell(StringConverter<T> converter, String pattern){
        setPattern(pattern);
        setConverter(converter);
        dateFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    public String getPattern() {
        return pattern.get();
    }

    public SimpleStringProperty patternProperty() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern.set(pattern);
    }

    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }

    public final void setConverter(StringConverter<T> value) {
        converterProperty().set(value);
    }

    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }

    @Override
    public void startEdit() {
        if (! isEditable() || ! getTableView().isEditable() || ! getTableColumn().isEditable()) {
            return;
        }
        super.startEdit();
        if (isEditing()) {
            if (datePicker == null) {
                datePicker = createDatePicker();
            }
            setText(null);
            setGraphic(datePicker);
            String text = getItemText();
            if(text != null) {
                datePicker.setValue(LocalDate.parse(text, dateFormatter));
            }
            datePicker.requestFocus();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItemText());
        setGraphic(null);
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
                if (datePicker != null) {
                    String text = getItemText();
                    if(text != null) {
                        datePicker.setValue(LocalDate.parse(text, dateFormatter));
                    }
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(getItemText());
                setGraphic(null);
            }
        }
    }

    private DatePicker createDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setShowWeekNumbers(true);
        datePicker.setOnAction(event -> {
            if(datePicker.getValue() == null){
                DatePickerTableCell.this.commitEdit(null);
            }
            else {
                DatePickerTableCell.this.commitEdit(converter.get().fromString(dateFormatter.format(datePicker.getValue())));
            }
            event.consume();
        });
        datePicker.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ESCAPE) {
                DatePickerTableCell.this.cancelEdit();
                t.consume();
            }
        });
        return datePicker;
    }

    private String getItemText() {
        return converter.get().toString(getItem());
    }

}
