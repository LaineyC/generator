package com.lite.generator.framework.ui.help;

import com.lite.generator.framework.aop.Log;

public interface TutorialDialogControllerInterface {

    @Log(content = "教程对话框-初始化")
    public void init();

    @Log(content = "教程对话框-显示")
    public void show();

    @Log(content = "教程对话框-确定")
    public void confirm();

}
