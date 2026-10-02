package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayOutBufferUnderSized {

    /**
     * "aabbccddeeff" encodes 6 bytes (12 hex chars / 2), but the output buffer
     * holds only 4 bytes, so decodeHex must throw DecoderException.
     */
    @Test
    void testDecodeHexCharArrayOutBufferUnderSized() {
        final char[] hexInput = "aabbccddeeff".toCharArray(); // requires 6-byte output
        final byte[] undersizedOut = new byte[4];             // only 4 bytes available

        assertThrows(DecoderException.class, () -> Hex.decodeHex(hexInput, undersizedOut, 0));
    }
}
