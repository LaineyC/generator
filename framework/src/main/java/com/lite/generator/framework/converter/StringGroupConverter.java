package com.lite.generator.framework.converter;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.model.Project;
import com.lite.generator.framework.util.StringUtil;
import javafx.scene.control.TreeItem;
import javafx.util.StringConverter;

public class StringGroupConverter extends StringConverter<Group> {

    public StringGroupConverter(){

    }

    @Override
    public String toString(Group object) {
        if(object == null){
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        do{
            stringBuffer.insert(0, object.getId());
            if(object.getParent() != null){
                stringBuffer.insert(0, ":");
            }
        }
        while((object = (Group)object.getParent()) != null);
        return stringBuffer.toString();
    }

    @Override
    public Group
    fromString(String string) {
        return fromString(string, Application.getController().getProject());
    }

    public Group fromString(String string, Project project) {
        if(StringUtil.isBlank(string) || project == null){
            return null;
        }
        String[] ids = string.split(":");
        if(!project.getId().equals(ids[0])){
            return null;
        }
        if(ids.length == 1){
            return project;
        }
        Group group = project;
        for(int i = 1; i < ids.length ; i++){
            for(TreeItem<Group> child : group.getChildren()){
                if(((Group)child).getId().equals(ids[i])){
                    group = (Group)child;
                    break;
                }
            }
        }
        return group == project ? null : group;
    }

}
