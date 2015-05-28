package com.lite.generator.framework.template;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.el.Parser;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.tool.TemplateTool;
import com.lite.generator.framework.util.FileUtil;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileTag extends Tag {

    private SimpleStringProperty template = new SimpleStringProperty();

    private SimpleStringProperty name = new SimpleStringProperty();

    private String path;

    public String getPath() {
        return path;
    }

    private void concatPath(Tag parent, Context context){
        if(parent == null || parent instanceof FunctionTag){
            this.path = Parser.parseString(this.getName(), context);
            return;
        }
        if(parent instanceof FolderTag){
            this.path = ((FolderTag)parent).getPath() + SystemVariable.fileSeparator +
                    Parser.parseString(this.getName(), context);
        }
        else{
            concatPath(parent.getParent(), context);
        }
    }

    public void execute(Context context){
        Context exeContext = new Context();
        exeContext.mergeVariable(context);
        String template = Parser.parseString(this.getTemplate(), exeContext);
        concatPath(this.getParent(), exeContext);
        String generatePath = Application.getConfig().getGeneratePath();
        String outFile = generatePath + SystemVariable.fileSeparator + path;
        String templateFile = SystemVariable.templatePath + SystemVariable.fileSeparator + template;
        Map<String,Object> data = new HashMap<>();
        this.getChildren().forEach(child -> {
            child.setParent(this);
            Map.Entry<String, Object> entry = ((TemplateContextTag)child).getTemplateContext(exeContext);
            data.put(entry.getKey(), entry.getValue());
        });
        File file = new File(outFile);
        FileUtil.mkdirs(file.getParent());
        TemplateTool.generate(templateFile, outFile, data);
    }

    public String getTemplate() {
        return template.get();
    }

    public SimpleStringProperty templateProperty() {
        return template;
    }

    public void setTemplate(String template) {
        this.template.set(template);
    }

    public SimpleStringProperty nameProperty(){
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

}
