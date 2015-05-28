package com.lite.generator.framework.ui.group;

import com.lite.generator.framework.aop.Log;
import com.lite.generator.framework.control.DraggableTableRow;
import com.lite.generator.framework.icon.Icons;
import com.lite.generator.framework.model.Config;
import com.lite.generator.framework.model.Model;
import com.lite.generator.framework.operation.History;
import com.lite.generator.framework.operation.Operation;
import com.lite.generator.framework.operation.ShortcutKey;
import com.lite.generator.framework.tool.ControlTool;
import com.lite.generator.framework.tool.PropertyFeatureTool;
import com.lite.generator.framework.ui.BaseController;
import com.lite.generator.framework.ui.MainController;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModelController extends BaseController<MainController, ModelControllerInterface> implements ModelControllerInterface {

    @FXML
    private TextField modelNameTextfield;

    @FXML
    private TextField modelCommentTextfield;

    @FXML
    private TextArea modelDescriptionTextArea;

    @FXML
    private Button addPropertyButton;

    @FXML
    private Button addQuickPropertiesButton;

    @FXML
    private Button deleteModelButton;

    @FXML
    private TableView<Map<String, ? extends WritableValue>> modelPropertiesTableView;

    private Model model;

    @Override
    public void init(Model model){
        this.model = model;
        addPropertyButton.setGraphic(new ImageView(Icons.property));
        addPropertyButton.setOnAction(e -> getProxy().addProperty(model));
        addQuickPropertiesButton.setGraphic(new ImageView(Icons.quickProperties));
        addQuickPropertiesButton.setOnAction(e -> getProxy().addQuickProperties(model));
        addQuickPropertiesButton.setDisable(getParent().getConfig().getQuickProperties().isEmpty());
        deleteModelButton.setGraphic(new ImageView(Icons.delete));
        deleteModelButton.setOnAction(e ->
            ControlTool.confirmDialog("确定删除模型 [ " + model.getName() + " ] ？", event -> {
                getParent().getProxy().deleteModel(model);
                return null;
            })
        );
        modelNameTextfield.textProperty().bindBidirectional(model.nameProperty());
        modelCommentTextfield.textProperty().bindBidirectional(model.commentProperty());
        modelDescriptionTextArea.textProperty().bindBidirectional(model.descriptionProperty());
        initModelPropertiesTableView();
    }


    private void initModelPropertiesTableView() {
       Config config = getParent().getConfig();

        modelPropertiesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<MenuItem> menuItems = modelPropertiesTableView.getContextMenu().getItems();

        modelPropertiesTableView.setOnContextMenuRequested((event) -> {
            menuItems.forEach(item -> item.setDisable(false));
            menuItems.get(2).setDisable(config.getQuickProperties().isEmpty());
            menuItems.get(4).setDisable(!History.hasBack(model));
            menuItems.get(5).setDisable(!History.hasGo(model));
            menuItems.get(6).setDisable(modelPropertiesTableView.getSelectionModel().getSelectedItems().isEmpty());
        });

        //添加单个属性
        menuItems.get(0).setGraphic(new ImageView(Icons.property));
        menuItems.get(0).setOnAction((event) -> getProxy().addProperty(model));

        //添加多个属性
        menuItems.get(1).setGraphic(new ImageView(Icons.properties));
        menuItems.get(1).setOnAction((event) -> getProxy().addProperties(model));

        //添加快捷属性
        menuItems.get(2).setGraphic(new ImageView(Icons.quickProperties));
        menuItems.get(2).setOnAction((event) -> getProxy().addQuickProperties(model));

        //撤销
        menuItems.get(4).setGraphic(new ImageView(Icons.undo));
        menuItems.get(4).setOnAction((event) -> getProxy().undo(History.back(model), model));

        //重做
        menuItems.get(5).setGraphic(new ImageView(Icons.redo));
        menuItems.get(5).setOnAction((event) -> getProxy().redo(History.go(model), model));

        //删除
        menuItems.get(6).setGraphic(new ImageView(Icons.delete));
        menuItems.get(6).setOnAction((event) -> getProxy().deleteProperties(model));

        modelPropertiesTableView.getColumns().add(ControlTool.lineNumberTableCell());
        config.getPropertyFeatures().forEach(propertyFeature -> {
            TableColumn<Map<String, ? extends WritableValue>, ?> column = PropertyFeatureTool.createTableColumn(propertyFeature);
            modelPropertiesTableView.getColumns().add(column);
        });

        modelPropertiesTableView.setRowFactory(factory -> {
            DraggableTableRow<Map<String, ? extends WritableValue>> tableRow = new DraggableTableRow<>();
            EventHandler<? super DragEvent> dragDone= tableRow.getOnDragDone();
            tableRow.setOnDragDone(event -> {
                dragDone.handle(event);
                DraggableTableRow<Map<String, ? extends WritableValue>> dropRow = tableRow.getDropTableRow();
                if (dropRow != null && dropRow.getItem() != null && event.getTransferMode() == TransferMode.MOVE) {
                    Operation<Map<String, ? extends WritableValue>> operation = new Operation<>();
                    operation.setTarget(tableRow.getItem());
                    operation.setFromIndex(tableRow.getFromIndex());
                    operation.setToIndex(tableRow.getToIndex());
                    operation.setType(Operation.Type.Move);
                    History.happen(model, operation);
                }
            });
            return tableRow;
        });

        modelPropertiesTableView.setOnKeyPressed(event -> {
            if(ShortcutKey.UNDO.match(event)){
                if(History.hasBack(model)) {
                    getProxy().undo(History.back(model), model);
                }
            }
            else if(ShortcutKey.REDO.match(event)){
                if(History.hasGo(model)) {
                    getProxy().redo(History.go(model), model);
                }
            }
            else if(ShortcutKey.DELETE.match(event)){
                if(!modelPropertiesTableView.getSelectionModel().getSelectedItems().isEmpty()){
                    getProxy().deleteProperties(model);
                }
            }
        });

        modelPropertiesTableView.setItems(model.getProperties());
    }

    @Override
    public void deleteProperties(Model model){
        TableView.TableViewSelectionModel<Map<String, ? extends WritableValue>> selectionModel = modelPropertiesTableView.getSelectionModel();
        ObservableList<Integer> selectedIndices = selectionModel.getSelectedIndices();

        List<Operation<?>> operations = new ArrayList<>();
        for(int i = 0 ; i < selectedIndices.size() ; i++){
            int index = selectedIndices.get(i);
            Map<String, ? extends WritableValue> property = selectionModel.getSelectedItems().get(i);
            Operation<Map<String, ? extends WritableValue>> operation = new Operation<>();
            operation.setTarget(property);
            operation.setFromIndex(index);
            operation.setType(Operation.Type.Remove);
            operations.add(operation);
        }
        History.happen(model, operations);

        modelPropertiesTableView.getItems().removeAll(selectionModel.getSelectedItems());
        selectionModel.clearSelection();
    }

    @Override
    public void addProperties(Model model){
        TableView.TableViewSelectionModel<Map<String, ? extends WritableValue>> selectionModel = modelPropertiesTableView.getSelectionModel();
        int index = selectionModel.getSelectedIndex() + 1;
        ObservableList<Map<String, ? extends WritableValue>> addProperties = FXCollections.observableArrayList();
        addProperties.add(PropertyFeatureTool.createProperty());
        addProperties.add(PropertyFeatureTool.createProperty());
        addProperties.add(PropertyFeatureTool.createProperty());
        addProperties.add(PropertyFeatureTool.createProperty());
        addProperties.add(PropertyFeatureTool.createProperty());

        List<Operation<?>> operations = new ArrayList<>();
        for(int i = 0 ; i < addProperties.size() ; i++){
            Map<String, ? extends WritableValue> property = addProperties.get(i);
            Operation<Map<String, ? extends WritableValue>> operation = new Operation<>();
            operation.setTarget(property);
            operation.setFromIndex(index + i);
            operation.setType(Operation.Type.Add);
            operations.add(operation);
        }
        History.happen(model, operations);

        modelPropertiesTableView.getItems().addAll(index, addProperties);
        selectionModel.clearSelection();
        selectionModel.selectRange(index, index + addProperties.size());
        modelPropertiesTableView.scrollTo(index);
    }

    @Override
    public void addProperty(Model model) {
        TableView.TableViewSelectionModel<Map<String, ? extends WritableValue>> selectionModel = modelPropertiesTableView.getSelectionModel();
        int index = selectionModel.getSelectedIndex() + 1;
        Map<String, ? extends WritableValue> property = PropertyFeatureTool.createProperty();

        Operation<Map<String, ? extends WritableValue>> operation = new Operation<>();
        operation.setTarget(property);
        operation.setFromIndex(index);
        operation.setType(Operation.Type.Add);
        History.happen(model, operation);

        modelPropertiesTableView.getItems().add(index, property);
        selectionModel.clearSelection();
        selectionModel.select(index);
        modelPropertiesTableView.scrollTo(index);
        modelPropertiesTableView.requestFocus();
    }

    @Override
    public void addQuickProperties(Model model) {
        Config config = getParent().getConfig();
        TableView.TableViewSelectionModel<Map<String, ? extends WritableValue>> selectionModel = modelPropertiesTableView.getSelectionModel();
        int index = selectionModel.getSelectedIndex() + 1;
        ObservableList<Map<String, ? extends WritableValue>> addProperties = FXCollections.observableArrayList();

        List<Operation<?>> operations = new ArrayList<>();

        for (int i = 0; i < config.getQuickProperties().size(); i++) {
            Map<String, ? extends WritableValue> quickProperty = config.getQuickProperties().get(i);
            Map<String, ? extends WritableValue> property = PropertyFeatureTool.cloneProperty(quickProperty);

            Operation<Map<String, ? extends WritableValue>> operation = new Operation<>();
            operation.setTarget(property);
            operation.setFromIndex(index + i);
            operation.setType(Operation.Type.Add);
            operations.add(operation);

            addProperties.add(property);
        }
        History.happen(model, operations);

        modelPropertiesTableView.getItems().addAll(index, addProperties);
        selectionModel.clearSelection();
        selectionModel.selectRange(index, index + addProperties.size());
        modelPropertiesTableView.scrollTo(index);
        modelPropertiesTableView.requestFocus();
    }

    @Override
    public void undo(List<Operation<?>> operations, Model model){
        TableView.TableViewSelectionModel<Map<String, ? extends WritableValue>> selectionModel = modelPropertiesTableView.getSelectionModel();
        selectionModel.clearSelection();
        operations.forEach(operation -> {
            switch (operation.getType()) {
                case Remove:
                    modelPropertiesTableView.getItems().add(operation.getFromIndex(),
                            (Map<String, ? extends WritableValue>) operation.getTarget());
                    selectionModel.select(operation.getFromIndex());
                    break;
                case Add:
                    modelPropertiesTableView.getItems().remove(operation.getTarget());
                    break;
                case Move:
                    ObservableList<Map<String, ? extends WritableValue>> items = modelPropertiesTableView.getItems();
                    items.remove(operation.getTarget());
                    items.add(operation.getFromIndex(), (Map<String, ? extends WritableValue>)operation.getTarget());
                    selectionModel.select((Map<String, ? extends WritableValue>)operation.getTarget());
                    break;
            }
        });
    }

    @Override
    public void redo(List<Operation<?>> operations, Model model){
        TableView.TableViewSelectionModel<Map<String, ? extends WritableValue>> selectionModel = modelPropertiesTableView.getSelectionModel();

        selectionModel.clearSelection();
        operations.forEach(operation -> {
            switch (operation.getType()) {
                case Remove:
                    modelPropertiesTableView.getItems().remove(operation.getTarget());
                    break;
                case Add:
                    modelPropertiesTableView.getItems().add(operation.getFromIndex(),
                            (Map<String, ? extends WritableValue>)operation.getTarget());
                    selectionModel.select(operation.getFromIndex());
                    break;
                case Move:
                    ObservableList<Map<String, ? extends WritableValue>> items = modelPropertiesTableView.getItems();
                    items.remove(operation.getTarget());
                    items.add(operation.getToIndex(), (Map<String, ? extends WritableValue>)operation.getTarget());
                    selectionModel.select((Map<String, ? extends WritableValue>)operation.getTarget());
                    break;
            }
        });
    }

}
