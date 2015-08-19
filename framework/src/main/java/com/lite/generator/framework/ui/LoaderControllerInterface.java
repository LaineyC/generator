package com.lite.generator.framework.ui;

import com.lite.generator.framework.aop.Log;

public interface LoaderControllerInterface {

    @Log(content = "加载器-初始化")
    public void init();

}
