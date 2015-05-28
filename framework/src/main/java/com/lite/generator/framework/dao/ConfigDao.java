package com.lite.generator.framework.dao;

import com.lite.generator.framework.model.Config;
import com.lite.generator.framework.model.Project;
import com.lite.generator.framework.model.PropertyFeature;
import com.lite.generator.framework.model.PropertyType;
import com.lite.generator.framework.template.TemplateConfig;
import com.lite.generator.framework.tool.PropertyFeatureTool;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.util.FileUtil;
import com.lite.generator.framework.util.SingletonUtil;
import com.lite.generator.framework.util.XmlUtil;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.List;
import java.util.Map;

public class ConfigDao {

    private TemplateConfigDao templateConfigDao = SingletonUtil.get(TemplateConfigDao.class);

    public void save(Config config){
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("config");

        Element generatePathElement = root.addElement("generate-path");
        String generatePath = config.getGeneratePath();
        if(generatePath != null){
            generatePathElement.setText(generatePath);
        }

        Element propertyFeaturesElement = root.addElement("property-features");

        config.getPropertyFeatures().forEach(propertyFeature -> {
            Element propertyFeatureElement = propertyFeaturesElement.addElement("property-feature");

            Element nameElement = propertyFeatureElement.addElement("name");
            String name = propertyFeature.getName();
            if (name != null) {
                nameElement.setText(name);
            }

            Element commentElement = propertyFeatureElement.addElement("comment");
            String comment = propertyFeature.getComment();
            if (comment != null) {
                commentElement.setText(comment);
            }

            Element propertyTypeElement = propertyFeatureElement.addElement("type");
            PropertyType propertyType = propertyFeature.getType();
            if (propertyType != null) {
                propertyTypeElement.setText(propertyType.toString());
            }

            Element defaultValueElement = propertyFeatureElement.addElement("default-value");
            String defaultValue = propertyFeature.getDefaultValue();
            if (defaultValue != null) {
                defaultValueElement.setText(defaultValue);
            }

            Element viewWidthElement = propertyFeatureElement.addElement("view-width");
            Double viewWidth = propertyFeature.getViewWidth();
            if (viewWidth != null) {
                viewWidthElement.setText(viewWidth.toString());
            }

            Element checkStatementElement = propertyFeatureElement.addElement("check-statement");
            String checkStatement = propertyFeature.getCheckStatement();
            if (checkStatement != null) {
                checkStatementElement.setText(checkStatement);
            }

            Element checkMessageElement = propertyFeatureElement.addElement("check-message");
            String checkMessage = propertyFeature.getCheckMessage();
            if (checkMessage != null) {
                checkMessageElement.setText(checkMessage);
            }

            Element referenceValuesElement = propertyFeatureElement.addElement("reference-values");
            String referenceValues = propertyFeature.getReferenceValues();
            if (referenceValues != null) {
                referenceValuesElement.setText(referenceValues);
            }
        });

        Element templateConfigElement = root.addElement("template-config");
        TemplateConfig templateConfig = config.getTemplateConfig();
        if(templateConfig != null && templateConfig.getName() != null){
            Element templateConfigNameElement = templateConfigElement.addElement("name");
            templateConfigNameElement.setText(templateConfig.getName());
        }

        Element quickProperties = root.addElement("quick-properties");

        config.getQuickProperties().forEach(property -> {
            Element quickProperty = quickProperties.addElement("quick-property");
            property.forEach((key, value) -> {
                Element propertyNameElement = quickProperty.addElement(key);
                String stringValue = PropertyFeatureTool.toString(key, value);
                if(stringValue != null){
                    propertyNameElement.setText(stringValue);
                }
            });
        });

        Element windowWidth = root.addElement("window-width");
        windowWidth.setText(Double.toString(config.getWindowWidth()));

        Element windowHeight = root.addElement("window-height");
        windowHeight.setText(Double.toString(config.getWindowHeight()));

        Element windowMaximized = root.addElement("window-maximized");
        windowMaximized.setText(Boolean.toString(config.getWindowMaximized()));

        Element dividerPosition = root.addElement("divider-position");
        dividerPosition.setText(Double.toString(config.getDividerPosition()));

        FileUtil.mkdirs(SystemVariable.configPath);
        XmlUtil.write(document, SystemVariable.configPath + SystemVariable.fileSeparator + "config.xml");
    }

