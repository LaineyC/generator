package com.lite.generator.framework.template;

import com.lite.generator.framework.el.Context;

public class ReturnTag extends Tag {

    @Override
    public void execute(Context context) {
        throw new BreakException();
    }

}
