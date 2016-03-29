package com.andrea.vm;

import com.andrea.pyobjects.PyCodeObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;


public class VirtualMachineTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final VirtualMachine virtualMachine = new VirtualMachine();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void runCode() throws Exception {
        /*
        i = 0
        while i <= 10:
            if i%2 == 0:
                print(i)
            elif i%3 == 0:
                print("meh")
            else:
                print("no")
            i += 1
         */
        String test = "0\n" +
                "0\n" +
                "2\n" +
                "64\n" +
                "100 0 0 90 0 0 120 94 0 101 0 0 100 1 0 107 1 0 114 102 0 101 0 0 100 2 0 22 100 0 0 107 2 0 114 " +
                "50 0 101 1 0 101 0 0 131 1 0 1 110 39 0 101 0 0 100 3 0 22 100 0 0 107 2 0 114 79 0 101 1 0 100 4 " +
                "0 131 1 0 1 110 10 0 101 1 0 100 5 0 131 1 0 1 101 0 0 100 6 0 55 90 0 0 113 9 0 87 100 7 0 83\n" +
                "8\n" +
                "0\n" +
                "10\n" +
                "2\n" +
                "3\n" +
                "'meh'\n" +
                "'no'\n" +
                "1\n" +
                "None\n" +
                "i print\n" +
                "\n" +
                "\n" +
                "\n" +
                "tests/test.py\n" +
                "<module>\n" +
                "1\n" +
                "0\n" +
                "b'\\x06\\x01\\x0f\\x01\\x10\\x01\\r\\x01\\x10\\x01\\r\\x02\\n\\x01'\n";
        PyCodeObject pyCodeObject = PyCodeObject.buildPyCodeObject(new BufferedReader(new StringReader(test)));
        virtualMachine.runCode(pyCodeObject);
        assertEquals("0\nno\n2\nmeh\n4\nno\n6\nno\n8\nmeh\n10\n", outContent.toString());
        outContent.reset();

        /*
        a = 1
        b = 2
        c = a
        d = a + c
        print(d)
         */
        test = "0\n" +
                "0\n" +
                "2\n" +
                "64\n" +
                "100 0 0 90 0 0 100 1 0 90 1 0 101 0 0 90 2 0 101 0 0 101 2 0 23 90 3 0 101 4 0 101 3 0 131 1 0 1 " +
                "100 2 0 83\n" +
                "3\n" +
                "1\n" +
                "2\n" +
                "None\n" +
                "a b c d print\n" +
                "\n" +
                "\n" +
                "\n" +
                "tests/test1.py\n" +
                "<module>\n" +
                "1\n" +
                "0\n" +
                "b'\\x06\\x01\\x06\\x01\\x06\\x01\\n\\x01'\n";
        pyCodeObject = PyCodeObject.buildPyCodeObject(new BufferedReader(new StringReader(test)));
        virtualMachine.runCode(pyCodeObject);
        assertEquals("2\n", outContent.toString());
        outContent.reset();

        /*
        x = 3
        while x < 10:
            i = 0
            while i < x:
                 i += 1
            x += 1
        print(x)
         */
        test = "0\n" +
                "0\n" +
                "2\n" +
                "64\n" +
                "100 0 0 90 0 0 120 61 0 101 0 0 100 1 0 107 0 0 114 69 0 100 2 0 90 1 0 120 26 0 101 1 0 101 0 0 " +
                "107 0 0 114 55 0 101 1 0 100 3 0 55 90 1 0 113 30 0 87 101 0 0 100 3 0 55 90 0 0 113 9 0 87 101 2 0 " +
                "101 0 0 131 1 0 1 100 4 0 83\n" +
                "5\n" +
                "3\n" +
                "10\n" +
                "0\n" +
                "1\n" +
                "None\n" +
                "x i print\n" +
                "\n" +
                "\n" +
                "\n" +
                "tests/test1.py\n" +
                "<module>\n" +
                "1\n" +
                "0\n" +
                "b'\\x06\\x01\\x0f\\x01\\x06\\x01\\x0f\\x01\\x0e\\x01\\x0e\\x01'\n";
        pyCodeObject = PyCodeObject.buildPyCodeObject(new BufferedReader(new StringReader(test)));
        virtualMachine.runCode(pyCodeObject);
        assertEquals("10\n", outContent.toString());
        outContent.reset();

        /*
        x = 2
        while True:
            x += 2
            if x == 8:
                 break
        print(x)
         */
        test = "0\n" +
                "0\n" +
                "2\n" +
                "64\n" +
                "100 0 0 90 0 0 120 27 0 101 0 0 100 0 0 55 90 0 0 101 0 0 100 1 0 107 2 0 114 9 0 80 113 9 0 87 101 " +
                "1 0 101 0 0 131 1 0 1 100 2 0 83\n" +
                "3\n" +
                "2\n" +
                "8\n" +
                "None\n" +
                "x print\n" +
                "\n" +
                "\n" +
                "\n" +
                "tests/test1.py\n" +
                "<module>\n" +
                "1\n" +
                "0\n" +
                "b'\\x06\\x01\\x03\\x01\\n\\x01\\x0c\\x01\\x05\\x01'\n";
        pyCodeObject = PyCodeObject.buildPyCodeObject(new BufferedReader(new StringReader(test)));
        virtualMachine.runCode(pyCodeObject);
        assertEquals("8\n", outContent.toString());
        outContent.reset();

