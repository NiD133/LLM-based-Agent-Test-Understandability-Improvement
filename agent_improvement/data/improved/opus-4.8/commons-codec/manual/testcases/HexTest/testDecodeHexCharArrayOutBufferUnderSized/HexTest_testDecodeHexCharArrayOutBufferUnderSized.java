package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Hex#decodeHex(char[], byte[], int)} rejects an output buffer
 * that is too small to hold the decoded bytes.
 */
public class HexTest_testDecodeHexCharArrayOutBufferUnderSized {

    @Test
    void testDecodeHexCharArrayOutBufferUnderSized() {
        // "aabbccddeeff" is 12 hex characters, which decode to 6 bytes.
        final char[] hexInput = "aabbccddeeff".toCharArray();

        // The output buffer only has room for 4 bytes, so decoding cannot fit.
        final byte[] undersizedOut = new byte[4];

        // Decoding into the too-small buffer must fail with a DecoderException.
        assertThrows(DecoderException.class,
                () -> Hex.decodeHex(hexInput, undersizedOut, 0));
    }
}
