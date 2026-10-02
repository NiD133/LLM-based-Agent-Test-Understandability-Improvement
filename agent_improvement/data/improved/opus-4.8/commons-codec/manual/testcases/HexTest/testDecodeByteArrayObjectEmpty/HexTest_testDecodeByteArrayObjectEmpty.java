package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hex#decode(Object)} returns an empty byte array when given
 * an empty byte array (passed as an {@link Object}).
 */
public class HexTest_testDecodeByteArrayObjectEmpty {

    @Test
    void testDecodeByteArrayObjectEmpty() throws DecoderException {
        final byte[] emptyInput = new byte[0];

        final byte[] decoded = (byte[]) new Hex().decode((Object) emptyInput);

        assertArrayEquals(new byte[0], decoded);
    }
}
