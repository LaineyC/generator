package com.lite.generator.framework.model;

import com.lite.generator.framework.template.TemplateConfig;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Map;

public class Config {

    private ObservableList<PropertyFeature> propertyFeatures = FXCollections.observableArrayList();

    private SimpleObjectProperty<TemplateConfig> templateConfig = new SimpleObjectProperty<>();

    private ObservableList<Map<String, ? extends WritableValue>> quickProperties = FXCollections.observableArrayList();

    private SimpleStringProperty generatePath = new SimpleStringProperty();

    private SimpleDoubleProperty windowWidth = new SimpleDoubleProperty();

    private SimpleDoubleProperty windowHeight = new SimpleDoubleProperty();

    private SimpleDoubleProperty dividerPosition = new SimpleDoubleProperty();

    private SimpleBooleanProperty windowMaximized = new SimpleBooleanProperty();

    public Config(){

    }

    public ObservableList<PropertyFeature> getPropertyFeatures() {
        return propertyFeatures;
    }

    public void setPropertyFeatures(ObservableList<PropertyFeature> propertyFeatures) {
        this.propertyFeatures = propertyFeatures;
    }

    public TemplateConfig getTemplateConfig() {
        return templateConfig.get();
    }

    public SimpleObjectProperty<TemplateConfig> templateConfigProperty() {
        return templateConfig;
    }

    public void setTemplateConfig(TemplateConfig templateConfig) {
        this.templateConfig.set(templateConfig);
    }

    public ObservableList<Map<String, ? extends WritableValue>> getQuickProperties() {
        return quickProperties;
    }

    public void setQuickProperties(ObservableList<Map<String, ? extends WritableValue>> quickProperties) {
        this.quickProperties = quickProperties;
    }

    public String getGeneratePath() {
        return generatePath.get();
    }

    public SimpleStringProperty generatePathProperty() {
        return generatePath;
    }

    public void setGeneratePath(String generatePath) {
        this.generatePath.set(generatePath);
    }


    public double getWindowWidth() {
        return windowWidth.get();
    }

    public SimpleDoubleProperty windowWidthProperty() {
        return windowWidth;
    }

    public void setWindowWidth(double windowWidth) {
        this.windowWidth.set(windowWidth);
    }

    public double getWindowHeight() {
        return windowHeight.get();
    }

    public SimpleDoubleProperty windowHeightProperty() {
        return windowHeight;
    }

    public void setWindowHeight(double windowHeight) {
        this.windowHeight.set(windowHeight);
    }

    public double getDividerPosition() {
        return dividerPosition.get();
    }

    public SimpleDoubleProperty dividerPositionProperty() {
        return dividerPosition;
    }

    public void setDividerPosition(double dividerPosition) {
        this.dividerPosition.set(dividerPosition);
    }

    public boolean getWindowMaximized() {
        return windowMaximized.get();
    }

    public SimpleBooleanProperty windowMaximizedProperty() {
        return windowMaximized;
    }

    public void setWindowMaximized(boolean windowMaximized) {
        this.windowMaximized.set(windowMaximized);
    }
}
