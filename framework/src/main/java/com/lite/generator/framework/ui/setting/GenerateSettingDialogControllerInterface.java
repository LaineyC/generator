package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.aop.ArgumentFormatter;
import com.lite.generator.framework.aop.Log;
import com.lite.generator.framework.model.Model;

import java.io.File;

public interface GenerateSettingDialogControllerInterface {

    public static class GeneratePathBrowseArgumentFormatter implements ArgumentFormatter {
        @Override
        public String format(Object[] args) {
            File file = (File)args[0];
            return file == null ? null : ("选择路径：" + file.getAbsolutePath());
        }
    }
    @Log(content = "浏览代码生成路径",argumentFormatter = GeneratePathBrowseArgumentFormatter.class)
    public void generatePathBrowse(File file);

    @Log(content = "生成设置对话框-初始化")
    public void init();

    @Log(content = "生成设置对话框-显示")
    public void show();

    @Log(content = "生成设置对话框-确定")
    public void confirm();

}
