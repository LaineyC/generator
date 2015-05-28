package com.lite.generator.framework.template;

import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.el.Parser;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class CallTag extends Tag{

    private SimpleStringProperty function  = new SimpleStringProperty();

    private SimpleObjectProperty<String[]> arguments = new SimpleObjectProperty<>();

    public String getFunction() {
        return function.get();
    }

    public SimpleStringProperty functionProperty() {
        return function;
    }

    public void setFunction(String function) {
        this.function.set(function);
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

    public void execute(Context context){
        String functionName = Parser.parseString(this.getFunction(), context);
        FunctionTag function = (FunctionTag)context.getVariable(functionName);

        Context exeContext = new Context();
        exeContext.mergeVariable(function.getParentContext());

        String[] arguments = function.getArguments();

        for(int i = 0 ; i < arguments.length ; i++){
            Object argumentValue = Parser.parseObject(getArguments()[i], context);
            exeContext.setVariable(arguments[i], argumentValue);
        }
        for(Tag child : function.getChildren()){
            child.setParent(function);
            try{
                child.execute(exeContext);
            }
            catch(ReturnException returnException){
                return;
            }
        }
    }

}
