package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that decoding an empty {@link ByteBuffer} through the generic
 * {@link Hex#decode(Object)} entry point yields an empty byte array.
 */
public class HexTest_testDecodeByteBufferObjectEmpty {

    @Test
    void testDecodeByteBufferObjectEmpty() throws DecoderException {
        // An empty buffer holds no hex characters, so decoding produces no bytes.
        final ByteBuffer emptyBuffer = ByteBuffer.allocate(0);

        final byte[] decoded = (byte[]) new Hex().decode((Object) emptyBuffer);

        assertArrayEquals(new byte[0], decoded);
    }
}
