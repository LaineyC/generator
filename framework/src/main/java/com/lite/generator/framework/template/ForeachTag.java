package com.lite.generator.framework.template;

import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.el.Parser;
import javafx.beans.property.SimpleStringProperty;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class ForeachTag extends Tag{

    private SimpleStringProperty item = new SimpleStringProperty();

    private SimpleStringProperty items = new SimpleStringProperty();

    private SimpleStringProperty status = new SimpleStringProperty();

    public String getItem() {
        return item.get();
    }

    public SimpleStringProperty itemProperty() {
        return item;
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public String getItems() {
        return items.get();
    }

    public SimpleStringProperty itemsProperty() {
        return items;
    }

    public void setItems(String items) {
        this.items.set(items);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void execute(Context context){
        String item = this.getItem();
        String status = this.getStatus();
        Object itemsObject = Parser.parseObject(this.getItems(), context);
        Context exeContext = new Context();
        exeContext.setVariable(item,"item");
        exeContext.mergeVariable(context);
        int index = 0;
        if(itemsObject instanceof List){
            List<?> items = (List<?>)itemsObject;
            for(Object t : items){
                exeContext.setVariable(item,t);
                if(status != null && !"".equals(status)){
                    Status s = new Status();
                    s.setFirst(index == 0);
                    s.setLast(index == items.size() - 1);
                    s.setIndex(index);
                    s.setCount(index + 1);
                    s.setCurrent(t);
                    exeContext.setVariable(status, s);
                    index++;
                }
                for(Tag child : this.getChildren()){
                    child.setParent(this);
                    try{
                        child.execute(exeContext);
                    }
                    catch(BreakException breakException){
                        return;
                    }
                    catch(ContinueException continueException){

                    }
                }
            }
        }
        else if(itemsObject instanceof Map){
            Map<String, ?> items = (Map<String, ?>)itemsObject;
            for(Map.Entry<String, ?> t : items.entrySet()){
                exeContext.setVariable(item,t);
                if(status != null && !"".equals(status)){
                    Status s = new Status();
                    s.setFirst(index == 0);
                    s.setLast(index == items.size() - 1);
                    s.setIndex(index);
                    s.setCount(index + 1);
                    s.setCurrent(t);
                    exeContext.setVariable(status, s);
                    index++;
                }
                for(Tag child : this.getChildren()){
                    child.setParent(this);
                    try{
                        child.execute(exeContext);
                    }
                    catch(BreakException breakException){
                        return;
                    }
                    catch(ContinueException continueException){

                    }
                }
            }
        }
        else if(itemsObject.getClass().isArray()){
            int length = Array.getLength(itemsObject);
            for(int i = 0 ; i < length ; i++){
                Object t = Array.get(itemsObject, i);
                exeContext.setVariable(item,t);
                if(status != null && !"".equals(status)){
                     Status s = new Status();
                     s.setFirst(index == 0);
                     s.setLast(index == length - 1);
                     s.setIndex(index);
                     s.setCount(index + 1);
                     s.setCurrent(t);
                    exeContext.setVariable(status, s);
                     index++;
                 }
                for(Tag child : this.getChildren()){
                    child.setParent(this);
                    try{
                        child.execute(exeContext);
                    }
                    catch(BreakException breakException){
                        return;
                    }
                    catch(ContinueException continueException){

                    }
                }
            }
        }
    }

    public static class Status{

             private boolean first;

             private boolean last;

             private int index;

             private int count;

             private Object current;

             public Status(){

             }

             public boolean isFirst() {
                 return first;
             }

             public void setFirst(boolean first) {
                 this.first = first;
             }

             public boolean isLast() {
                 return last;
             }

             public void setLast(boolean last) {
                 this.last = last;
             }

             public int getIndex() {
                 return index;
             }

             public void setIndex(int index) {
                 this.index = index;
             }

             public int getCount() {
                 return count;
             }

             public void setCount(int count) {
                 this.count = count;
             }

             public Object getCurrent() {
                 return current;
             }

             public void setCurrent(Object current) {
                 this.current = current;
             }

         }

}
