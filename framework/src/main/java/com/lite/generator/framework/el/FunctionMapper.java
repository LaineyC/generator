package com.lite.generator.framework.el;

import com.lite.generator.framework.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class FunctionMapper extends javax.el.FunctionMapper {

    private static Map<String, Map<String, Method>> functions = new HashMap<>();

    static {
        ReflectionUtil.loadClasses.forEach(clazz -> {
            Function function = (Function)clazz.getDeclaredAnnotation(Function.class);
            if(function != null){
                String prefix = function.prefix();
                if(!functions.containsKey(prefix)){
                    functions.put(prefix, new HashMap<>());
                }
                Map<String, Method> methods = functions.get(prefix);
                for (Method method : clazz.getMethods()) {
                    if (Modifier.isStatic(method.getModifiers())) {
                        methods.put(method.getName(), method);
                    }
                }
            }
        });
    }

    @Override
    public Method resolveFunction(String prefix, String localName) {
        if(!functions.containsKey(prefix)){
            return null;
        }
        return functions.get(prefix).get(localName);
    }

}
