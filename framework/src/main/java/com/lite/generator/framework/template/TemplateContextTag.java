package com.lite.generator.framework.template;

import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.el.Parser;
import javafx.beans.property.SimpleStringProperty;

import java.util.AbstractMap;
import java.util.Map;

public class TemplateContextTag extends Tag{

    private SimpleStringProperty var = new SimpleStringProperty();

    private SimpleStringProperty value = new SimpleStringProperty();

    public String getVar() {
        return var.get();
    }

    public SimpleStringProperty varProperty() {
        return var;
    }

    public void setVar(String var) {
        this.var.set(var);
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

    @Override
    public void execute(Context context) {
        //
    }

    public Map.Entry getTemplateContext(Context context) {
        Object value = Parser.parseObject(this.getValue(), context);
        return new AbstractMap.SimpleEntry<>(this.getVar(), value);
    }
}
