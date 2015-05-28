package com.lite.generator.framework.service;

import com.lite.generator.framework.dao.ConfigDao;
import com.lite.generator.framework.dao.ProjectDao;
import com.lite.generator.framework.dao.TemplateConfigDao;
import com.lite.generator.framework.model.Config;
import com.lite.generator.framework.model.Project;
import com.lite.generator.framework.template.TemplateConfig;
import com.lite.generator.framework.util.KeyGenerator;
import com.lite.generator.framework.util.SingletonUtil;
import javafx.collections.ObservableList;

public class StoreService {

    private ConfigDao configDao = SingletonUtil.get(ConfigDao.class);

    private ProjectDao projectDao = SingletonUtil.get(ProjectDao.class);

    private TemplateConfigDao templateConfigDao = SingletonUtil.get(TemplateConfigDao.class);

    public Config getConfig(){
        Config config = configDao.get();
        if(config == null){
            config = new Config();
            config.setWindowWidth(1200);
            config.setWindowHeight(900);
            config.setWindowMaximized(false);
            config.setDividerPosition(0.16666667);
        }
        return config;
    }

    public void saveConfig(Config config){
        configDao.save(config);
    }

    public Project getProject(){
        Project project = projectDao.get(null, null);
        if(project == null){
            String id = KeyGenerator.timeHex();
            project = new Project();
            project.setId(id);
            project.setName("新建项目_" + id);
        }
        return project;
    }

    public void saveProject(Project project){
        projectDao.save(project);
    }

    public ObservableList<TemplateConfig> getTemplateConfigs(){
        return templateConfigDao.getAll();
    }

}
