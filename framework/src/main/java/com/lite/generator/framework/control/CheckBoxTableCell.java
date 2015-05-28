package com.lite.generator.framework.control;

import javafx.util.StringConverter;

public class CheckBoxTableCell<S,T> extends javafx.scene.control.cell.CheckBoxTableCell<S,T> {

    //private boolean isFirst = true;

    public CheckBoxTableCell(){
        this(null);
    }

    public CheckBoxTableCell(StringConverter<T> converter){
        super(null, converter);
    }

    @Override public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        /*if (!empty) {
            if(!isFirst){
                try {
                    Field field = TableCell.class.getDeclaredField("itemDirty");
                    field.setAccessible(true);
                    if((boolean)field.get(this)){
                        //Application.getController().dirtyClass();
                    }
                }
                catch (Exception exception){

                }
            }
            isFirst = false;
        }*/
    }

}