        /*
        def get_string():
            return "Hello, World!"

        s = get_string()
        print(s)
         */
        test = "0\n" +
                "0\n" +
                "2\n" +
                "64\n" +
                "100 0 0 100 1 0 132 0 0 90 0 0 101 0 0 131 0 0 90 1 0 101 2 0 101 1 0 131 1 0 1 100 2 0 83\n" +
                "3\n" +
                "SOC\n" +
                "0\n" +
                "0\n" +
                "1\n" +
                "67\n" +
                "100 1 0 83\n" +
                "2\n" +
                "None\n" +
                "'Hello, World!'\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "tests/test1.py\n" +
                "get_string\n" +
                "0\n" +
                "'get_string'\n" +
                "None\n" +
                "get_string s print\n" +
                "\n" +
                "\n" +
                "\n" +
                "tests/test1.py\n" +
                "<module>\n" +
                "0";
        pyCodeObject = PyCodeObject.buildPyCodeObject(new BufferedReader(new StringReader(test)));
        virtualMachine.runCode(pyCodeObject);
        assertEquals("Hello, World!\n", outContent.toString());
        outContent.reset();

        /*
        def sum(a, b):
	        return a + b
        print(sum(1,2))
         */
        test = "0\n" +
                "0\n" +
                "4\n" +
                "64\n" +
                "100 0 0 100 1 0 132 0 0 90 0 0 101 1 0 101 0 0 100 2 0 100 3 0 131 2 0 131 1 0 1 100 4 0 83\n" +
                "5\n" +
                "SOC\n" +
                "2\n" +
                "2\n" +
                "2\n" +
                "67\n" +
                "124 0 0 124 1 0 23 83\n" +
                "1\n" +
                "None\n" +
                "\n" +
                "a b\n" +
                "\n" +
                "\n" +
                "tests/test2.py\n" +
                "sum\n" +
                "0\n" +
                "'sum'\n" +
                "1\n" +
                "2\n" +
                "None\n" +
                "sum print\n" +
                "\n" +
                "\n" +
                "\n" +
                "tests/test2.py\n" +
                "<module>\n" +
                "0";
        pyCodeObject = PyCodeObject.buildPyCodeObject(new BufferedReader(new StringReader(test)));
        virtualMachine.runCode(pyCodeObject);
        assertEquals("3\n", outContent.toString());
        outContent.reset();

        /*
        def sum(a):
            return a + b

        b = 3
        print(sum(2))
         */
        test = "0\n" +
                "0\n" +
                "3\n" +
                "64\n" +
                "100 0 0 100 1 0 132 0 0 90 0 0 100 2 0 90 1 0 101 2 0 101 0 0 100 3 0 131 1 0 131 1 0 1 100 4 0 83\n" +
                "5\n" +
                "SOC\n" +
                "1\n" +
                "1\n" +
                "2\n" +
                "67\n" +
                "124 0 0 116 0 0 23 83\n" +
                "1\n" +
                "None\n" +
                "b\n" +
                "a\n" +
                "\n" +
                "\n" +
                "../JPY_Build/tests/test.py\n" +
                "sum\n" +
                "0\n" +
                "'sum'\n" +
                "3\n" +
                "2\n" +
                "None\n" +
                "sum b print\n" +
                "\n" +
                "\n" +
                "\n" +
                "../JPY_Build/tests/test.py\n" +
                "<module>\n" +
                "0\n";
        pyCodeObject = PyCodeObject.buildPyCodeObject(new BufferedReader(new StringReader(test)));
        virtualMachine.runCode(pyCodeObject);
        assertEquals("5\n", outContent.toString());
        outContent.reset();

        /*
        for i in range(10, 20, 2):
            print(i)
         */
        test = "0\n" +
                "0\n" +
                "4\n" +
                "64\n" +
                "120 36 0 101 0 0 100 0 0 100 1 0 100 2 0 131 3 0 68 93 16 0 90 1 0 101 2 0 101 1 0 131 1 0 1 113 19 0 87 100 3 0 83\n" +
                "4\n" +
                "10\n" +
                "20\n" +
                "2\n" +
                "None\n" +
                "range i print\n" +
                "\n" +
                "\n" +
                "\n" +
                "../test/testfor.py\n" +
                "<module>\n" +
                "0";
        pyCodeObject = PyCodeObject.buildPyCodeObject(new BufferedReader(new StringReader(test)));
        virtualMachine.runCode(pyCodeObject);
        assertEquals("10\n12\n14\n16\n18\n", outContent.toString());
        outContent.reset();
    }
}