package com.lite.generator.framework.ui.help;

import com.lite.generator.framework.ui.BaseDialogController;
import com.lite.generator.framework.ui.MainController;
import javafx.scene.control.Button;

public class AboutDialogController extends BaseDialogController<MainController, AboutDialogControllerInterface> implements AboutDialogControllerInterface {

    public AboutDialogController(){

    }

    @Override
    public void init(){
        Button confirm = new Button("确定");
        confirm.getStyleClass().add("btn-primary");
        confirm.setOnAction((evt) -> getProxy().confirm());
        getDialog().getToolBar().getItems().add(confirm);
    }

    @Override
    public void show(){
        getDialog().show();
    }

    @Override
    public void confirm(){
        getDialog().hide();
    }

}
