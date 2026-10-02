package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#encode(Object)} when given an empty {@link ByteBuffer}.
 */
public class HexTest_testEncodeByteBufferObjectEmpty {

    /**
     * Encoding an empty ByteBuffer through the {@code Object} overload of
     * {@link Hex#encode(Object)} should yield an empty {@code char[]}, since an
     * empty input contains no bytes to convert into hexadecimal characters.
     */
    @Test
    void testEncodeByteBufferObjectEmpty() throws EncoderException {
        final ByteBuffer emptyBuffer = ByteBuffer.allocate(0);

        final char[] encoded = (char[]) new Hex().encode((Object) emptyBuffer);

        assertArrayEquals(new char[0], encoded);
    }
}
