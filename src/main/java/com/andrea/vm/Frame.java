package com.andrea.vm;


import com.andrea.pyobjects.PyCodeObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Frame {
    public Stack<Object> valueStack;
    public Stack<Block> blockStack;

    public Frame previousFrame;
    public PyCodeObject codeObject;

    public int bytecodeCounter;
    public int bytecodeSize;

    private Map<String, Object> locals;

    public Frame(PyCodeObject codeObject, Frame previousFrame) {
        this.codeObject = codeObject;
        this.previousFrame = previousFrame;

        this.valueStack = new Stack<>();
        this.blockStack = new Stack<>();
        this.locals = new HashMap<>();

        this.bytecodeCounter = 0;
        this.bytecodeSize = codeObject.getCodeLength();
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
