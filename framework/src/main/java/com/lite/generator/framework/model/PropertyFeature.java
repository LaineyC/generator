package com.lite.generator.framework.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.StringConverter;

public class PropertyFeature {

    private SimpleStringProperty name = new SimpleStringProperty();

    private SimpleStringProperty comment = new SimpleStringProperty();

    private SimpleObjectProperty<PropertyType> type = new SimpleObjectProperty<>();

    private SimpleStringProperty defaultValue = new SimpleStringProperty();

    private SimpleObjectProperty<Double> viewWidth = new SimpleObjectProperty<>();

    private SimpleStringProperty checkStatement = new SimpleStringProperty();

    private SimpleStringProperty checkMessage = new SimpleStringProperty();

    private SimpleStringProperty referenceValues = new SimpleStringProperty();

    private SimpleObjectProperty<StringConverter> converter = new SimpleObjectProperty<>();

    public PropertyFeature(){

    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public PropertyType getType() {
        return type.get();
    }

    public SimpleObjectProperty<PropertyType> typeProperty() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type.set(type);
    }

    public String getDefaultValue() {
        return defaultValue.get();
    }

    public SimpleStringProperty defaultValueProperty() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue.set(defaultValue);
    }

    public Double getViewWidth() {
        return viewWidth.get();
    }

    public SimpleObjectProperty<Double> viewWidthProperty() {
        return viewWidth;
    }

    public void setViewWidth(Double viewWidth) {
        this.viewWidth.set(viewWidth);
    }

    public String getCheckStatement() {
        return checkStatement.get();
    }

    public SimpleStringProperty checkStatementProperty() {
        return checkStatement;
    }

    public void setCheckStatement(String checkStatement) {
        this.checkStatement.set(checkStatement);
    }

    public String getReferenceValues() {
        return referenceValues.get();
    }

    public SimpleStringProperty referenceValuesProperty() {
        return referenceValues;
    }

    public void setReferenceValues(String referenceValues) {
        this.referenceValues.set(referenceValues);
    }

    public StringConverter getConverter() {
        return converter.get();
    }

    public SimpleObjectProperty<StringConverter> converterProperty() {
        return converter;
    }

    public void setConverter(StringConverter converter) {
        this.converter.set(converter);
    }

    public String getCheckMessage() {
        return checkMessage.get();
    }

    public SimpleStringProperty checkMessageProperty() {
        return checkMessage;
    }

    public void setCheckMessage(String checkMessage) {
        this.checkMessage.set(checkMessage);
    }

}
