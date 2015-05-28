package com.lite.generator.framework.template;

import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.el.Parser;
import javafx.beans.property.SimpleStringProperty;

public class IfTag extends Tag{

    private SimpleStringProperty test = new SimpleStringProperty();

    public SimpleStringProperty testProperty() {
        return test;
    }

    public String getTest() {
        return test.get();
    }

    public void setTest(String test) {
        this.test.set(test);
    }

    public void execute(Context context){
        Context exeContext = new Context();
        exeContext.mergeVariable(context);
        boolean test = Parser.parseBoolean(this.getTest(), exeContext);
        if(test) {
            this.getChildren().forEach(child -> {
                child.setParent(this);
                child.execute(exeContext);
            });
        }
    }

}
