package com.lite.generator.framework.service;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.el.Parser;
import com.lite.generator.framework.model.*;
import com.lite.generator.framework.util.StringUtil;
import javafx.beans.value.WritableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ValidateService {

    private String validateChildGroup(ObservableList<TreeItem<Group>> children){
        String message = null;
        for(int i = 1 ; i < children.size() + 1; i++){
            Group child = (Group)children.get(i - 1);
            if(child instanceof Model){
                message = validateModel((Model)child);
            }
            else{
                message = validateModule((Module)child);
            }
            if(message != null){
                return i + " -> " + message;
            }
            for(int j = 1 ; j < children.size() + 1 ; j++){
                Group otherChild = (Group)children.get(j - 1);
                if(otherChild instanceof Model){
                    message = validateModel((Model)otherChild);
                }
                else{
                    message = validateModule((Module)otherChild);
                }
                if(message != null){
                    return j + " -> " + message;
                }
                if(child != otherChild && otherChild.getName().equals(child.getName())){
                    return "[ " + i + ", " + j + " ] [ 名称 ] 重复！";
                }
            }
        }
        return message;
    }

    public String validateProject(Project project){
        String name = project.getName();
        if(StringUtil.isBlank(name)){
            return "项目 [ 名称 ] 未填写";
        }
        return validateChildGroup(project.getChildren());
    }

    public String validateModule(Module module){
        String name = module.getName();
        if(StringUtil.isBlank(name)){
            return "模块 [ 名称 ] 未填写";
        }
        return validateChildGroup(module.getChildren());
    }

    public String validateModel(Model model){
        String name = model.getName();
        if(StringUtil.isBlank(name)){
            return "模型 [ 名称 ] 未填写";
        }
        return validateProperties(model.getProperties());
    }

    public String validateProperties(ObservableList<Map<String, ? extends WritableValue>> properties) {
        ObservableList<PropertyFeature> propertyFeatures = Application.getConfig().getPropertyFeatures();
        Map<String, PropertyFeature> checkMap = new HashMap<>();
        propertyFeatures.forEach(propertyFeature -> {
            String checkStatement = propertyFeature.getCheckStatement();
            if(StringUtil.isNotBlank(checkStatement)){
                checkMap.put(propertyFeature.getName(), propertyFeature);
            }
        });
        int index = 1;
        Context context = new Context();
        for(Map<String, ? extends WritableValue> property : properties){
            for(Map.Entry<String, ? extends WritableValue> entry : property.entrySet()){
                Object value = entry.getValue();
                if(value != null){
                    value = ((WritableValue)value).getValue();
                }
                context.setVariable(entry.getKey(), value);
            }
            for(Map.Entry<String, ?> entry : property.entrySet()){
                PropertyFeature checkPropertyFeature = checkMap.get(entry.getKey());
                if(checkPropertyFeature != null) {
                    String checkStatement = checkPropertyFeature.getCheckStatement();
                    String el = "${" + checkStatement + "}";
                    boolean check;
                    try {
                        check = Parser.parseBoolean(el, context);
                    } catch (Exception exception) {
                        return "属性 第 [" + index + " ] 行 [ " + checkPropertyFeature.getComment() + " ] EL表达式 [ " + el + " ] 解析错误！";
                    }
                    if (!check) {
                        String checkMessage = StringUtil.isBlank(checkPropertyFeature.getCheckMessage()) ? checkStatement : checkPropertyFeature.getCheckMessage();
                        return "属性 第 [ " + index + " ] 行 [ " + checkPropertyFeature.getComment() + " ] [ " + checkMessage + " ]";
                    }
                }
            }
            index++;
        }
        return null;
    }

    public String validatePropertyFeatures(ObservableList<PropertyFeature> propertyFeatures) {
        if(propertyFeatures.isEmpty()){
            return "未设置属性描述！";
        }
        for(int i = 1 ; i < propertyFeatures.size() + 1 ; i++){
            PropertyFeature propertyFeature = propertyFeatures.get(i - 1);
            String name = propertyFeature.getName();
            if(StringUtil.isBlank(name)){
                return "第 [ " + i + " ] 行 [ 名称 ] 未填写！";
            }
            for(int j = 1 ; j < propertyFeatures.size() + 1 ; j++){
                PropertyFeature pf = propertyFeatures.get(j - 1);
                String pfName = pf.getName();
                if(StringUtil.isBlank(pfName)){
                    return "第 [ " + j + " ] 行 [ 名称 ] 未填写！";
                }
                if(propertyFeature != pf && name.equals(pfName)){
                    return "第 [ " + i + ", " + j + " ] 行 [ 名称 ] 重复！";
                }
            }
            String comment = propertyFeature.getComment();
            if(StringUtil.isBlank(comment)){
                return "第 [ " + i + " ] 行 [ 列标题 ] 未填写！";
            }
            Double viewWidth =  propertyFeature.getViewWidth();
            if(viewWidth == null || viewWidth <= 0){
                return "第 [ " + i + " ] 行 [ 显示宽度 ] 不合法！";
            }
            PropertyType type = propertyFeature.getType();
            if(type == null){
                return "第 [ " + i + " ] 行 [ 类型 ] 未选择！";
            }
            String referenceValues = propertyFeature.getReferenceValues();
            if(type.equals(PropertyType.Enum) && StringUtil.isBlank(referenceValues)){
                return "第 [ " + i + " ] 行 [ 参考值 ] 未填写！";
            }
        }
        return null;
    }

    public boolean existsPath(String path){
        if(path == null){
            return false;
        }
        File pathFile = new File(path);
        return pathFile.exists();
    }

}
