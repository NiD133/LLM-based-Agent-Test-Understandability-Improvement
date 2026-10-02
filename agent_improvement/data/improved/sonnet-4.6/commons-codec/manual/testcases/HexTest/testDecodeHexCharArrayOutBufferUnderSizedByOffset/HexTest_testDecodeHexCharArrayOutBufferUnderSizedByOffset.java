package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayOutBufferUnderSizedByOffset {

    // "aabbccddeeff" decodes to 6 bytes (aa, bb, cc, dd, ee, ff)
    private static final String HEX_STRING_6_BYTES = "aabbccddeeff";

    // Buffer of 6 bytes starting at offset 1 leaves only 5 slots — too few for 6 decoded bytes
    private static final int OUTPUT_BUFFER_SIZE = 6;
    private static final int OUTPUT_OFFSET = 1;

    @Test
    void testDecodeHexCharArrayOutBufferUnderSizedByOffset() {
        // The hex string decodes to 6 bytes, but with outOffset=1 only 5 slots remain
        // in the 6-element output array, so decodeHex must throw DecoderException
        final byte[] out = new byte[OUTPUT_BUFFER_SIZE];
        assertThrows(DecoderException.class,
                () -> Hex.decodeHex(HEX_STRING_6_BYTES.toCharArray(), out, OUTPUT_OFFSET));
    }
}
