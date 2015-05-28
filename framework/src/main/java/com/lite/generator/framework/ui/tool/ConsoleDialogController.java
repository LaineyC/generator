package com.lite.generator.framework.ui.tool;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.service.ValidateService;
import com.lite.generator.framework.tool.ControlTool;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.tool.TaskTool;
import com.lite.generator.framework.ui.BaseDialogController;
import com.lite.generator.framework.ui.MainController;
import com.lite.generator.framework.util.LoopedStreams;
import com.lite.generator.framework.util.SingletonUtil;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleDialogController extends BaseDialogController<MainController, ConsoleDialogControllerInterface> implements ConsoleDialogControllerInterface {

    @FXML
    private TextArea consoleTextArea;

    private ValidateService validateService = SingletonUtil.get(ValidateService.class);

    @Override
    public void init(){
        Button log = new Button("查看日志");
        log.setContentDisplay(ContentDisplay.RIGHT);
        log.setGraphic(new ImageView(Icons.browse));
        log.setOnAction(e -> {
            if(!validateService.existsPath(SystemVariable.logPath)){
                ControlTool.messageTooltip("日志文件夹不存在！", getDialog());
                return;
            }
            getProxy().viewLog();
        });
        log.getStyleClass().add("btn-info");

        Button confirm = new Button("确定");
        confirm.getStyleClass().add("btn-primary");
        confirm.setOnAction((evt) -> getProxy().confirm());
        getDialog().getToolBar().getItems().addAll(confirm, log);

        LoopedStreams loopedStreams = SingletonUtil.get(LoopedStreams.class);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(loopedStreams.getInputStream()));
        Task<Void> task = new Task<Void>() {
            @Override public Void call() {
                StringBuffer stringBuffer = new StringBuffer();
                try {
                    String message;
                    while((message = bufferedReader.readLine()) != null) {
                        this.updateMessage(new String(
                            stringBuffer.append(message).append("\n").toString().getBytes(),
                            "UTF-8")
                        );
                    }
                }
                catch(IOException e) {
                    System.exit(1);
                }
                return null;
            }
        };
        task.messageProperty().addListener((observable, oldValue, newValue) -> {
            consoleTextArea.setText(newValue);
            consoleTextArea.setScrollTop(Double.MAX_VALUE);
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        consoleTextArea.setEditable(false);
    }

    @Override
    public void show(){
        getDialog().show();
    }

    @Override
    public void confirm(){
        getDialog().hide();
    }

    @Override
    public void viewLog() {
        TaskTool.newTask(
            "正在打开......",
            (p) -> {
                try {
                    Desktop.getDesktop().open(new File(SystemVariable.logPath));
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
                return null;
            },
            getDialog()
        );
    }

}
