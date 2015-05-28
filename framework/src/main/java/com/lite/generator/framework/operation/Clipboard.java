package com.lite.generator.framework.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clipboard<T> {

    private static Map<Class<?>, Clipboard> clipBoardMap = new HashMap<>();

    public Clipboard(){

    }

    public static <T> void copy(Class<T> type, List body){
        Clipboard clipBoard = getClipBoard(type);
        clipBoard.getClipBoard().clear();
        clipBoard.getClipBoard().addAll(body);
    }


    public static <T> List get(Class<T> type){
        Clipboard clipBoard = getClipBoard(type);
        return clipBoard.getClipBoard();
    }

    private static <T> Clipboard getClipBoard(Class<T> type){
        if(!clipBoardMap.containsKey(type)){
            clipBoardMap.put(type, new Clipboard<T>());
        }
        return clipBoardMap.get(type);
    }

    private List<T> clipBoard = new ArrayList<>();

    public List<T> getClipBoard(){
        return clipBoard;
    }

}
