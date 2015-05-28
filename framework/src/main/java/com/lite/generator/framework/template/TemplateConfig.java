package com.lite.generator.framework.template;

import com.lite.generator.framework.el.Context;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class TemplateConfig extends Tag {

    private SimpleBooleanProperty apply = new SimpleBooleanProperty();

    private SimpleStringProperty name = new SimpleStringProperty();

    private SimpleStringProperty description = new SimpleStringProperty();

    public void execute(Context context){
        Context exeContext = new Context();
        exeContext.mergeVariable(context);
        this.getChildren().forEach(child -> {
            child.setParent(this);
            child.execute(exeContext);
        });
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

}
