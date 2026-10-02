package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexPartialInput {

    /**
     * Verifies that encodeHex correctly encodes a sub-range of a byte array,
     * using both lowercase and uppercase hex output, and at different offsets.
     *
     * "hello world" as UTF-8 bytes:
     *   index: 0='h'(68), 1='e'(65), 2='l'(6c), 3='l'(6c),
     *          4='o'(6f), 5=' '(20), 6='w'(77), 7='o'(6f),
     *          8='r'(72), 9='l'(6c), 10='d'(64)
     */
    @Test
    void testEncodeHexPartialInput() {
        final byte[] data = "hello world".getBytes(StandardCharsets.UTF_8);

        // Encoding zero bytes always produces an empty result
        char[] hex = Hex.encodeHex(data, 0, 0, true);
        assertArrayEquals(new char[0], hex);

        // Encoding 1 byte at offset 0 → 'h' = 0x68
        hex = Hex.encodeHex(data, 0, 1, true);
        assertArrayEquals("68".toCharArray(), hex);

        hex = Hex.encodeHex(data, 0, 1, false);
        assertArrayEquals("68".toCharArray(), hex);  // '68' has no alphabetic digits, so case is irrelevant

        // Encoding 4 bytes starting at offset 2 → "ll o" = 0x6c 0x6c 0x6f 0x20
        hex = Hex.encodeHex(data, 2, 4, true);
        assertArrayEquals("6c6c6f20".toCharArray(), hex);

        hex = Hex.encodeHex(data, 2, 4, false);
        assertArrayEquals("6C6C6F20".toCharArray(), hex);

        // Encoding 1 byte at offset 10 (last char) → 'd' = 0x64
        hex = Hex.encodeHex(data, 10, 1, true);
        assertArrayEquals("64".toCharArray(), hex);

        hex = Hex.encodeHex(data, 10, 1, false);
        assertArrayEquals("64".toCharArray(), hex);  // '64' has no alphabetic digits, so case is irrelevant
    }
}
