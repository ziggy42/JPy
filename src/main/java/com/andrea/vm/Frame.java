package com.andrea.vm;


import com.andrea.pyobjects.PyCodeObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Frame {
    Stack<Object> valueStack;
    Stack<Block> blockStack;

    Frame previousFrame;
    PyCodeObject pyCodeObject;

    int bytecodeCounter;
    int bytecodeSize;

    private Map<String, Object> locals;

    Frame(PyCodeObject pyCodeObject, Frame previousFrame) {
        this(pyCodeObject, previousFrame, new HashMap<>());
    }

    Frame(PyCodeObject pyCodeObject, Frame previousFrame, Map<String, Object> locals) {
        this.pyCodeObject = pyCodeObject;
        this.previousFrame = previousFrame;

        this.valueStack = new Stack<>();
        this.blockStack = new Stack<>();
        this.locals = locals;

        this.bytecodeCounter = 0;
        this.bytecodeSize = pyCodeObject.getCodeLength();
    }

    boolean isInLocals(String key) {
        return locals.containsKey(key);
    }

    Object getVariableByName(String name) {
        if (!locals.containsKey(name))
            throw new NullPointerException();
        return locals.get(name);
    }

    void setVariable(String name, Object value) {
        locals.put(name, value);
    }

}
