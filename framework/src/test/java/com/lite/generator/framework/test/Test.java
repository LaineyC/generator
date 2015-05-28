package com.lite.generator.framework.test;

import com.lite.generator.framework.dao.ProjectDao;
import com.lite.generator.framework.model.Group;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.model.Module;
import com.lite.generator.framework.model.Project;
import com.lite.generator.framework.operation.History;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.util.KeyGenerator;
import com.lite.generator.framework.util.SingletonUtil;
import com.lite.generator.framework.util.StringUtil;
import javafx.scene.control.TreeItem;

public class Test {

    @org.junit.Test
    public void test1(){
        Project project = createProject();

        getParentPath((Group) project.getChildren().get(0));

        project = SingletonUtil.get(ProjectDao.class).get(null, null);
        System.out.println("da");
    }

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
        System.out.println(storePath.toString());
        return storePath.toString();
    }


    private Project createProject(){
        Project project = new Project();
        project.setId("project");
        project.setName("项目");
        for(int i = 1 ; i < 5 ; i++){
            Module module = new Module();
            module.setId("module" + i);
            module.setName("模块_" + i);
            project.getChildren().add(module);
            for(int j = 1 ; j < 5 ; j++){
                Model model = new Model();
                model.setId("model" + j);
                model.setName("模型_" + j);
                module.getChildren().add(model);
            }
        }
        Model model = new Model();
        model.setId(KeyGenerator.timeHex());
        model.setName("模型_");
        project.getChildren().add(model);
        return project;
    }

    private void function(Group group, String path){
        for(int i= 0 ;i < group.getChildren().size() ; i++){
            Group child = (Group)group.getChildren().get(i);
            System.out.println(path);
            function(child,path + "/" + child.getName());
        }
    }

    @org.junit.Test
    public void test2(){
      System.out.println(History.back("11"));
        System.out.println(History.back("11"));
        System.out.println(History.back("11"));
    }

    public Group fromString(String string, Project project) {
        if(StringUtil.isBlank(string)){
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


    public Group fromString(String string) {
        /*if(string != null){
            ObservableList<Class> classes = Main.getGenerateConfig().getModule().getClasses();
            for(Class clazz : classes){
                if(string.equals(clazz.getName())){
                    return clazz;
                }
            }
        }*/
        return null;
    }

}
