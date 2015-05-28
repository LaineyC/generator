package com.lite.generator.framework.dao;

import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.model.PropertyType;
import com.lite.generator.framework.tool.PropertyFeatureTool;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.util.FileUtil;
import com.lite.generator.framework.util.StringUtil;
import com.lite.generator.framework.util.XmlUtil;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelDao extends GroupDao<Model>{

    public ModelDao(){

    }

    @Override
    protected String getStorePath(String id) {
        return null;
    }

    @Override
    protected String getStoreFile(String id) {
        return id + ".xml";
    }

    @Override
    protected void saveChild(Group child) {

    }

    @Override
    protected void addChild(String id, String className, Group parent) {

    }

    protected void saveHandle(Element root, Model model){
        Element properties = root.addElement("properties");
        model.getProperties().forEach(property -> {
            Element propertyElement = properties.addElement("property");
            property.forEach((key, value) -> {
                Element propertyNameElement = propertyElement.addElement(key);
                String stringValue = PropertyFeatureTool.toString(key, value);
                if(stringValue != null){
                    propertyNameElement.setText(stringValue);
                }
            });
        });
    }

    Map<WritableValue, String> groupMap = new HashMap<>();

    protected void getHandle(Element root, Model model){
        List<Node> propertiesNode = root.selectNodes("/model/properties/property");
        propertiesNode.forEach(propertyNode -> {
            Map<String, WritableValue<?>> property = FXCollections.observableHashMap();
            PropertyFeatureTool.propertyFeatureMap.forEach((key, propertyFeature) -> {
                Node valueNode = propertyNode.selectSingleNode("child::" + key);
                WritableValue<?> value = PropertyFeatureTool.fromString(key, valueNode == null ? null : valueNode.getText());;
                property.put(key, value);
                if(PropertyType.Group.equals(propertyFeature.getType())){
                    if(valueNode != null){
                        groupMap.put(value, valueNode.getText());
                    }
                }
            });
            model.getProperties().add(property);
        });
    }

}
