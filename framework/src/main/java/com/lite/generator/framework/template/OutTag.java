package com.lite.generator.framework.template;

import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.el.Parser;
import javafx.beans.property.SimpleStringProperty;

public class OutTag extends Tag{

    private SimpleStringProperty value = new SimpleStringProperty();

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
        Object value = Parser.parseObject(this.getValue(), context);
        System.out.println(value);
    }

}
