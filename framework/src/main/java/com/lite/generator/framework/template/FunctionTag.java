package com.lite.generator.framework.template;

import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.el.Parser;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class FunctionTag extends Tag{

    private Context parentContext;

    private SimpleStringProperty name = new SimpleStringProperty();

    private SimpleObjectProperty<String[]> arguments = new SimpleObjectProperty<>();

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String[] getArguments() {
        return arguments.get();
    }

    public SimpleObjectProperty<String[]> argumentsProperty() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments.set(arguments);
    }

    public Context getParentContext() {
        return parentContext;
    }

    public FunctionTag(){
        
    }

    public void execute(Context context){
        parentContext = context;
        String name = this.getName();
        context.setVariable(name, this);
    }

}
