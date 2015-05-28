package com.lite.generator.framework.dao;

import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.model.Module;
import com.lite.generator.framework.model.Project;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.util.FileUtil;
import com.lite.generator.framework.util.SingletonUtil;
import com.lite.generator.framework.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.List;

public class ModuleDao extends GroupDao<Module>{

    private ModelDao modelDao = SingletonUtil.get(ModelDao.class);

    public ModuleDao(){

    }

    @Override
    protected String getStorePath(String id) {
        return id;
    }

    @Override
    protected String getStoreFile(String id) {
        return "module.xml";
    }

    @Override
    protected void saveChild(Group child) {
        if(child instanceof Model){
            modelDao.save((Model)child);
        }
        else{
            save((Module)child);
        }
    }

    @Override
    protected void addChild(String id, String className, Group parent) {
        if("model".equals(className)){
            modelDao.get(id, parent);
        }
        else{
            get(id, parent);
        }
    }

}
