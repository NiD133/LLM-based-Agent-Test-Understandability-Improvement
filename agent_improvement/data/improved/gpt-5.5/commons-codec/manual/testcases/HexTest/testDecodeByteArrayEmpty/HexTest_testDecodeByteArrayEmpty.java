package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteArrayEmpty {

    @Test
    void testDecodeByteArrayEmpty() throws DecoderException {
        final byte[] emptyHexInput = new byte[0];
        final byte[] expectedDecodedBytes = new byte[0];

        assertArrayEquals(expectedDecodedBytes, new Hex().decode(emptyHexInput));
    }
}
