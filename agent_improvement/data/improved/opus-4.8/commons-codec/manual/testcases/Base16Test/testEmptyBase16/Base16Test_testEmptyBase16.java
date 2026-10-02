package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Base16} encodes and decodes empty and {@code null} inputs
 * without producing any output.
 */
public class Base16Test_testEmptyBase16 {

    private static final byte[] EMPTY = {};

    @Test
    void testEmptyBase16() {
        // Encoding an empty array yields an empty array.
        assertEquals(0, new Base16().encode(EMPTY).length, "empty Base16 encode");
        // Encoding null yields null.
        assertNull(new Base16().encode(null), "empty Base16 encode");

        // Encoding an empty array with an offset/length still yields an empty array.
        assertEquals(0, new Base16().encode(EMPTY, 0, 1).length, "empty Base16 encode with offset");
        // Encoding null with an offset/length still yields null.
        assertNull(new Base16().encode(null), "empty Base16 encode with offset");

        // Decoding an empty array yields an empty array.
        assertEquals(0, new Base16().decode(EMPTY).length, "empty Base16 decode");
        // Decoding null yields null.
        assertNull(new Base16().decode((byte[]) null), "empty Base16 encode");
    }
}
