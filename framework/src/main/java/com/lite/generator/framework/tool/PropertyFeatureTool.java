package com.lite.generator.framework.tool;

import com.lite.generator.framework.control.CheckBoxTableCell;
import com.lite.generator.framework.control.DatePickerTableCell;
import com.lite.generator.framework.control.GroupTreeViewTableCell;
import com.lite.generator.framework.converter.StringConverters;
import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.model.PropertyFeature;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PropertyFeatureTool {

    public static final Map<String, PropertyFeature> propertyFeatureMap = new HashMap<>();

    public static void setConverter(PropertyFeature propertyFeature){
        switch (propertyFeature.getType()){
            case Boolean:
                propertyFeature.setConverter(StringConverters.Boolean);
                break;
            case Integer:
                propertyFeature.setConverter(StringConverters.Integer);
                break;
            case Double:
                propertyFeature.setConverter(StringConverters.Double);
                break;
            case String:
                propertyFeature.setConverter(StringConverters.String);
                break;
            case Date:
                propertyFeature.setConverter(StringConverters.Date);
                break;
            case Enum:
                propertyFeature.setConverter(StringConverters.Enum);
                break;
            case Group:
                propertyFeature.setConverter(StringConverters.Group);
                break;
        }
    }

    public static Map<String, ? extends WritableValue> createProperty(){
        Map<String, WritableValue<?>> map = FXCollections.observableHashMap();
        propertyFeatureMap.forEach((k, p) -> {
            WritableValue<?> property = null;
            String defaultValue = p.getDefaultValue();
            switch (p.getType()){
                case Boolean:
                    String referenceValues = p.getReferenceValues();
                    if(referenceValues != null && !"".equals(referenceValues)){
                        property = defaultValue == null ? new SimpleBooleanProperty() :
                            new SimpleBooleanProperty(StringConverters.Boolean.fromString(defaultValue, referenceValues.split(",")));
                    }
                    else{
                        property = defaultValue == null ? new SimpleBooleanProperty() :
                            new SimpleBooleanProperty(StringConverters.Boolean.fromString(defaultValue));
                    }
                    break;
                case Integer:
                    property = defaultValue == null ? new SimpleObjectProperty<Integer>() :
                        new SimpleObjectProperty<>(StringConverters.Integer.fromString(defaultValue));
                    break;
                case Double:
                    property = defaultValue == null ? new SimpleObjectProperty<Integer>() :
                        new SimpleObjectProperty<>(StringConverters.Double.fromString(defaultValue));
                    break;
                case String:
                    property = defaultValue == null ? new SimpleStringProperty() :
                        new SimpleStringProperty(StringConverters.String.fromString(defaultValue));
                    break;
                case Date:
                    property = defaultValue == null ? new SimpleObjectProperty<Date>() :
                        new SimpleObjectProperty<>(StringConverters.Date.fromString(defaultValue));
                    break;
                case Enum:
                    property = defaultValue == null ? new SimpleStringProperty() :
                        new SimpleStringProperty(StringConverters.Enum.fromString(defaultValue, p.getReferenceValues().split(",")));
                    break;
                case Group:
                    property = defaultValue == null ? new SimpleObjectProperty<Class>() :
                        new SimpleObjectProperty<>(StringConverters.Group.fromString(defaultValue));
                    break;
            }
            map.put(p.getName(), property);
        });
        return map;
    }

    public static TableColumn<Map<String, ? extends WritableValue>, ?> createTableColumn(PropertyFeature propertyFeature){
        TableColumn<Map<String, ? extends WritableValue>, ?> column = new TableColumn<>(propertyFeature.getComment());
        column.setSortable(false);
        column.setPrefWidth(propertyFeature.getViewWidth());
        column.setCellValueFactory(new MapValueFactory(propertyFeature.getName()));
        String referenceValues = propertyFeature.getReferenceValues();
        switch (propertyFeature.getType()) {
            case Boolean:
                if(referenceValues != null && !"".equals(referenceValues)){
                    column.setCellFactory(f -> new CheckBoxTableCell(StringConverters.Boolean.newInstance(referenceValues.split(","))));
                }
                else {
                    column.setCellFactory(f -> new CheckBoxTableCell());
                }
                break;
            case Integer:
                column.setCellFactory(f -> {
                    TextFieldTableCell cell =  new TextFieldTableCell(StringConverters.Integer);
                    cell.setAlignment(Pos.CENTER_RIGHT);
                    return cell;
                });
                break;
            case Double:
                column.setCellFactory(f -> {
                    TextFieldTableCell cell = new TextFieldTableCell(StringConverters.Double);
                    cell.setAlignment(Pos.CENTER_RIGHT);
                    return cell;
                });
                break;
            case String:
                if(referenceValues != null && !"".equals(referenceValues)){
                    column.setCellFactory(f -> {
                        ComboBoxTableCell comboBoxTableCell = new ComboBoxTableCell<>(StringConverters.String, referenceValues.split(","));
                        comboBoxTableCell.setComboBoxEditable(true);
                        return comboBoxTableCell;
                    });
                }
                else {
                    column.setCellFactory(f -> new TextFieldTableCell(StringConverters.String));
                }
                break;
            case Date:
                column.setCellFactory(f -> {
                    DatePickerTableCell cell = new DatePickerTableCell(StringConverters.Date);
                    cell.setAlignment(Pos.CENTER);
                    return cell;
                });
                break;
            case Enum:
                column.setCellFactory(f -> {
                    ChoiceBoxTableCell cell = new ChoiceBoxTableCell(StringConverters.Enum, referenceValues.split(","));
                    cell.setAlignment(Pos.CENTER);
                    return cell;
                });
                break;
            case Group:
                column.setCellFactory(f -> {
                    GroupTreeViewTableCell cell = new GroupTreeViewTableCell();
                    cell.setAlignment(Pos.CENTER);
                    return cell;
                });
                break;
        }
        return column;
    }

    public static Map<String, ? extends WritableValue> cloneProperty(Map<String, ? extends WritableValue> sourceProperty){
        Map<String, ? extends  WritableValue> property = PropertyFeatureTool.createProperty();
        sourceProperty.forEach((k, v) ->
            property.get(k).setValue(v.getValue())
        );
        return property;
    }

    public static String toString(String propertyFeatureName, WritableValue<?> writableValue) {
        if(writableValue == null || writableValue.getValue() == null){
            return null;
        }
        String string = null;
        PropertyFeature propertyFeature = propertyFeatureMap.get(propertyFeatureName);
        switch (propertyFeature.getType()){
            case Boolean:
                string = StringConverters.Boolean.toString((Boolean)writableValue.getValue());
                break;
            case Integer:
                string = StringConverters.Integer.toString((Integer)writableValue.getValue());
                break;
            case Double:
                string = StringConverters.Double.toString((Double)writableValue.getValue());
                break;
            case String:
                string = StringConverters.String.toString((String)writableValue.getValue());
                break;
            case Date:
                string = StringConverters.Date.toString((Date)writableValue.getValue());
                break;
            case Enum:
                string = StringConverters.Enum.toString((String)writableValue.getValue(), propertyFeature.getReferenceValues().split(","));
                break;
            case Group:
                string = StringConverters.Group.toString((Group)writableValue.getValue());
                break;
        }
        return string;
    }

    public static WritableValue<?> fromString(String propertyFeatureName, String value) {
        if(value == null){
            return null;
        }
        WritableValue<?> writableValue = null;
        PropertyFeature propertyFeature = propertyFeatureMap.get(propertyFeatureName);
        switch (propertyFeature.getType()){
            case Boolean:
                writableValue = new SimpleBooleanProperty(StringConverters.Boolean.fromString(value));
                break;
            case Integer:
                writableValue = new SimpleObjectProperty<>(StringConverters.Integer.fromString(value));
                break;
            case Double:
                writableValue = new SimpleObjectProperty<>(StringConverters.Double.fromString(value));
                break;
            case String:
                writableValue = new SimpleStringProperty(StringConverters.String.fromString(value));
                break;
            case Date:
                writableValue = new SimpleObjectProperty<>(StringConverters.Date.fromString(value));
                break;
            case Enum:
                writableValue = new SimpleStringProperty(StringConverters.Enum.fromString(value, propertyFeature.getReferenceValues().split(",")));
                break;
            case Group:
                writableValue = new SimpleObjectProperty<>(StringConverters.Group.fromString(value));
                break;
        }
        return writableValue;
    }

}
