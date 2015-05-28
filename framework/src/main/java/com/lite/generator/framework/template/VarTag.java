package com.lite.generator.framework.template;

import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.el.Parser;
import javafx.beans.property.SimpleStringProperty;

public class VarTag extends Tag{

    private SimpleStringProperty name = new SimpleStringProperty();

    private SimpleStringProperty value = new SimpleStringProperty();

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public void execute(Context context){
        String name = this.getName();
        Object value = Parser.parseObject(this.getValue(), context);
        context.setVariable(name, value);
    }

}
