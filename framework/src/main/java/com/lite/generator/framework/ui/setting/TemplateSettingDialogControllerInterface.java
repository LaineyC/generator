package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.aop.Log;

public interface TemplateSettingDialogControllerInterface {

    @Log(content = "模板设置对话框-初始化")
    public void init();

    @Log(content = "模板设置对话框-显示")
    public void show();

    @Log(content = "模板设置对话框-确定")
    public void confirm();

    @Log(content = "模板设置对话框-刷新")
    public void refresh();

    @Log(content = "模板设置对话框-浏览")
    public void browse();

}
