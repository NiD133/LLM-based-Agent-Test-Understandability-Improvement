package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteArrayObjectEmpty {

    @Test
    void testDecodeByteArrayObjectEmpty() throws DecoderException {
        assertArrayEquals(new byte[0], (byte[]) new Hex().decode((Object) new byte[0]));
    }
}
