package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexPartialInput {

    private static final byte[] HELLO_WORLD_BYTES = "hello world".getBytes(StandardCharsets.UTF_8);

    @Test
    void testEncodeHexPartialInput() {
        assertEncodedSlice("", 0, 0, true);
        assertEncodedSlice("68", 0, 1, true);
        assertEncodedSlice("68", 0, 1, false);
        assertEncodedSlice("6c6c6f20", 2, 4, true);
        assertEncodedSlice("6C6C6F20", 2, 4, false);
        assertEncodedSlice("64", 10, 1, true);
        assertEncodedSlice("64", 10, 1, false);
    }

    private void assertEncodedSlice(final String expectedHex, final int offset, final int length,
            final boolean toLowerCase) {
        final char[] actualHex = Hex.encodeHex(HELLO_WORLD_BYTES, offset, length, toLowerCase);

        assertArrayEquals(expectedHex.toCharArray(), actualHex);
    }
}
