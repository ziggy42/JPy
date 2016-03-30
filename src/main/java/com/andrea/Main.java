package com.andrea;

import com.andrea.pyobjects.PyCodeObject;
import com.andrea.vm.VirtualMachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String filePath;
        if (args.length == 0) {
            System.out.print("File Path: ");
            Scanner scanner = new Scanner(System.in);
            filePath = scanner.nextLine();
        } else filePath = args[0];

        PyCodeObject pyCodeObject;
        VirtualMachine vm = new VirtualMachine();
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            pyCodeObject = PyCodeObject.buildPyCodeObject(in);
            vm.runCode(pyCodeObject);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error executing file: " + e.getMessage());
        }
    }
}
