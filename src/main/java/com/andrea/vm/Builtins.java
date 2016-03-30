package com.andrea.vm;


import com.andrea.pyobjects.PyRange;
import org.jetbrains.annotations.Contract;

import java.util.Scanner;

class Builtins {

    private static final String[] builtins = {"ArithmeticError", "AssertionError", "AttributeError", "BaseException",
            "BlockingIOError", "BrokenPipeError", "BufferError", "BytesWarning", "ChildProcessError",
            "ConnectionAbortedError", "ConnectionError", "ConnectionRefusedError", "ConnectionResetError",
            "DeprecationWarning", "EOFError", "Ellipsis", "EnvironmentError", "Exception", "False", "FileExistsError",
            "FileNotFoundError", "FloatingPointError", "FutureWarning", "GeneratorExit", "IOError", "ImportError",
            "ImportWarning", "IndentationError", "IndexError", "InterruptedError", "IsADirectoryError", "KeyError",
            "KeyboardInterrupt", "LookupError", "MemoryError", "NameError", "None", "NotADirectoryError",
            "NotImplemented", "NotImplementedError", "OSError", "OverflowError", "PendingDeprecationWarning",
            "PermissionError", "ProcessLookupError", "RecursionError", "ReferenceError", "ResourceWarning",
            "RuntimeError", "RuntimeWarning", "StopAsyncIteration", "StopIteration", "SyntaxError", "SyntaxWarning",
            "SystemError", "SystemExit", "TabError", "TimeoutError", "True", "TypeError", "UnboundLocalError",
            "UnicodeDecodeError", "UnicodeEncodeError", "UnicodeError", "UnicodeTranslateError", "UnicodeWarning",
            "UserWarning", "ValueError", "Warning", "ZeroDivisionError", "_", "__build_class__", "__debug__", "__doc__",
            "__import__", "__loader__", "__name__", "__package__", "__spec__", "abs", "all", "any", "ascii", "bin",
            "bool", "bytearray", "bytes", "callable", "chr", "classmethod", "compile", "complex", "copyright",
            "credits", "delattr", "dict", "dir", "divmod", "enumerate", "eval", "exec", "exit", "filter", "float",
            "format", "frozenset", "getattr", "globals", "hasattr", "hash", "help", "hex", "id", "input", "int",
            "isinstance", "issubclass", "iter", "len", "license", "list", "locals", "map", "max", "memoryview", "min",
            "next", "object", "oct", "open", "ord", "pow", "print", "property", "quit", "range", "repr", "reversed",
            "round", "set", "setattr", "slice", "sorted", "staticmethod", "str", "sum", "super", "tuple", "type",
            "vars", "zip"};

    @Contract(pure = true)
    static boolean isBuiltin(String st) {
        for (String s : builtins)
            if (st.equals(s))
                return true;
        return false;
    }

    static Object call(String functionName, Object[] posParams) {
        switch (functionName) {
            case "input":
                Scanner scanner = new Scanner(System.in);
                return scanner.nextLine();
            case "print":
                System.out.println(posParams[0]);
                return null;
            case "range":
                if (posParams.length == 1)
                    return new PyRange(0, (long) posParams[0], 0);
                else if (posParams.length == 2)
                    return new PyRange((long) posParams[1], (long) posParams[0], 0);
                else
                    return new PyRange((long) posParams[2], (long) posParams[1], (long) posParams[0]);
            default:
                throw new UnsupportedOperationException();
        }
    }
}
