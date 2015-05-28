package com.lite.generator.framework.template;

import com.lite.generator.framework.el.Context;

import java.util.ArrayList;
import java.util.List;

public abstract class Tag{

    private List<Tag> children = new ArrayList<>();

    private Tag parent;

    public Tag(){

    }

    public abstract void execute(Context context);

    public Tag getParent() {
        return parent;
    }

    public void setParent(Tag parent) {
        this.parent = parent;
    }

    public List<Tag> getChildren() {
        return children;
    }

    public void setChildren(List<Tag> children) {
        this.children = children;
    }

}
