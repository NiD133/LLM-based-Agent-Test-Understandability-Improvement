package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class Base58Test_testEmptyBase58 {

    @Test
    void testEmptyBase58() {
        Base58 codec = new Base58();

        // Encoding an empty byte array should return an empty byte array
        byte[] emptyInput = new byte[0];
        byte[] encodedResult = codec.encode(emptyInput);
        assertEquals(0, encodedResult.length, "empty Base58 encode");

        // Encoding null should return null
        assertNull(codec.encode(null), "empty Base58 encode");

        // Decoding an empty byte array should return an empty byte array
        byte[] decodedResult = codec.decode(emptyInput);
        assertEquals(0, decodedResult.length, "empty Base58 decode");

        // Decoding null should return null
        assertNull(codec.decode((byte[]) null), "empty Base58 decode");
    }
}
