package com.lite.generator.framework.ui.group;

import com.lite.generator.framework.aop.ArgumentFormatter;
import com.lite.generator.framework.aop.Log;
import com.lite.generator.framework.model.Module;

public interface ModuleControllerInterface {

    public static class InitArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            Module module = (Module)args[0];
            return "模块名称：" + module.getName();
        }
    }
    @Log(content = "模块面板-初始化", argumentFormatter = InitArgumentFormatter.class)
    public void init(Module module);

}
