package com.lite.generator.framework.operation;

import java.util.*;

public class History {

    private static Map<Object, History> historyMap = new HashMap<>();

    public History(){

    }

    public static void happen(Object body, Operation<?> operation) {
        List<Operation<?>> operations = new ArrayList<>();
        operations.add(operation);
        History.happen(body, operations);
    }

    public static void happen(Object body, List<Operation<?>> operations) {
        History history = getHistory(body);
        history.getBack().addFirst(operations);
        history.getGo().clear();
    }

    public static List<Operation<?>> back(Object body){
        History history = getHistory(body);
        LinkedList<List<Operation<?>>> back = history.getBack();
        if(back.isEmpty()){
            return null;
        }
        List<Operation<?>> operations = back.removeFirst();
        LinkedList<List<Operation<?>>> go = history.getGo();
        go.addFirst(operations);
        return operations;
    }

    public static List<Operation<?>> go(Object body){
        History history = getHistory(body);
        LinkedList<List<Operation<?>>> go = history.getGo();
        if(go.isEmpty()){
            return null;
        }
        List<Operation<?>> operations = go.removeFirst();
        LinkedList<List<Operation<?>>> back = history.getBack();
        back.addFirst(operations);
        return operations;
    }

    public static boolean hasGo(Object body){
        History history = getHistory(body);
        LinkedList<List<Operation<?>>> go = history.getGo();
        return !go.isEmpty();
    }


    public static boolean hasBack(Object body){
        History history = getHistory(body);
        LinkedList<List<Operation<?>>> back = history.getBack();
        return !back.isEmpty();
    }

    private static History getHistory(Object body){
        if(!historyMap.containsKey(body)){
            historyMap.put(body, new History());
        }
        return historyMap.get(body);
    }

    private LinkedList<List<Operation<?>>> go = new LinkedList<>();

    private LinkedList<List<Operation<?>>> back = new LinkedList<>();

    private LinkedList<List<Operation<?>>> getGo() {
        return go;
    }

    private LinkedList<List<Operation<?>>> getBack() {
        return back;
    }

}
