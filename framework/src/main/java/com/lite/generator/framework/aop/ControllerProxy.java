package com.lite.generator.framework.aop;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.tool.ControlTool;
import com.lite.generator.framework.util.LogUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ControllerProxy<Target> implements InvocationHandler {

    private Target target;

    public <Interface> Interface proxy(Target target) {
        this.target = target;
        return (Interface)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    //同步
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;

        Log log = method.getAnnotation(Log.class);
        String message = "[" + log.content() + "]";

        if(log.argumentFormatter() != DefaultArgumentFormatter.class){
            String argumentMessage = log.argumentFormatter().newInstance().format(args);
            message = argumentMessage == null ? message : message + "[" + argumentMessage + "]";
        }
        long start = System.currentTimeMillis();
        try {
            result = method.invoke(target, args);
            long interval = System.currentTimeMillis() - start;
            if(log.returnValueFormatter() != DefaultReturnValueFormatter.class){
                String returnMessage = log.returnValueFormatter().newInstance().format(result);
                message = returnMessage == null ? message : message + "[" + returnMessage + "]";
            }
            message = message + "[用时：" + interval + "ms]";
            LogUtil.logger.info(message);
        }
        catch (Exception exception){
            long interval = System.currentTimeMillis() - start;
            message = message + "[用时：" + interval + "ms]";
            LogUtil.logger.error(message, exception);
            ControlTool.messageTooltip("[ " + log.content() + " ] 执行出现异常，打开控制台查看堆栈！", Application.getWindow());
            throw exception;
        }
        finally {

        }

        return result;
    }

}