package com.lite.generator.framework.tool;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.InputStream;

public class FXMLLoaderTool {

    public static FXMLLoader load(InputStream inputStream){
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.load(inputStream);
        }
        catch (IOException exception){
            throw new RuntimeException(inputStream.toString(), exception);
        }
        return loader;
    }
}
