package com.lite.generator.framework.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class LoaderController extends BaseController<BaseController, LoaderControllerInterface> implements LoaderControllerInterface {

    @FXML
    private ProgressBar loadProgressBar;

    public LoaderController() {

    }

    @Override
    public void init(){

    }

}
