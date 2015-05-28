package com.lite.generator.framework.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;

public class Group extends TreeItem<Group>{

    private SimpleStringProperty id = new SimpleStringProperty();

    private SimpleStringProperty name = new SimpleStringProperty();

    private SimpleStringProperty comment = new SimpleStringProperty();

    private SimpleStringProperty description = new SimpleStringProperty();

    private SimpleBooleanProperty apply = new SimpleBooleanProperty();

    public Group(){
        setValue(this);
        setApply(true);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
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

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public boolean getApply() {
        return apply.get();
    }

    public SimpleBooleanProperty applyProperty() {
        return apply;
    }

    public void setApply(boolean apply) {
        this.apply.set(apply);
    }

    @Override
    public String toString(){
        return "Group [ id: " + getId() + ", name: " + getName() + " ]";
    }

}
