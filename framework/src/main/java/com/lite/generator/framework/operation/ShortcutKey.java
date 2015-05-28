package com.lite.generator.framework.operation;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class ShortcutKey {

    //后退
    public static final KeyCodeCombination UNDO = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);

    //前进
    public static final KeyCodeCombination REDO = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);

    //删除
    public static final KeyCodeCombination DELETE = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);

    //剪切
    public static final KeyCodeCombination CUT = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);

    //复制
    public static final KeyCodeCombination COPY = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);

    //黏贴
    public static final KeyCodeCombination PASTE = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

}
