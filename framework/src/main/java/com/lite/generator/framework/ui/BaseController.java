package com.lite.generator.framework.ui;

import com.lite.generator.framework.aop.ControllerProxy;

public abstract class BaseController<ControllerParent, ControllerInterface>{

    private ControllerInterface proxy;

    private ControllerParent parent;

    public BaseController(){
        this.proxy = new ControllerProxy<BaseController<ControllerParent, ControllerInterface>>().proxy(this);
    }

    public ControllerInterface getProxy() {
        return proxy;
    }

    public ControllerParent getParent() {
        return parent;
    }

    public void setParent(ControllerParent parent) {
        this.parent = parent;
    }

}
