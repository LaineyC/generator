package com.lite.generator.framework.model;

import com.lite.generator.framework.icon.Icons;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

import java.util.Map;

public class Model extends Group {

    private ObservableList<Map<String, ? extends WritableValue>> properties = FXCollections.observableArrayList();

    public Model(){
        setGraphic(new ImageView(Icons.model));
    }

    public ObservableList<Map<String, ? extends WritableValue>> getProperties() {
        return properties;
    }

    public void setProperties(ObservableList<Map<String, ? extends WritableValue>> properties) {
        this.properties = properties;
    }

}
