package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.aop.Log;

public interface StartUpSettingDialogControllerInterface {

    @Log(content = "启动设置对话框-初始化")
    public void init();

    @Log(content = "启动设置对话框-显示")
    public void show();

    @Log(content = "启动设置对话框-确定")
    public void confirm();

    @Log(content = "启动设置对话框-添加单个属性描述")
    public void addProperty();

    @Log(content = "启动设置对话框-添加多个属性描述")
    public void addProperties();

    @Log(content = "启动设置对话框-删除选中属性描述")
    public void deleteProperties();

}
