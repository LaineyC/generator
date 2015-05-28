package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.aop.Log;

public interface QuickSettingDialogControllerInterface {

    @Log(content = "快捷设置对话框-初始化")
    public void init();

    @Log(content = "快捷设置对话框-显示")
    public void show();

    @Log(content = "快捷设置对话框-确定")
    public void confirm();

    @Log(content = "快捷设置对话框-添加单个快捷属性")
    public void addProperty();

    @Log(content = "快捷设置对话框-添加多个快捷属性")
    public void addProperties();

    @Log(content = "快捷设置对话框-删除选中快捷属性")
    public void deleteProperties();

}
