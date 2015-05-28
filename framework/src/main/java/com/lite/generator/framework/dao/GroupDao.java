package com.lite.generator.framework.dao;

import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.model.Module;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.util.FileUtil;
import com.lite.generator.framework.util.ReflectionUtil;
import com.lite.generator.framework.util.SingletonUtil;
import com.lite.generator.framework.util.XmlUtil;
import javafx.scene.control.TreeItem;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.List;

public abstract class GroupDao<T extends Group> {

    private Class<? extends Group> tClass = ReflectionUtil.getSuperClassGenericsType(this.getClass(), 0);

    private String getParentPath(Group parentGroup){
        if(parentGroup == null) {
            return SystemVariable.projectConfigPath;
        }
        StringBuffer storePath = new StringBuffer();
        do{
            if(parentGroup.getParent() == null){
                break;
            }
            storePath.insert(0, parentGroup.getId());
            storePath.insert(0, SystemVariable.fileSeparator);
            storePath.insert(0, SystemVariable.groupFolder);
            storePath.insert(0, SystemVariable.fileSeparator);
            parentGroup = (Group)parentGroup.getParent();
        }
        while(true);
        storePath.insert(0, SystemVariable.projectConfigPath);
        storePath.append(SystemVariable.fileSeparator);
        storePath.append(SystemVariable.groupFolder);
        return storePath.toString();
    }

    public void save(T group){
        save(group ,true);
    }

    protected abstract String getStorePath(String id);

    protected abstract String getStoreFile(String id);

    protected abstract void saveChild(Group child);

    protected abstract void addChild(String childId, String className, Group parent);

    protected void saveHandle(Element root, T group){

    }

    protected void getHandle(Element root, T group){

    }

    public void save(T group, boolean deep){
        String parentPath = getParentPath((Group)group.getParent());
        String storePath = getStorePath(group.getId());
        storePath = storePath == null ? parentPath : parentPath + SystemVariable.fileSeparator + storePath;
        String groupName = group.getClass().getSimpleName().toLowerCase();
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(groupName);

        Element idElement = root.addElement("id");
        String id = group.getId();
        if(id != null){
            idElement.setText(id);
        }

        Element nameElement = root.addElement("name");
        String name = group.getName();
        if(name != null){
            nameElement.setText(name);
        }

        Element commentElement = root.addElement("comment");
        String comment = group.getComment();
        if(comment != null) {
            commentElement.setText(comment);
        }

        Element descriptionElement = root.addElement("description");
        String description = group.getDescription();
        if(description != null) {
            descriptionElement.setText(description);
        }


        Element expandedElement = root.addElement("expanded");
        boolean isExpanded =  group.isExpanded();
        expandedElement.setText(Boolean.toString(isExpanded));

        Element modelsElement = root.addElement("groups");
        group.getChildren().forEach(child -> {
            String groupChildName = child.getClass().getSimpleName().toLowerCase();
            Element modelElement = modelsElement.addElement(groupChildName);
            Element modelIdElement = modelElement.addElement("id");
            Group groupChild = (Group)child;
            modelIdElement.setText(groupChild.getId());
            if (deep) {
                saveChild(groupChild);
            }
        });

        saveHandle(root, group);

        FileUtil.mkdirs(storePath);
        String storeFile = storePath + SystemVariable.fileSeparator + getStoreFile(group.getId());
        XmlUtil.write(document, storeFile);
    }

    public T get(String id, Group groupParent){
        String parentPath = getParentPath(groupParent);
        String storePath = getStorePath(id);
        storePath = storePath == null ? parentPath : parentPath + SystemVariable.fileSeparator + storePath;
        String storeFile = storePath + SystemVariable.fileSeparator + getStoreFile(id);
        T g;
        Document document;
        try{
            document = XmlUtil.read(storeFile);
            g = (T)tClass.getDeclaredConstructor().newInstance();
        }
        catch (Exception exception){
            return null;
        }
        T group = g;

        String groupName = tClass.getSimpleName().toLowerCase();
        Element root = document.getRootElement();

        Node idNode = root.selectSingleNode("/" + groupName + "/id");
        group.setId(idNode.getText());

        Node nameNode = root.selectSingleNode("/" + groupName + "/name");
        group.setName(nameNode.getText());

        Node commentNode = root.selectSingleNode("/" + groupName + "/comment");
        group.setComment(commentNode.getText());

        Node descriptionNode = root.selectSingleNode("/" + groupName + "/description");
        group.setDescription(descriptionNode.getText());

        Node expandedNode = root.selectSingleNode("/" + groupName + "/expanded");
        group.setExpanded(Boolean.valueOf(expandedNode.getText()));

        if(groupParent != null) {
            groupParent.getChildren().add(group);
        }

        List<Node> groupsNode = root.selectNodes("/" + groupName + "/groups/child::node()");
        groupsNode.forEach(groupNode  -> {
            Node childIdNode = groupNode.selectSingleNode("child::id");
            if(childIdNode != null) {
                String childId = groupNode.selectSingleNode("child::id").getText();
                addChild(childId, groupNode.getName(), group);
            }
        });

        getHandle(root, group);

        return group;
    }

}
