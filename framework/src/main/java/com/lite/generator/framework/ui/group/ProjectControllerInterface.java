package com.lite.generator.framework.ui.group;

import com.lite.generator.framework.aop.ArgumentFormatter;
import com.lite.generator.framework.aop.Log;
import com.lite.generator.framework.model.Project;

public interface ProjectControllerInterface {

    public static class InitArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Project project = (Project)args[0];
            return "项目名称：" + project.getName();
        }
    }
    @Log(content = "项目面板-初始化", argumentFormatter = InitArgumentFormatter.class)
    public void init(Project project);

}
