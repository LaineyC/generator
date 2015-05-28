package com.lite.generator.framework.ui.group;

import com.lite.generator.framework.aop.ArgumentFormatter;
import com.lite.generator.framework.aop.Log;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.model.Module;
import com.lite.generator.framework.operation.Operation;

import java.util.List;

public interface ModelControllerInterface {

    public static class InitArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Model model = (Model)args[0];
            return "模型名称：" + model.getName();
        }
    }
    @Log(content = "模型面板-初始化", argumentFormatter = InitArgumentFormatter.class)
    public void init(Model model);

    public static class DeletePropertiesArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Model model = (Model)args[0];
            return "所属模型：" + model.getName();
        }
    }
    @Log(content = "删除属性", argumentFormatter = DeletePropertiesArgumentFormatter.class)
    public void deleteProperties(Model model);

    public static class AddPropertyArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Model model = (Model)args[0];
            return "所属模型：" + model.getName();
        }
    }
    @Log(content = "添加单个属性", argumentFormatter = AddPropertyArgumentFormatter.class)
    public void addProperty(Model model);

    public static class AddPropertiesArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Model model = (Model)args[0];
            return "所属模型：" + model.getName();
        }
    }
    @Log(content = "添加多个属性", argumentFormatter = AddPropertiesArgumentFormatter.class)
    public void addProperties(Model model);

    public static class AddQuickPropertiesArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Model model = (Model)args[0];
            return "所属模型：" + model.getName();
        }
    }
    @Log(content = "添加快捷属性", argumentFormatter = AddQuickPropertiesArgumentFormatter.class)
    public void addQuickProperties(Model model);

    public static class UndoArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            List<Operation<?>> operations = (List<Operation<?>>)args[0];
            if(operations == null){
                return null;
            }
            Model model = (Model)args[1];
            return "操作类型：" + operations.get(0).getType().toString() +
                    "，操作对象：模型-> " + model.getName();
        }
    }
    @Log(content = "撤销", argumentFormatter = UndoArgumentFormatter.class)
    public void undo(List<Operation<?>> operations, Model model);

    public static class RedoArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            List<Operation<?>> operations = (List<Operation<?>>)args[0];
            if(operations == null){
                return null;
            }
            Model model = (Model)args[1];
            return "操作类型：" + operations.get(0).getType().toString() +
                    "，操作对象：模型-> " + model.getName();
        }
    }
    @Log(content = "重做", argumentFormatter = RedoArgumentFormatter.class)
    public void redo(List<Operation<?>> operations, Model model);

}
