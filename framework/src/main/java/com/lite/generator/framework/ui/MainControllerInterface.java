package com.lite.generator.framework.ui;

import com.lite.generator.framework.aop.ArgumentFormatter;
import com.lite.generator.framework.aop.Log;
import com.lite.generator.framework.model.*;

public interface MainControllerInterface {

    public static class InitArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Config config = (Config)args[0];
            return "宽："  + config.getWindowWidth() +
                    "，高：" + config.getWindowHeight() +
                    "，分割位置：" + config.getDividerPosition();
        }
    }
    @Log(content = "程序界面-初始化", argumentFormatter = InitArgumentFormatter.class)
    public void init(Config config, Project project);

    @Log(content = "程序退出")
    public void exit();

    @Log(content = "保存配置")
    public void saveConfig();

    @Log(content = "加载配置")
    public Object[] loadConfig();

    @Log(content = "生成代码")
    public void generateCode();

    @Log(content = "浏览代码")
    public void browseCode();

    @Log(content = "清空代码")
    public void clearCode();

    public static class EditProjectArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Project project = (Project)args[0];
            return "项目名称：" + project.getName();
        }
    }
    @Log(content = "编辑项目", argumentFormatter = EditProjectArgumentFormatter.class)
    public void editProject(Project project);

    public static class NewModuleArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Group group = (Group)args[0];
            return "父分组：" + group.getName();
        }
    }
    @Log(content = "新建模块", argumentFormatter = NewModuleArgumentFormatter.class)
    public Module newModule(Group group);

    public static class EditModuleArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Module module = (Module)args[0];
            return "模块名称：" + module.getName();
        }
    }
    @Log(content = "编辑模块", argumentFormatter = EditModuleArgumentFormatter.class)
    public void editModule(Module module);

    public static class DeleteModuleArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Module module = (Module)args[0];
            return "模块名称：" + module.getName();
        }
    }
    @Log(content = "删除模块", argumentFormatter = DeleteModuleArgumentFormatter.class)
    public void deleteModule(Module module);

    public static class NewModelArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Group group = (Group)args[0];
            return "父分组：" + group.getName();
        }
    }
    @Log(content = "新建模型", argumentFormatter = NewModelArgumentFormatter.class)
    public Model newModel(Group group);

    public static class EditModelArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Model model = (Model)args[0];
            return "模型名称：" + model.getName();
        }
    }
    @Log(content = "编辑模型", argumentFormatter = EditModelArgumentFormatter.class)
    public void editModel(Model model);

    public static class DeleteModelArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Model model = (Model)args[0];
            return "模型名称：" + model.getName();
        }
    }
    @Log(content = "删除模块", argumentFormatter = DeleteModelArgumentFormatter.class)
    public void deleteModel(Model model);

}
