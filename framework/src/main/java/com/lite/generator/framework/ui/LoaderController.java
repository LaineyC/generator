package com.lite.generator.framework.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class LoaderController extends BaseController<BaseController, LoderControllerInterface> implements LoderControllerInterface {

    @FXML
    private ProgressBar loadProgressBar;

    public LoaderController() {

    }

    @Override
    public void init(){

    }

}
