package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteArrayObjectEmpty {

    @Test
    void testDecodeByteArrayObjectEmpty() throws DecoderException {
        // Passing an empty byte[] as Object should decode to an empty byte array
        Object emptyByteArray = new byte[0];
        byte[] result = (byte[]) new Hex().decode(emptyByteArray);
        assertArrayEquals(new byte[0], result);
    }
}
