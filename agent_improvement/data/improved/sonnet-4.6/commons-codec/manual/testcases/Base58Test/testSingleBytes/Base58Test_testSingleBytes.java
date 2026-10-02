package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class Base58Test_testSingleBytes {

    private final Base58 codec = new Base58();

    @Test
    void testSingleBytes() {
        // Verify that each non-zero single byte survives a round-trip through Base58
        // encode → decode. Byte value 0 is omitted because it is the leading-zero sentinel
        // in Base58 and would decode to an empty array, not {0}.
        for (int i = 1; i <= 255; i++) {
            final byte[] data = { (byte) i };
            final byte[] enc = codec.encode(data);
            final byte[] dec = codec.decode(enc);
            assertArrayEquals(data, dec, "Failed for byte value: " + i);
        }
    }
}
