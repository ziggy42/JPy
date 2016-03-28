package com.andrea.vm;

import com.andrea.pyobjects.PyCodeObject;


class Function {
    private VirtualMachine virtualMachine;
    private String name;
    private PyCodeObject codeObject;
    private Object[] defaults;

    Function(VirtualMachine virtualMachine, String name, PyCodeObject codeObject, Object[] defaults) {
        this.virtualMachine = virtualMachine;
        this.name = name;
        this.codeObject = codeObject;
        this.defaults = defaults;
    }

    Object call(Object[] args) throws Exception {
        Frame frame = virtualMachine.createFrame(codeObject, args);
        return virtualMachine.runFrame(frame);
    }
}
