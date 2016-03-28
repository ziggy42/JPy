package com.andrea.vm;


import com.andrea.pyobjects.PyCodeObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Frame {
    public Stack<Object> valueStack;
    public Stack<Block> blockStack;

    public Frame previousFrame;
    public PyCodeObject pyCodeObject;

    public int bytecodeCounter;
    public int bytecodeSize;

    private Map<String, Object> locals;

    public Frame(PyCodeObject pyCodeObject, Frame previousFrame) {
        this(pyCodeObject, previousFrame, new HashMap<>());
    }

    public Frame(PyCodeObject pyCodeObject, Frame previousFrame, Map<String, Object> locals) {
        this.pyCodeObject = pyCodeObject;
        this.previousFrame = previousFrame;

        this.valueStack = new Stack<>();
        this.blockStack = new Stack<>();
        this.locals = locals;

        this.bytecodeCounter = 0;
        this.bytecodeSize = pyCodeObject.getCodeLength();
    }

    public boolean isInLocals(String key) {
        return locals.containsKey(key);
    }

    public Object getVariableByName(String name) {
        if (!locals.containsKey(name))
            throw new NullPointerException();
        return locals.get(name);
    }

    public void setVariable(String name, Object value) {
        locals.put(name, value);
    }

}
