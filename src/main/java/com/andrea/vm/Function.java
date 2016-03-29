package com.andrea.vm;

import com.andrea.pyobjects.PyCodeObject;


class Function {
    private VirtualMachine virtualMachine;
    private String name;
    private PyCodeObject pyCodeObject;
    private Object[] defaults;

    Function(VirtualMachine virtualMachine, String name, PyCodeObject pyCodeObject, Object[] defaults) {
        this.virtualMachine = virtualMachine;
        this.name = name;
        this.pyCodeObject = pyCodeObject;
        this.defaults = defaults;
    }

    Object call(Object[] args) throws Exception {
        Frame lastFrame = virtualMachine.getCurrentFrame();
        return virtualMachine.runFrame(
                virtualMachine.createFrame(pyCodeObject, args, lastFrame.getGlobals(), lastFrame.getLocals()));
    }
}
