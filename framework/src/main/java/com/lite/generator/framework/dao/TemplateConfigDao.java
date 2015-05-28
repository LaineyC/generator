package com.lite.generator.framework.dao;

import com.lite.generator.framework.template.*;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.util.XmlUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TemplateConfigDao {

    public ObservableList<TemplateConfig> getAll(){
        ObservableList<TemplateConfig> templateConfigs = FXCollections.observableArrayList();
        File path = new File(SystemVariable.templatePath);
        File[] configFiles = path.listFiles( (File dir, String name) -> name.endsWith(".xml"));
        if(configFiles != null) {
            for (File configFile : configFiles) {
                if (configFile.isFile()) {
                    templateConfigs.add(get(configFile.getName()));
                }
            }
        }
        return templateConfigs;
    }

    public TemplateConfig get(String name){
        TemplateConfig templateConfig;
        Document document;
        try{
            document = XmlUtil.read(SystemVariable.templatePath + SystemVariable.fileSeparator + name);
        }
        catch (Exception exception) {
            return null;
        }
        Element root = document.getRootElement();
        templateConfig = new TemplateConfig();
        templateConfig.setName(name);
        String description = root.attributeValue("description");
        templateConfig.setDescription(description);
        parseTag(root.elements(), templateConfig);
        return templateConfig;
    }

    private void parseTag(List<Element> elements, Tag parent){
        elements.forEach(element ->{
            String tagName = element.getName();
            if("folder".equals(tagName)){
                FolderTag pathTag = new FolderTag();
                pathTag.setName(element.attributeValue("name"));
                parent.getChildren().add(pathTag);
            }
            else if("file".equals(tagName)){
                FileTag fileTag = new FileTag();
                fileTag.setName(element.attributeValue("name"));
                fileTag.setTemplate(element.attributeValue("template"));
                parent.getChildren().add(fileTag);
            }
            else if("template-context".equals(tagName)){
                TemplateContextTag templateContextTag = new TemplateContextTag();
                templateContextTag.setVar(element.attributeValue("var"));
                templateContextTag.setValue(element.attributeValue("value"));
                parent.getChildren().add(templateContextTag);
            }
            else if("if".equals(tagName)){
                IfTag ifTag = new IfTag();
                ifTag.setTest(element.attributeValue("test"));
                parent.getChildren().add(ifTag);
            }
            else if("foreach".equals(tagName)){
                ForeachTag foreachTag = new ForeachTag();
                foreachTag.setItem(element.attributeValue("item"));
                foreachTag.setItems(element.attributeValue("items"));
                foreachTag.setStatus(element.attributeValue("status"));
                parent.getChildren().add(foreachTag);
            }
            else if("break".equals(tagName)){
                BreakTag breakTag = new BreakTag();
                parent.getChildren().add(breakTag);
            }
            else if("continue".equals(tagName)){
                ContinueTag continueTag = new ContinueTag();
                parent.getChildren().add(continueTag);
            }
            else if("out".equals(tagName)){
                OutTag outTag = new OutTag();
                outTag.setValue(element.attributeValue("value"));
                parent.getChildren().add(outTag);
            }
            else if("var".equals(tagName)){
                VarTag varTag = new VarTag();
                varTag.setName(element.attributeValue("name"));
                varTag.setValue(element.attributeValue("value"));
                parent.getChildren().add(varTag);
            }
            else if("function".equals(tagName)){
                FunctionTag functionTag = new FunctionTag();
                functionTag.setName(element.attributeValue("name"));
                int i = 1;
                String argument;
                List<String> arguments = new ArrayList<>();
                while((argument = element.attributeValue("argument" + i)) != null){
                    arguments.add(argument);
                    i++;
                }
                functionTag.setArguments(arguments.toArray(new String[arguments.size()]));
                parent.getChildren().add(functionTag);
            }
            else if("return".equals(tagName)){
                ReturnTag returnTag = new ReturnTag();
                parent.getChildren().add(returnTag);
            }
            else if("call".equals(tagName)){
                CallTag callTag = new CallTag();
                callTag.setFunction(element.attributeValue("function"));
                int i = 1;
                String argument;
                List<String> arguments = new ArrayList<>();
                while((argument = element.attributeValue("argument" + i)) != null){
                    arguments.add(argument);
                    i++;
                }
                callTag.setArguments(arguments.toArray(new String[arguments.size()]));
                parent.getChildren().add(callTag);
            }
            parseTag(element.elements(), parent.getChildren().get(parent.getChildren().size() - 1));
        });
    }

}
