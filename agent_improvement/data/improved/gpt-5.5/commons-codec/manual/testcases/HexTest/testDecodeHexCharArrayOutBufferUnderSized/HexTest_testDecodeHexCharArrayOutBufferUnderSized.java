package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayOutBufferUnderSized {

    @Test
    void testDecodeHexCharArrayOutBufferUnderSized() {
        final byte[] out = new byte[4];

        assertThrows(DecoderException.class, () -> Hex.decodeHex("aabbccddeeff".toCharArray(), out, 0));
    }
}
