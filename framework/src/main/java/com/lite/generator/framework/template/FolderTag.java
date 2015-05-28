package com.lite.generator.framework.template;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.el.Parser;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.util.FileUtil;
import javafx.beans.property.SimpleStringProperty;

public class FolderTag extends Tag {

    private SimpleStringProperty name = new SimpleStringProperty();

    private String path;

    public String getPath() {
        return path;
    }

    private void concatPath(Tag parent, Context context){
        if(parent == null || parent instanceof FunctionTag){
            path = Parser.parseString(this.getName(), context);
            return;
        }
        if(parent instanceof FolderTag){
            path = ((FolderTag)parent).getPath() + SystemVariable.fileSeparator +
                    Parser.parseString(this.getName(), context);
        }
        else{
            concatPath(parent.getParent(), context);
        }
    }

    public void execute(Context context){
        Context exeContext = new Context();
        exeContext.mergeVariable(context);
        concatPath(this.getParent(), exeContext);
        String generatePath = Application.getConfig().getGeneratePath();
        FileUtil.mkdirs(generatePath + SystemVariable.fileSeparator + this.path);
        this.getChildren().forEach(child -> {
            child.setParent(this);
            child.execute(exeContext);
        });
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty(){
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

}
