package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.model.Config;
import com.lite.generator.framework.service.StoreService;
import com.lite.generator.framework.template.TemplateConfig;
import com.lite.generator.framework.tool.SystemVariable;
import com.lite.generator.framework.tool.TaskTool;
import com.lite.generator.framework.ui.BaseDialogController;
import com.lite.generator.framework.ui.MainController;
import com.lite.generator.framework.util.SingletonUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TemplateSettingDialogController extends BaseDialogController<MainController, TemplateSettingDialogControllerInterface> implements TemplateSettingDialogControllerInterface {

    @FXML
    private TableView<TemplateConfig> templateConfigsTableView;

    private ObservableList<TemplateConfig> templateConfigs;

    private StoreService storeService = SingletonUtil.get(StoreService.class);

    @Override
    public void init(){
        Config generateConfig = getParent().getConfig();

        Button open = new Button("浏览");
        open.setContentDisplay(ContentDisplay.RIGHT);
        open.setGraphic(new ImageView(Icons.browse));
        open.getStyleClass().add("btn-info");
        open.setOnAction((evt) ->
            getProxy().browse()
        );

        Button refresh = new Button("刷新");
        refresh.setContentDisplay(ContentDisplay.RIGHT);
        refresh.setGraphic(new ImageView(Icons.refresh));
        refresh.getStyleClass().add("btn-info");
        refresh.setOnAction((evt) -> getProxy().refresh());

        Button confirm = new Button("确定");
        confirm.getStyleClass().add("btn-primary");
        confirm.setOnAction((evt) -> getProxy().confirm());
        getDialog().getToolBar().getItems().addAll(confirm, refresh, open);

        ObservableList<TableColumn<TemplateConfig, ?>> columns = templateConfigsTableView.<TemplateConfig>getColumns();

        TableColumn<TemplateConfig, Boolean> applyColumn = (TableColumn<TemplateConfig, Boolean>) columns.get(0);
        applyColumn.setCellValueFactory(new PropertyValueFactory<>("apply"));
        applyColumn.setCellFactory(p -> new CheckBoxTableCell<>());

        TableColumn<TemplateConfig, String> nameColumn = (TableColumn<TemplateConfig, String>) columns.get(1);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<TemplateConfig, String> commentColumn = (TableColumn<TemplateConfig, String>) columns.get(2);
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        templateConfigsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null){
                return;
            }
            templateConfigs.forEach(templateConfig -> templateConfig.setApply(false));
            newValue.setApply(true);

            generateConfig.setTemplateConfig(newValue);
            templateConfigsTableView.getSelectionModel().select(newValue);
        });

        getDialog().show();
        refresh();
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
    public void refresh(){
        TaskTool.newTask("正在刷新......",
            (p) -> storeService.getTemplateConfigs(),
            (p) -> {
                refresh(storeService.getTemplateConfigs());
                return null;
            },
            getDialog()
        );
    }

    private void refresh(ObservableList<TemplateConfig> templateConfigs){
        Config config = getParent().getConfig();
        this.templateConfigs = templateConfigs;

        templateConfigsTableView.setItems(templateConfigs);
        templateConfigs.forEach(templateConfig -> {
            if(config.getTemplateConfig() == null){
                return;
            }
            if(templateConfig.getName().equals(config.getTemplateConfig().getName())){
                templateConfig.setApply(true);
                templateConfigsTableView.getSelectionModel().select(templateConfig);
                config.setTemplateConfig(templateConfig);
            }
        });
    }

    @Override
    public void browse(){
        TaskTool.newTask(
            "正在打开......",
            (p) -> {
                try {
                    File path = new File(SystemVariable.templatePath);
                    Desktop.getDesktop().open(path);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
                return null;
            },
            getDialog()
        );
    }

}
