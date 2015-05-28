package com.lite.generator.framework.ui.help;

import com.lite.generator.framework.Application;
import com.lite.generator.framework.tool.ControlTool;
import com.lite.generator.framework.ui.BaseDialogController;
import com.lite.generator.framework.ui.MainController;
import javafx.animation.PauseTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker.State;
import javafx.util.Duration;

public class TutorialDialogController extends BaseDialogController<MainController, TutorialDialogControllerInterface> implements TutorialDialogControllerInterface {

    @FXML
    private WebView webView;

    public TutorialDialogController(){

    }

    @Override
    public void init(){
        Button confirm = new Button("确定");
        confirm.getStyleClass().add("btn-primary");
        confirm.setOnAction((evt) -> getProxy().confirm());
        getDialog().getToolBar().getItems().add(confirm);

        WebEngine webEngine = webView.getEngine();
        /*webEngine.getLoadWorker().stateProperty().addListener(
            (ObservableValue<? extends State> observable, State oldValue, State newValue) -> {
                if (newValue == State.SUCCEEDED) {
                    PauseTransition ptr = new PauseTransition(Duration.seconds(0.25));
                    ptr.setOnFinished((ActionEvent event) ->
                        ControlTool.progressForceStop(getDialog())
                    );
                    ptr.play();
                }
        });
        getDialog().show();
        ControlTool.progressStart("正在加载......", getDialog());*/
        webEngine.load(TutorialDialogController.class.getResource("/tutorial.html").toExternalForm());
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
