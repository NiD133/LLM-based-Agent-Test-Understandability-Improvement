package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class Base16Test_testEmptyBase16 {

    /**
     * Test encode and decode of empty byte array.
     */
    @Test
    void testEmptyBase16() {
        byte[] empty = {};

        byte[] result = new Base16().encode(empty);
        assertEquals(0, result.length, "empty Base16 encode");
        assertNull(new Base16().encode(null), "empty Base16 encode");

        result = new Base16().encode(empty, 0, 1);
        assertEquals(0, result.length, "empty Base16 encode with offset");
        assertNull(new Base16().encode(null), "empty Base16 encode with offset");

        empty = new byte[0];

        result = new Base16().decode(empty);
        assertEquals(0, result.length, "empty Base16 decode");
        assertNull(new Base16().decode((byte[]) null), "empty Base16 encode");
    }
}
