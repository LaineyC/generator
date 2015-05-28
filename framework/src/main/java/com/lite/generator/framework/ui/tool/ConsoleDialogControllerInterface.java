package com.lite.generator.framework.ui.tool;

import com.lite.generator.framework.aop.Log;

public interface ConsoleDialogControllerInterface {

    @Log(content = "控制台对话框-初始化")
    public void init();

    @Log(content = "控制台对话框-显示")
    public void show();

    @Log(content = "控制台对话框-确定")
    public void confirm();

    @Log(content = "控制台对话框-查看日志")
    public void viewLog();

}
