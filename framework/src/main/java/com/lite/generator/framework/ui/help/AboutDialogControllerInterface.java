package com.lite.generator.framework.ui.help;

import com.lite.generator.framework.aop.Log;

public interface AboutDialogControllerInterface {

    @Log(content = "关于对话框-初始化")
    public void init();

    @Log(content = "关于对话框-显示")
    public void show();

    @Log(content = "关于对话框-确定")
    public void confirm();

}
