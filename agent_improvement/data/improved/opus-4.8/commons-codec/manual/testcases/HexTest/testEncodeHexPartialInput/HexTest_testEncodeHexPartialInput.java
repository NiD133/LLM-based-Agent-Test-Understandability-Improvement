package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#encodeHex(byte[], int, int, boolean)}, the overload that encodes only a
 * sub-range of the input byte array (a "partial" slice defined by an offset and length).
 */
public class HexTest_testEncodeHexPartialInput {

    /** Source bytes for "hello world" in UTF-8; each char encodes to a single byte. */
    private static final byte[] HELLO_WORLD = "hello world".getBytes(StandardCharsets.UTF_8);

    private static final boolean LOWER_CASE = true;
    private static final boolean UPPER_CASE = false;

    @Test
    void testEncodeHexPartialInput() {
        // A zero-length slice produces no hex characters.
        assertArrayEquals(new char[0], Hex.encodeHex(HELLO_WORLD, 0, 0, LOWER_CASE));

        // First byte only ('h' == 0x68). Lower/upper case are identical for these digits.
        assertArrayEquals("68".toCharArray(), Hex.encodeHex(HELLO_WORLD, 0, 1, LOWER_CASE));
        assertArrayEquals("68".toCharArray(), Hex.encodeHex(HELLO_WORLD, 0, 1, UPPER_CASE));

        // Four bytes starting at index 2 ("llo " == 0x6c 0x6c 0x6f 0x20).
        // Here the case flag changes the output: lower-case 'c'/'f' vs upper-case 'C'/'F'.
        assertArrayEquals("6c6c6f20".toCharArray(), Hex.encodeHex(HELLO_WORLD, 2, 4, LOWER_CASE));
        assertArrayEquals("6C6C6F20".toCharArray(), Hex.encodeHex(HELLO_WORLD, 2, 4, UPPER_CASE));

        // Last byte only ('d' == 0x64), reached via offset 10.
        assertArrayEquals("64".toCharArray(), Hex.encodeHex(HELLO_WORLD, 10, 1, LOWER_CASE));
        assertArrayEquals("64".toCharArray(), Hex.encodeHex(HELLO_WORLD, 10, 1, UPPER_CASE));
    }
}
