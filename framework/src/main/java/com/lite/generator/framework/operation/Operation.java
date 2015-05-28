package com.lite.generator.framework.operation;

public class Operation<T> {

    private int fromIndex = -1;

    private int toIndex = -1;

    private Type type;

    private T target;

    public Operation(){

    }

    public int getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }

    public static enum Type{

        Add,

        Remove,

        Move

    }

}
