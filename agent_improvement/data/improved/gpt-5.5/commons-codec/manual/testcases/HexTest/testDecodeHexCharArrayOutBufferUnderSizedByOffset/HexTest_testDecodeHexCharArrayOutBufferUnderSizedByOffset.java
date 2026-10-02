package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayOutBufferUnderSizedByOffset {

    @Test
    void testDecodeHexCharArrayOutBufferUnderSizedByOffset() {
        final char[] sixDecodedBytes = "aabbccddeeff".toCharArray();
        final byte[] out = new byte[6];

        assertThrows(DecoderException.class, () -> Hex.decodeHex(sixDecodedBytes, out, 1));
    }
}
