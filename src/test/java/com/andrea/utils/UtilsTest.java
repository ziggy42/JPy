package com.andrea.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void twoBytesToInt() throws Exception {
        assertEquals(0, Utils.twoBytesToInt((byte) 0, (byte) 0));
        assertEquals(26, Utils.twoBytesToInt((byte) 26, (byte) 0));
        assertEquals(7734, Utils.twoBytesToInt((byte) 54, (byte) 30));
    }

}