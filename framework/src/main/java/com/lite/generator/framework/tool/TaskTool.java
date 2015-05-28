package com.lite.generator.framework.tool;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.util.LogUtil;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;

public class TaskTool {

    public static <R> void newTask(String message, Callback<Object, R> task){
        newTask(message, task, null, null, Application.getWindow());
    }

    public static <R> void newTask(String message, Callback<Object, R> task, Window window){
        newTask(message, task, null, null, window);
    }

    public static <R> void newTask(String message, Callback<Object, R> task, Callback<R, Object> success){
        newTask(message, task, success, null, Application.getWindow());
    }

    public static <R> void newTask(String message, Callback<Object, R> task, Callback<R, Object> success, Window window){
        newTask(message, task, success, null, window);
    }

    public static <R> void newTask(String message, Callback<Object, R> task, Callback<R, Object> success, Callback<Throwable, Object> fail){
        newTask(message, task, success, fail, Application.getWindow());
    }

    public static <R> void newTask(String message, Callback<Object, R> task, Callback<R, Object> success, Callback<Throwable, Object> fail, Window window){
        ControlTool.progressStart(message, window);

        long start = System.currentTimeMillis();
        Task<R> newTask = new Task<R>() {
            @Override public R call() {
                return task.call(null);
            }
        };

        newTask.stateProperty().addListener((observable, oldState, newState) -> {
            if (newState.equals(Worker.State.SUCCEEDED)) {
                PauseTransition ptr = new PauseTransition(Duration.seconds(0.25));
                ptr.setOnFinished((ActionEvent event) -> {
                    if(success != null){
                        success.call(newTask.getValue());
                    }
                    long interval = System.currentTimeMillis() - start;
                    LogUtil.logger.info("[" + message + "][用时：" + interval + "ms]");
                    ControlTool.progressStop(window);
                });
                ptr.play();
            }
            if(newState.equals(Worker.State.FAILED)){
                if(fail != null){
                    fail.call(newTask.getException());
                }
                long interval = System.currentTimeMillis() - start;
                LogUtil.logger.error("[" + message + "][用时：" + interval + "ms]", newTask.getException());
                ControlTool.progressForceStop(window);
                ControlTool.messageTooltip("[ " + message + " ] 执行出现异常，打开控制台查看堆栈！", Application.getWindow());
            }
        });

        new Thread(newTask).start();
    }

}
