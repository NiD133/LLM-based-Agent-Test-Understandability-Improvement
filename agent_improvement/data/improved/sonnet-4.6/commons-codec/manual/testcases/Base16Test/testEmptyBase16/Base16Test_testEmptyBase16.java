package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class Base16Test_testEmptyBase16 {

    /**
     * Tests that encoding and decoding empty and null byte arrays produce
     * the expected results: an empty input yields an empty output, and a
     * null input yields null.
     */
    @Test
    void testEmptyBase16() {
        final Base16 codec = new Base16();
        final byte[] empty = new byte[0];

        // Encoding an empty byte array should produce an empty byte array
        byte[] result = codec.encode(empty);
        assertEquals(0, result.length, "empty Base16 encode");

        // Encoding null should return null
        assertNull(codec.encode(null), "null Base16 encode");

        // Encoding an empty byte array with offset=0 and length=1 should still produce an empty byte array
        result = codec.encode(empty, 0, 1);
        assertEquals(0, result.length, "empty Base16 encode with offset");

        // Encoding null via the offset-variant should return null
        assertNull(codec.encode(null), "null Base16 encode with offset");

        // Decoding an empty byte array should produce an empty byte array
        result = codec.decode(empty);
        assertEquals(0, result.length, "empty Base16 decode");

        // Decoding null should return null
        assertNull(codec.decode((byte[]) null), "null Base16 decode");
    }
}
