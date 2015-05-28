package com.lite.generator.framework.dao;

import com.lite.generator.framework.converter.StringConverters;
import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.model.Module;
import com.lite.generator.framework.model.Project;
import com.lite.generator.framework.util.SingletonUtil;
import org.dom4j.Element;

public class ProjectDao extends GroupDao<Project>{

    private ModuleDao moduleDao = SingletonUtil.get(ModuleDao.class);

    private ModelDao modelDao = SingletonUtil.get(ModelDao.class);

    public ProjectDao(){

    }

    @Override
    protected String getStorePath(String id) {
        return null;
    }

    @Override
    protected String getStoreFile(String id) {
        return "project.xml";
    }

    @Override
    protected void saveChild(Group child) {
        if(child instanceof Model){
            modelDao.save((Model)child);
        }
        else{
            moduleDao.save((Module)child);
        }
    }

    @Override
    protected void addChild(String id, String className, Group parent) {
        if("model".equals(className)){
            modelDao.get(id, parent);
        }
        else{
            moduleDao.get(id, parent);
        }
    }

    @Override
    protected void getHandle(Element root, Project project){
        modelDao.groupMap.forEach((writableValue, stringValue) ->
            writableValue.setValue(StringConverters.Group.fromString(stringValue, project))
        );
    }

}
