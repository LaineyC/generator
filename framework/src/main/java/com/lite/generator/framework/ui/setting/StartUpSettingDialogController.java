package com.lite.generator.framework.ui.setting;

import com.lite.generator.framework.control.DraggableTableRow;
import com.lite.generator.framework.control.LineNumberTableCell;
import com.lite.generator.framework.converter.StringConverters;
import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.model.PropertyFeature;
import com.lite.generator.framework.model.PropertyType;
import com.lite.generator.framework.service.ValidateService;
import com.lite.generator.framework.tool.ControlTool;
import com.lite.generator.framework.tool.PropertyFeatureTool;
import com.lite.generator.framework.ui.BaseDialogController;
import com.lite.generator.framework.ui.MainController;
import com.lite.generator.framework.util.SingletonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;

public class StartUpSettingDialogController extends BaseDialogController<MainController, StartUpSettingDialogControllerInterface> implements StartUpSettingDialogControllerInterface {

    @FXML
    private TableView<PropertyFeature> propertyFeaturesTableView;

    private ValidateService validateService = SingletonUtil.get(ValidateService.class);

    @Override
    public void init(){
        Button confirm = new Button("确定");
        confirm.getStyleClass().add("btn-primary");
        confirm.setOnAction((evt) -> {
            String message = validateService.validatePropertyFeatures(getParent().getConfig().getPropertyFeatures());
            if(message != null){
                ControlTool.messageTooltip(message, getDialog());
                return;
            }
            getProxy().confirm();
        });
        getDialog().getToolBar().getItems().add(confirm);

        propertyFeaturesTableView.setRowFactory( e -> new DraggableTableRow<>());

        propertyFeaturesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<TableColumn<PropertyFeature, ?>> columns = propertyFeaturesTableView.<PropertyFeature>getColumns();

        TableColumn<PropertyFeature, String> lineNumberColumn = (TableColumn<PropertyFeature, String>) columns.get(0);
        lineNumberColumn.setCellFactory(f -> new LineNumberTableCell<>());

        TableColumn<PropertyFeature, String> nameColumn = (TableColumn<PropertyFeature, String>) columns.get(1);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(f -> new TextFieldTableCell<>(StringConverters.String));

        TableColumn<PropertyFeature, String> commentColumn = (TableColumn<PropertyFeature, String>) columns.get(2);
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        commentColumn.setCellFactory(f -> new TextFieldTableCell<>(StringConverters.String));

        TableColumn<PropertyFeature, Double> viewWidthColumn = (TableColumn<PropertyFeature, Double>) columns.get(3);
        viewWidthColumn.setCellValueFactory(new PropertyValueFactory<>("viewWidth"));
        viewWidthColumn.setCellFactory(f -> {
            TextFieldTableCell cell = new TextFieldTableCell<>(StringConverters.Double);
            cell.setAlignment(Pos.CENTER_RIGHT);
            return cell;
        });

        TableColumn<PropertyFeature, PropertyType> typeColumn = (TableColumn<PropertyFeature, PropertyType>) columns.get(4);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellFactory(f -> {
            ChoiceBoxTableCell cell = new ChoiceBoxTableCell<>(PropertyType.values());
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<PropertyFeature, String> defaultValueColumn = (TableColumn<PropertyFeature, String>) columns.get(5);
        defaultValueColumn.setCellValueFactory(new PropertyValueFactory<>("defaultValue"));
        defaultValueColumn.setCellFactory(f -> new TextFieldTableCell<>(StringConverters.String));

        TableColumn<PropertyFeature, String> checkStringColumn = (TableColumn<PropertyFeature, String>) columns.get(6);
        checkStringColumn.setCellValueFactory(new PropertyValueFactory<>("checkStatement"));
        checkStringColumn.setCellFactory(f -> new TextFieldTableCell<>(StringConverters.String));

        TableColumn<PropertyFeature, String> checkMessageColumn = (TableColumn<PropertyFeature, String>) columns.get(7);
        checkMessageColumn.setCellValueFactory(new PropertyValueFactory<>("checkMessage"));
        checkMessageColumn.setCellFactory(f -> new TextFieldTableCell<>(StringConverters.String));

        TableColumn<PropertyFeature, String> referenceValuesColumn = (TableColumn<PropertyFeature, String>) columns.get(8);
        referenceValuesColumn.setCellValueFactory(new PropertyValueFactory<>("referenceValues"));
        referenceValuesColumn.setCellFactory(f -> new TextFieldTableCell<>(StringConverters.String));

        ObservableList<MenuItem> menuItems = propertyFeaturesTableView.getContextMenu().getItems();

        propertyFeaturesTableView.setOnContextMenuRequested((event) ->
            menuItems.get(3).setDisable(propertyFeaturesTableView.getSelectionModel().getSelectedItems().isEmpty())
        );

        //添加单个属性描述
        menuItems.get(0).setGraphic(new ImageView(Icons.property));
        menuItems.get(0).setOnAction((event) -> getProxy().addProperty());
        //添加多个属性描述
        menuItems.get(1).setGraphic(new ImageView(Icons.properties));
        menuItems.get(1).setOnAction((event) -> getProxy().addProperties());
        //删除选中属性描述
        menuItems.get(3).setGraphic(new ImageView(Icons.delete));
        menuItems.get(3).setOnAction((event) -> getProxy().deleteProperties());

        propertyFeaturesTableView.setItems(getParent().getConfig().getPropertyFeatures());
    }

    @Override
    public void show(){
        getDialog().show();
    }

    @Override
    public void confirm(){
        PropertyFeatureTool.propertyFeatureMap.clear();
        getParent().getConfig().getPropertyFeatures().forEach(propertyFeature -> {
            PropertyFeatureTool.propertyFeatureMap.put(propertyFeature.getName(), propertyFeature);
            PropertyFeatureTool.setConverter(propertyFeature);
        });

        getDialog().hide();
    }

    @Override
    public void addProperty(){
        TableView.TableViewSelectionModel selectionModel = propertyFeaturesTableView.getSelectionModel();
        int index = selectionModel.getSelectedIndex() + 1;
        propertyFeaturesTableView.getItems().add(index, new PropertyFeature());
        selectionModel.clearSelection();
        selectionModel.select(index);
        propertyFeaturesTableView.scrollTo(index);
    }

    @Override
    public void addProperties(){
        TableView.TableViewSelectionModel selectionModel = propertyFeaturesTableView.getSelectionModel();
        int index = selectionModel.getSelectedIndex() + 1;
        ObservableList<PropertyFeature> addColumns = FXCollections.observableArrayList();
        addColumns.addAll(new PropertyFeature(), new PropertyFeature(),
                new PropertyFeature(), new PropertyFeature(), new PropertyFeature());
        propertyFeaturesTableView.getItems().addAll(index, addColumns);
        selectionModel.clearSelection();
        selectionModel.selectRange(index, index + addColumns.size());
        propertyFeaturesTableView.scrollTo(index);
    }

    @Override
    public void deleteProperties(){
        TableView.TableViewSelectionModel<PropertyFeature> selectionModel = propertyFeaturesTableView.getSelectionModel();
        propertyFeaturesTableView.getItems().removeAll(selectionModel.getSelectedItems());
        selectionModel.clearSelection();
    }

}
