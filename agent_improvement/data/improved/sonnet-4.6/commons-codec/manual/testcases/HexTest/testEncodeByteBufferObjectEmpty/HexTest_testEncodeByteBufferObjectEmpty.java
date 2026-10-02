package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeByteBufferObjectEmpty {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeByteBufferObjectEmpty() throws EncoderException {
        // Encoding an empty ByteBuffer passed as Object should produce an empty char array
        ByteBuffer emptyBuffer = allocate(0);
        char[] result = (char[]) new Hex().encode((Object) emptyBuffer);
        assertArrayEquals(new char[0], result);
    }
}
