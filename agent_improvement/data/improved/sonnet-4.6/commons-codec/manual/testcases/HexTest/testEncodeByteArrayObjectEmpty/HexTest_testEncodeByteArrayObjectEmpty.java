package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeByteArrayObjectEmpty {

    @Test
    void testEncodeByteArrayObjectEmpty() throws EncoderException {
        Hex hex = new Hex();
        byte[] emptyInput = new byte[0];

        // encode(Object) returns char[] when given a byte[]
        char[] result = (char[]) hex.encode((Object) emptyInput);

        assertArrayEquals(new char[0], result);
    }
}
