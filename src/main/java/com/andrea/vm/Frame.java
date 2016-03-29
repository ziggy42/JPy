package com.andrea.vm;


import com.andrea.pyobjects.PyCodeObject;

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
    private Map<String, Object> globals;

    Frame(PyCodeObject pyCodeObject, Frame previousFrame, Map<String, Object> globals, Map<String, Object> locals) {
        this.pyCodeObject = pyCodeObject;
        this.previousFrame = previousFrame;
        this.valueStack = new Stack<>();
        this.blockStack = new Stack<>();
        this.locals = locals;
        this.globals = globals;
        this.bytecodeCounter = 0;
        this.bytecodeSize = pyCodeObject.getCodeLength();
    }

    boolean isInLocals(String key) {
        return locals.containsKey(key);
    }

    boolean isInGlobals(String key) {
        return globals.containsKey(key);
    }

    Object getLocalVariableByName(String name) {
        return locals.get(name);
    }

    Object getGlobalVariableByName(String key) {
        return globals.get(key);
    }

    Map<String, Object> getLocals() {
        return this.locals;
    }

    Map<String, Object> getGlobals() {
        return this.globals;
    }

    void setVariable(String name, Object value) {
        locals.put(name, value);
    }

    void setGlobalVariable(String name, Object value) {
        locals.put(name, value);
    }

}
