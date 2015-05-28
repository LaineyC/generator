package com.lite.generator.framework.ui;

public abstract class BaseDialogController<ControllerParent, ControllerInterface> extends BaseController<ControllerParent, ControllerInterface> {

    private DialogController dialogController;

    private Dialog dialog;

    public BaseDialogController(){

    }

    protected Dialog getDialog() {
        return dialog;
    }

    protected void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    protected DialogController getDialogController() {
        return dialogController;
    }

    protected void setDialogController(DialogController dialogController) {
        this.dialogController = dialogController;
    }

}
