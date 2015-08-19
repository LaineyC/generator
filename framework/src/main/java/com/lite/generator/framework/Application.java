package com.lite.generator.framework;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.model.*;
import com.lite.generator.framework.service.ValidateService;
import com.lite.generator.framework.tool.ControlTool;
import com.lite.generator.framework.tool.FXMLLoaderTool;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.tool.TaskTool;
import com.lite.generator.framework.ui.LoaderController;
import com.lite.generator.framework.ui.MainController;
import com.lite.generator.framework.util.FileUtil;
import com.lite.generator.framework.util.LoopedStreams;
import com.lite.generator.framework.util.ReflectionUtil;
import com.lite.generator.framework.util.SingletonUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.io.File;
import java.io.PrintStream;

public class Application extends javafx.application.Application{

    private static MainController controller;

    private static Stage window;

    private static Config config;

    public static MainController getController() {
        return controller;
    }

    public static Stage getWindow() {
        return window;
    }

    public static Config getConfig() {
        return config;
    }

    private ValidateService validateService = SingletonUtil.get(ValidateService.class);

    public static void main(String[] args) {
        LoopedStreams loopedStreams = SingletonUtil.get(LoopedStreams.class);
        PrintStream printStream = new PrintStream(loopedStreams.getOutputStream());
        System.setOut(printStream);
        System.setErr(printStream);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Stage loadStage = new Stage();
        FXMLLoader l = new FXMLLoader();
        Pane r = l.load(LoaderController.class.getResourceAsStream("loader.fxml"));
        LoaderController loaderController = l.getController();
        loaderController.getProxy().init();
        loadStage.getIcons().add(Icons.logo);
        loadStage.initStyle(StageStyle.TRANSPARENT);
        loadStage.setScene(new Scene(r));
        loadStage.setTitle("加载配置...");
        loadStage.setWidth(500);
        loadStage.setHeight(200);
        loadStage.show();

        Application.window = primaryStage;

        FXMLLoader loader = FXMLLoaderTool.load(MainController.class.getResourceAsStream("main.fxml"));
        Pane root = loader.getRoot();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(Icons.logo);
        primaryStage.setTitle("黑梦");
        MainController controller = loader.getController();
        Application.controller = controller;

        primaryStage.setOnCloseRequest((e) -> {
            String message = validateService.validateProject(controller.getProject());
            if(message != null){
                ControlTool.messageTooltip(message, Application.getWindow());
                e.consume();
                return;
            }
            controller.getProxy().exit();
            e.consume();
        });

        FileUtil.mkdirs(SystemVariable.logPath);
        FileUtil.mkdirs(SystemVariable.configPath);
        FileUtil.mkdirs(SystemVariable.templatePath);
        FileUtil.mkdirs(SystemVariable.projectConfigPath);
        FileUtil.mkdirs(SystemVariable.classPath);
        FileUtil.mkdirs(SystemVariable.libraryPath);

        //加载Jar
        ReflectionUtil.loadJar(SystemVariable.libraryPath);
        //加载class
        ReflectionUtil.loadClass(SystemVariable.classPath);

        TaskTool.newTask(
            "加载配置......",
            (p) -> controller.getProxy().loadConfig(),
            (configs) -> {
                Config config = (Config)configs[0];
                Application.config = config;
                primaryStage.setWidth(config.getWindowWidth());
                primaryStage.setHeight(config.getWindowHeight());
                primaryStage.setMaximized(config.getWindowMaximized());

                Project project = (Project)configs[1];
                controller.getProxy().init(config, project);

                primaryStage.setOnShown(e -> {
                    if(config.getPropertyFeatures().isEmpty()){
                        controller.showStartUpSettingDialog();
                    }
                });
                loadStage.close();
                primaryStage.show();
                return null;
            },
            (e) -> {
                File logFile = new File(SystemVariable.logPath + SystemVariable.fileSeparator  + "log.log");
                try {
                    Desktop.getDesktop().open(logFile);
                }
                catch (Exception exception){
                    //
                }
                finally {
                    loadStage.close();
                    primaryStage.close();
                }
                return null;
            },
            window
        );
    }

}