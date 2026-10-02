package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeByteArrayObjectEmpty {

    @Test
    void testEncodeByteArrayObjectEmpty() throws EncoderException {
        assertArrayEquals(new char[0], (char[]) new Hex().encode((Object) new byte[0]));
    }
}
