package com.andrea.utils;

import org.jetbrains.annotations.Contract;

public class Utils {

    @Contract(pure = true)
    public static int twoBytesToInt(byte b1, byte b2) {
        return ((b2 & 0xff) << 8) | (b1 & 0xff);
    }

}
