package com.lite.generator.framework.ui;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.aop.Log;
import com.lite.generator.framework.control.GroupTreeCell;
import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.model.*;
import com.lite.generator.framework.service.StoreService;
import com.lite.generator.framework.service.ValidateService;
import com.lite.generator.framework.tool.ControlTool;
import com.lite.generator.framework.tool.FXMLLoaderTool;
import com.lite.generator.framework.tool.TaskTool;
import com.lite.generator.framework.tool.TemplateTool;
import com.lite.generator.framework.ui.group.ModelController;
import com.lite.generator.framework.ui.group.ModuleController;
import com.lite.generator.framework.ui.group.ProjectController;
import com.lite.generator.framework.ui.help.AboutDialog;
import com.lite.generator.framework.ui.help.TutorialDialog;
import com.lite.generator.framework.ui.setting.GenerateSettingDialog;
import com.lite.generator.framework.ui.setting.QuickSettingDialog;
import com.lite.generator.framework.ui.setting.StartUpSettingDialog;
import com.lite.generator.framework.ui.setting.TemplateSettingDialog;
import com.lite.generator.framework.ui.tool.ConsoleDialog;
import com.lite.generator.framework.util.FileUtil;
import com.lite.generator.framework.util.KeyGenerator;
import com.lite.generator.framework.util.SingletonUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MainController extends BaseController<BaseController, MainControllerInterface> implements MainControllerInterface {

    private TutorialDialog tutorialDialog;

    private AboutDialog aboutDialog;

    private StartUpSettingDialog startUpSettingDialog;

    private TemplateSettingDialog templateSettingDialog;

    private QuickSettingDialog quickSettingDialog;

    private GenerateSettingDialog generateSettingDialog;

    private ConsoleDialog consoleDialog;

    @FXML
    private AnchorPane root;

    @FXML
    private MenuBar menuBar;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TreeView<Group> projectTreeView;

    @FXML
    private TabPane groupsTabPane;

    private Project project;

    private Config config;

    private StoreService storeService = SingletonUtil.get(StoreService.class);

    private ValidateService validateService = SingletonUtil.get(ValidateService.class);

    public MainController(){

    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Project getProject() {
        return project;
    }

    public Config getConfig() {
        return config;
    }

    public TreeView<Group> getProjectTreeView() {
        return projectTreeView;
    }

    public TabPane getGroupsTabPane() {
        return groupsTabPane;
    }

    public SplitPane getSplitPane() {
        return splitPane;
    }

    @Override
    public void init(Config config, Project project){
        this.config = config;
        this.project = project;
        initMenuBar();
        initProjectTreeView();
        splitPane.setDividerPositions(config.getDividerPosition());
        editProject(project);
    }

    private List<MenuItem> projectMenuItems = new ArrayList<>();

    private List<MenuItem> moduleMenuItems = new ArrayList<>();

    private List<MenuItem> modelMenuItems = new ArrayList<>();

    private void initProjectTreeView(){
        projectTreeView.setCellFactory(f -> {
            GroupTreeCell groupTreeCell = new GroupTreeCell();
            groupTreeCell.setOnMouseClicked(e -> {
                if(e.getClickCount() == 2){
                    Group group = groupTreeCell.getItem();
                    if(group instanceof Project){
                        getProxy().editProject((Project)group);
                    }
                    else if(group instanceof Module){
                        getProxy().editModule((Module)group);
                    }
                    else if(group instanceof Model){
                        getProxy().editModel((Model)group);
                    }
                }
            });
            return groupTreeCell;
        });
        projectTreeView.setRoot(project);
        initProjectMenuItems();
        initModuleMenuItems();
        initModelMenuItems();
        MultipleSelectionModel<TreeItem<Group>> selectionModel = projectTreeView.getSelectionModel();
        ContextMenu contextMenu = projectTreeView.getContextMenu();
        ObservableList<MenuItem> menuItems = contextMenu.getItems();
        menuItems.addAll(projectMenuItems);
        projectTreeView.setOnContextMenuRequested(e -> {
            menuItems.clear();
            TreeItem<Group> group = selectionModel.getSelectedItem();
            if(group instanceof Project){
                menuItems.addAll(projectMenuItems);
            }
            else if(group instanceof Module){
                menuItems.addAll(moduleMenuItems);
            }
            else if(group instanceof Model){
                menuItems.addAll(modelMenuItems);
            }
        });
    }

    private void initProjectMenuItems(){
        MenuItem addModel = new MenuItem();
        addModel.setGraphic(new ImageView(Icons.model));
        addModel.setText("新建模型");
        addModel.setOnAction(e -> {
            Model model = getProxy().newModel(project);
            getProxy().editModel(model);
        });
        projectMenuItems.add(addModel);

        MenuItem addModule = new MenuItem();
        addModule.setText("新建模块");
        addModule.setGraphic(new ImageView(Icons.module));
        addModule.setOnAction(e -> {
            Module module = getProxy().newModule(project);
            getProxy().editModule(module);
        });
        projectMenuItems.add(addModule);

        MenuItem editProject = new MenuItem();
        editProject.setGraphic(new ImageView(Icons.edit));
        editProject.setText("编辑项目");
        editProject.setOnAction(e -> getProxy().editProject(project));
        projectMenuItems.add(editProject);
    }

    private void initModuleMenuItems(){
        MultipleSelectionModel<TreeItem<Group>> selectionModel = projectTreeView.getSelectionModel();
        MenuItem addModel = new MenuItem();
        addModel.setGraphic(new ImageView(Icons.model));
        addModel.setText("新建模型");
        addModel.setOnAction(e -> {
            Module module = (Module)selectionModel.getSelectedItem();
            Model model = getProxy().newModel(module);
            getProxy().editModel(model);
        });
        moduleMenuItems.add(addModel);

        MenuItem addModule = new MenuItem();
        addModule.setText("新建子模块");
        addModule.setGraphic(new ImageView(Icons.module));
        addModule.setOnAction(e -> {
            Module module = (Module)selectionModel.getSelectedItem();
            Module newModule = getProxy().newModule(module);
            getProxy().editModule(newModule);
        });
        moduleMenuItems.add(addModule);

        MenuItem editModule = new MenuItem();
        editModule.setGraphic(new ImageView(Icons.edit));
        editModule.setText("编辑模块");
        editModule.setOnAction(e -> {
            Module module = (Module)selectionModel.getSelectedItem();
            getProxy().editModule(module);
        });
        moduleMenuItems.add(editModule);

        MenuItem deleteModule = new MenuItem();
        deleteModule.setGraphic(new ImageView(Icons.delete));
        deleteModule.setText("删除模块");
        deleteModule.setOnAction(e -> {
            Module module = (Module)selectionModel.getSelectedItem();
            ControlTool.confirmDialog("确定删除模块 [ " + module.getName() + " ] ？",event -> {
                getProxy().deleteModule(module);
                return null;
            });
        });
        moduleMenuItems.add(deleteModule);
    }

    private void initModelMenuItems(){
        MultipleSelectionModel<TreeItem<Group>> selectionModel = projectTreeView.getSelectionModel();
        MenuItem editModel = new MenuItem();
        editModel.setGraphic(new ImageView(Icons.edit));
        editModel.setText("编辑模型");
        editModel.setOnAction(e -> {
            Model model = (Model)selectionModel.getSelectedItem();
            getProxy().editModel(model);
        });
        modelMenuItems.add(editModel);

        MenuItem deleteModel = new MenuItem();
        deleteModel.setGraphic(new ImageView(Icons.delete));
        deleteModel.setText("删除模型");
        deleteModel.setOnAction(e -> {
            Model model = (Model)selectionModel.getSelectedItem();
            ControlTool.confirmDialog("确定删除模型 [ " + model.getName() + " ] ？",event -> {
                getProxy().deleteModel(model);
                return null;
            });
        });
        modelMenuItems.add(deleteModel);
    }

    private Map<Group,Tab> groupsMap = new HashMap<>();

    @Override
    public void editProject(Project project){
        Tab tab;
        if(groupsMap.containsKey(project)){
            tab = groupsMap.get(project);
        }
        else{
            FXMLLoader loader = FXMLLoaderTool.load(ProjectController.class.getResourceAsStream("project.fxml"));
            ProjectController projectController = loader.getController();
            projectController.setParent(this);
            projectController.getProxy().init(project);
            tab = new Tab();
            tab.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if(newValue){
                        projectTreeView.getSelectionModel().select(project);
                    }
                });
            tab.setOnClosed(e -> groupsMap.remove(project));
            tab.setText(project.getName());
            tab.setContent(loader.getRoot());
            tab.setGraphic(new ImageView(Icons.product));
            tab.textProperty().bindBidirectional(project.nameProperty());
            groupsTabPane.getTabs().add(0, tab);
            groupsMap.put(project, tab);
        }
        groupsTabPane.getSelectionModel().select(tab);
    }

    @Override
    public Module newModule(Group group){
        Module module = new Module();
        String id = KeyGenerator.timeHex();
        module.setId(id);
        module.setName("新建模块_" + id);
        MultipleSelectionModel<TreeItem<Group>> selectionModel = projectTreeView.getSelectionModel();
        group.getChildren().add(module);
        selectionModel.select(module);
        projectTreeView.requestFocus();
        return module;
    }

    @Override
    public void editModule(Module module){
        Tab tab;
        if(groupsMap.containsKey(module)){
            tab = groupsMap.get(module);
        }
        else{
            FXMLLoader loader = FXMLLoaderTool.load(ModuleController.class.getResourceAsStream("module.fxml"));
            ModuleController moduleEditController = loader.getController();
            moduleEditController.setParent(this);
            moduleEditController.getProxy().init(module);
            tab = new Tab();
            tab.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if(newValue){
                        projectTreeView.getSelectionModel().select(module);
                    }
                });
            tab.setOnClosed(e -> groupsMap.remove(module));
            tab.setText(module.getName());
            tab.setContent(loader.getRoot());
            tab.setGraphic(new ImageView(Icons.module));
            tab.textProperty().bindBidirectional(module.nameProperty());
            groupsTabPane.getTabs().add(0, tab);
            groupsMap.put(module, tab);
        }
        groupsTabPane.getSelectionModel().select(tab);
    }

    @Override
    public void deleteModule(Module module){
        module.getParent().getChildren().remove(module);
        if(groupsMap.containsKey(module)){
            Tab tab = groupsMap.remove(module);
            groupsTabPane.getTabs().remove(tab);
        }
    }

    @Override
    public Model newModel(Group group){
        Model model = new Model();
        String id = KeyGenerator.timeHex();
        model.setId(id);
        model.setName("新建模型_" + id);
        MultipleSelectionModel<TreeItem<Group>> selectionModel = projectTreeView.getSelectionModel();
        group.getChildren().add(model);
        selectionModel.select(model);
        projectTreeView.requestFocus();
        return model;
    }

    @Override
    public void editModel(Model model){
        Tab tab;
        if(groupsMap.containsKey(model)){
            tab = groupsMap.get(model);
        }
        else {
            FXMLLoader loader = FXMLLoaderTool.load(ModuleController.class.getResourceAsStream("model.fxml"));
            ModelController modelController = loader.getController();
            modelController.setParent(this);
            modelController.getProxy().init(model);
            tab = new Tab();
            tab.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if(newValue){
                        projectTreeView.getSelectionModel().select(model);
                    }
                });
            tab.setOnClosed(e -> groupsMap.remove(model));
            tab.setText(model.getName());
            tab.setContent(loader.getRoot());
            tab.setGraphic(new ImageView(Icons.model));
            tab.textProperty().bindBidirectional(model.nameProperty());
            groupsTabPane.getTabs().add(0, tab);
            groupsMap.put(model, tab);
        }
        groupsTabPane.getSelectionModel().select(tab);
    }

    @Override
    public void deleteModel(Model model){
        model.getParent().getChildren().remove(model);
        if(groupsMap.containsKey(model)){
            Tab tab = groupsMap.remove(model);
            groupsTabPane.getTabs().remove(tab);
        }
    }

    private void initMenuBar(){
        ObservableList<Menu> menus = menuBar.getMenus();
        menus.get(0).setGraphic(new ImageView(Icons.file));
        ObservableList<MenuItem> fileItems = menus.get(0).getItems();
        //保存配置
        fileItems.get(0).setGraphic(new ImageView(Icons.saveConfig));
        fileItems.get(0).setOnAction((event) -> {
            String message = validateService.validateProject(project);
            if(message != null){
                ControlTool.messageTooltip(message, Application.getWindow());
                return;
            }
            TaskTool.newTask(
                "正在保存......",
                (p) -> {
                    getProxy().saveConfig();
                    return null;
                }
            );
        });
        //退出
        fileItems.get(2).setGraphic(new ImageView(Icons.exit));
        fileItems.get(2).setOnAction((event) -> {
            String message = validateService.validateProject(project);
            if(message != null){
                ControlTool.messageTooltip(message, Application.getWindow());
                return;
            }
            getProxy().exit();
        });

        menus.get(1).setGraphic(new ImageView(Icons.tool));
        ObservableList<MenuItem> utilItems = menus.get(1).getItems();
        Menu code = (Menu)utilItems.get(0);
        code.setGraphic(new ImageView(Icons.codeGenerate));
        ObservableList<MenuItem> codeItems = code.getItems();
        //生成代码
        codeItems.get(0).setGraphic(new ImageView(Icons.generateSetting));
        codeItems.get(0).setOnAction((event) -> {
            if(!validateService.existsPath(config.getGeneratePath())){
                ControlTool.messageTooltip("代码生成路径不存在！", Application.getWindow());
                return;
            }
            if(config.getTemplateConfig() == null){
                ControlTool.messageTooltip("未选择生成策略文件！", Application.getWindow());
                return;
            }
            getProxy().generateCode();
        });
        //浏览代码
        codeItems.get(1).setGraphic(new ImageView(Icons.browse));
        codeItems.get(1).setOnAction((event) -> {
            if(!validateService.existsPath(config.getGeneratePath())){
                ControlTool.messageTooltip("代码生成路径不存在！", Application.getWindow());
                return;
            }
            getProxy().browseCode();
        });
        //清空代码
        codeItems.get(2).setGraphic(new ImageView(Icons.delete));
        codeItems.get(2).setOnAction((event) -> {
            if(!validateService.existsPath(config.getGeneratePath())){
                ControlTool.messageTooltip("代码生成路径不存在！", Application.getWindow());
                return;
            }
            ControlTool.confirmDialog("确定清空代码，删除 [ " + config.getGeneratePath() + " ] 文件夹下所有文件？",e -> {
                getProxy().clearCode();
                return null;
            });
        });
        //控制台
        utilItems.get(1).setGraphic(new ImageView(Icons.console));
        utilItems.get(1).setOnAction((event) -> {
            if(consoleDialog == null){
                consoleDialog = new ConsoleDialog();
                consoleDialog.getController().setParent(this);
                consoleDialog.getController().getProxy().init();
            }
            consoleDialog.getController().getProxy().show();
        });

        menus.get(2).setGraphic(new ImageView(Icons.setting));
        ObservableList<MenuItem> settingItems = menus.get(2).getItems();
        //启动设置
        settingItems.get(0).setGraphic(new ImageView(Icons.startSetting));
        settingItems.get(0).setOnAction((event) -> showStartUpSettingDialog());
        //快捷设置
        settingItems.get(1).setGraphic(new ImageView(Icons.quickSetting));
        settingItems.get(1).setOnAction((event) -> {
            if(quickSettingDialog == null){
                quickSettingDialog = new QuickSettingDialog();
                quickSettingDialog.getController().setParent(this);
                quickSettingDialog.getController().getProxy().init();
            }
            quickSettingDialog.getController().getProxy().show();
        });
        //生成设置
        settingItems.get(2).setGraphic(new ImageView(Icons.generateSetting));
        settingItems.get(2).setOnAction((event) -> {
            if(generateSettingDialog == null){
                generateSettingDialog = new GenerateSettingDialog();
                generateSettingDialog.getController().setParent(this);
                generateSettingDialog.getController().getProxy().init();
            }
            generateSettingDialog.getController().getProxy().show();
        });
        //模板设置
        settingItems.get(3).setGraphic(new ImageView(Icons.templateSetting));
        settingItems.get(3).setOnAction((event) -> {
            if(templateSettingDialog == null){
                templateSettingDialog = new TemplateSettingDialog();
                templateSettingDialog.getController().setParent(this);
                templateSettingDialog.getController().getProxy().init();
            }
            templateSettingDialog.getController().getProxy().show();
        });

        menus.get(3).setGraphic(new ImageView(Icons.help));
        ObservableList<MenuItem> helpItems = menus.get(3).getItems();
        //教程
        helpItems.get(0).setGraphic(new ImageView(Icons.tutorial));
        helpItems.get(0).setOnAction((event) -> {
            if(tutorialDialog == null){
                tutorialDialog = new TutorialDialog();
                tutorialDialog.getController().setParent(this);
                tutorialDialog.getController().getProxy().init();
            }
            tutorialDialog.getController().getProxy().show();
        });
        //关于
        helpItems.get(1).setGraphic(new ImageView(Icons.about));
        helpItems.get(1).setOnAction((event) -> {
            if(aboutDialog == null){
                aboutDialog = new AboutDialog();
                aboutDialog.getController().setParent(this);
                aboutDialog.getController().getProxy().init();
            }
            aboutDialog.getController().getProxy().show();
        });

    }

    public void showStartUpSettingDialog(){
        if(startUpSettingDialog == null){
            startUpSettingDialog = new StartUpSettingDialog();
            startUpSettingDialog.getController().setParent(this);
            startUpSettingDialog.getController().getProxy().init();
        }
        startUpSettingDialog.getController().getProxy().show();
    }

    @Override
    public void exit(){
        TaskTool.newTask(
            "保存配置......",
            (p) -> {
                getProxy().saveConfig();
                return null;
            },
            (p) -> {
                Application.getWindow().close();
                return null;
            }
        );
    }

    @Override
    public void saveConfig(){
        config.setWindowWidth(Application.getWindow().getWidth());
        config.setWindowHeight(Application.getWindow().getHeight());
        config.setWindowMaximized(Application.getWindow().isMaximized());
        config.setDividerPosition(splitPane.getDividerPositions()[0]);
        storeService.saveConfig(config);
        storeService.saveProject(project);
    }

    @Override
    public Object[] loadConfig(){
        Object[] configs = new Object[2];
        configs[0] = storeService.getConfig();
        configs[1] = storeService.getProject();
        return configs;
    }

    @Override
    public void generateCode() {
        TaskTool.newTask(
            "正在生成......",
            (p) -> {
                Project generateProject = ControlTool.generateProject(project);
                TemplateTool.generate(config, generateProject);
                return null;
            }
        );
    }

    @Override
    public void browseCode(){
        TaskTool.newTask(
            "正在打开......",
            (p) -> {
                try {
                    File path = new File(config.getGeneratePath());
                    Desktop.getDesktop().open(path);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
                return null;
            }
        );
    }

    @Override
    public void clearCode(){
        TaskTool.newTask(
            "正在清空......",
            (p) -> {
                File[] files = new File(config.getGeneratePath()).listFiles();
                for (File file : files) {
                    FileUtil.deleteFile(file);
                }
                return null;
            }
        );
    }

}