    public Config get(){
        Document document;
        try{
            document = XmlUtil.read(SystemVariable.configPath + SystemVariable.fileSeparator + "config.xml");
        }
        catch (Exception exception){
            return null;
        }
        Element root = document.getRootElement();
        Config config = new Config();

        Node generatePathNode = root.selectSingleNode("/config/generate-path");
        config.setGeneratePath(generatePathNode.getText());

        List<Node> propertyFeatures = root.selectNodes("/config/property-features/property-feature");
        propertyFeatures.forEach(pf ->{
            PropertyFeature propertyFeature = new PropertyFeature();

            Node nameNode = pf.selectSingleNode("child::name");
            propertyFeature.setName(nameNode.getText());

            Node commentNode = pf.selectSingleNode("child::comment");
            propertyFeature.setComment(commentNode.getText());

            Node typeNode = pf.selectSingleNode("child::type");
            propertyFeature.setType(PropertyType.valueOf(typeNode.getText()));

            PropertyFeatureTool.setConverter(propertyFeature);

            Node editTypeNode = pf.selectSingleNode("child::default-value");
            propertyFeature.setDefaultValue(editTypeNode.getText());

            Node viewWidthNode = pf.selectSingleNode("child::view-width");
            propertyFeature.setViewWidth(new Double(viewWidthNode.getText()));

            Node checkStatementNode = pf.selectSingleNode("child::check-statement");
            propertyFeature.setCheckStatement(checkStatementNode.getText());

            Node checkMessageNode = pf.selectSingleNode("child::check-message");
            propertyFeature.setCheckMessage(checkMessageNode.getText());

            Node referenceValuesNode = pf.selectSingleNode("child::reference-values");
            propertyFeature.setReferenceValues(referenceValuesNode.getText());

            config.getPropertyFeatures().add(propertyFeature);

            PropertyFeatureTool.propertyFeatureMap.put(propertyFeature.getName(), propertyFeature);
        });

        Node templateConfigName = root.selectSingleNode("/config/template-config/name");
        if(templateConfigName != null) {
            TemplateConfig templateConfigTag = templateConfigDao.get(templateConfigName.getText());
            config.setTemplateConfig(templateConfigTag);
        }

        List<Node> quickProperties = root.selectNodes("/config/quick-properties/quick-property");

        quickProperties.forEach(property -> {
            Map<String,  WritableValue<?>> quickProperty = FXCollections.observableHashMap();
            PropertyFeatureTool.propertyFeatureMap.forEach((key, value) -> {
                Node valueNode = property.selectSingleNode("child::" + key);
                quickProperty.put(key, PropertyFeatureTool.fromString(key, valueNode == null ? null : valueNode.getText()));
            });
            config.getQuickProperties().add(quickProperty);
        });

        Node windowWidthNode = root.selectSingleNode("/config/window-width");
        config.setWindowWidth(new Double(windowWidthNode.getText()));

        Node windowHeightNode = root.selectSingleNode("/config/window-height");
        config.setWindowHeight(new Double(windowHeightNode.getText()));

        Node windowMaximizedNode = root.selectSingleNode("/config/window-maximized");
        config.setWindowMaximized(new Boolean(windowMaximizedNode.getText()));

        Node dividerPositionNode = root.selectSingleNode("/config/divider-position");
        config.setDividerPosition(new Double(dividerPositionNode.getText()));

        return config;
    }

}
