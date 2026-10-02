package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferObjectEmpty {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testDecodeByteBufferObjectEmpty() throws DecoderException {
        // Decoding an empty ByteBuffer passed as Object should yield an empty byte array
        ByteBuffer emptyBuffer = allocate(0);
        byte[] result = (byte[]) new Hex().decode((Object) emptyBuffer);
        assertArrayEquals(new byte[0], result);
    }
}
