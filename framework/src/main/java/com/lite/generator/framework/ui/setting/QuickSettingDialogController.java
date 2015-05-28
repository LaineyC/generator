package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.control.DraggableTableRow;
import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.service.ValidateService;
import com.lite.generator.framework.tool.ControlTool;
import com.lite.generator.framework.tool.PropertyFeatureTool;
import com.lite.generator.framework.ui.BaseDialogController;
import com.lite.generator.framework.ui.MainController;
import com.lite.generator.framework.util.SingletonUtil;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.util.Map;

public class QuickSettingDialogController extends BaseDialogController<MainController, QuickSettingDialogControllerInterface> implements QuickSettingDialogControllerInterface {

    @FXML
    private TableView<Map<String, ? extends WritableValue>> quickPropertiesTableView;

    private ValidateService validateService = SingletonUtil.get(ValidateService.class);

    @Override
    public void init(){
        Button confirm = new Button("确定");
        confirm.getStyleClass().add("btn-primary");
        confirm.setOnAction((evt) -> getProxy().confirm());
        getDialog().getToolBar().getItems().add(confirm);

        quickPropertiesTableView.setRowFactory( e -> new DraggableTableRow<>());

        quickPropertiesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<MenuItem> menuItems = quickPropertiesTableView.getContextMenu().getItems();

        quickPropertiesTableView.setOnContextMenuRequested((event) ->
            menuItems.get(3).setDisable(quickPropertiesTableView.getSelectionModel().getSelectedItems().isEmpty())
        );

        //添加单个属性
        menuItems.get(0).setGraphic(new ImageView(Icons.property));
        menuItems.get(0).setOnAction((event) -> getProxy().addProperty());
        //添加多个属性
        menuItems.get(1).setGraphic(new ImageView(Icons.properties));
        menuItems.get(1).setOnAction((event) -> getProxy().addProperties());
        //删除选中属性
        menuItems.get(3).setGraphic(new ImageView(Icons.delete));
        menuItems.get(3).setOnAction((event) -> getProxy().deleteProperties());

        quickPropertiesTableView.getColumns().add(ControlTool.lineNumberTableCell());
        getParent().getConfig().getPropertyFeatures().forEach(propertyFeature -> {
            TableColumn column = PropertyFeatureTool.createTableColumn(propertyFeature);
            quickPropertiesTableView.getColumns().add(column);
        });

        quickPropertiesTableView.setItems(getParent().getConfig().getQuickProperties());
    }

    @Override
    public void show(){
        getDialog().show();
    }

    @Override
    public void confirm(){
        String message = validateService.validateProperties(quickPropertiesTableView.getItems());
        if(message != null){
            ControlTool.messageTooltip(message, getDialog());
            return;
        }
        getDialog().hide();
    }

    @Override
    public void addProperty(){
        TableView.TableViewSelectionModel selectionModel = quickPropertiesTableView.getSelectionModel();
        int index = selectionModel.getSelectedIndex() + 1;
        quickPropertiesTableView.getItems().add(index, PropertyFeatureTool.createProperty());
        selectionModel.clearSelection();
        selectionModel.select(index);
        quickPropertiesTableView.scrollTo(index);
    }

    @Override
    public void addProperties(){
        TableView.TableViewSelectionModel selectionModel = quickPropertiesTableView.getSelectionModel();
        ObservableList<Map<String, ? extends WritableValue>> addColumns = FXCollections.observableArrayList();
        addColumns.addAll(
                PropertyFeatureTool.createProperty(),
                PropertyFeatureTool.createProperty(),
                PropertyFeatureTool.createProperty(),
                PropertyFeatureTool.createProperty(),
                PropertyFeatureTool.createProperty()
        );
        int index = selectionModel.getSelectedIndex() + 1;
        quickPropertiesTableView.getItems().addAll(index, addColumns);
        selectionModel.clearSelection();
        selectionModel.selectRange(index, index + addColumns.size());
        quickPropertiesTableView.scrollTo(index);
    }

    @Override
    public void deleteProperties(){
        TableView.TableViewSelectionModel<Map<String, ? extends WritableValue>> selectionModel = quickPropertiesTableView.getSelectionModel();
        quickPropertiesTableView.getItems().removeAll(selectionModel.getSelectedItems());
        selectionModel.clearSelection();
    }

}
