package com.andrea;

import com.andrea.pyobjects.PyCodeObject;
import com.andrea.vm.VirtualMachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        String filePath;
        if (args.length == 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("File Path: ");
            filePath = in.readLine();
            in.close();
        } else {
            filePath = args[0];
        }

        BufferedReader in = new BufferedReader(new FileReader(filePath));
        PyCodeObject pyCodeObject = PyCodeObject.buildPyCodeObject(in);
        in.close();

        VirtualMachine vm = new VirtualMachine();
        vm.runCode(pyCodeObject);
    }
}
