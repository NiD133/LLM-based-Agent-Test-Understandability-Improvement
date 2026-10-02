package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class Base58Test_testEmptyBase58 {

    @Test
    void testEmptyBase58() {
        byte[] emptyInput = {};
        byte[] encoded = new Base58().encode(emptyInput);
        assertEquals(0, encoded.length, "empty Base58 encode");
        assertNull(new Base58().encode(null), "empty Base58 encode");

        emptyInput = new byte[0];
        byte[] decoded = new Base58().decode(emptyInput);
        assertEquals(0, decoded.length, "empty Base58 decode");
        assertNull(new Base58().decode((byte[]) null), "empty Base58 decode");
    }
}
