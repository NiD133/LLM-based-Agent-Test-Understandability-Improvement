package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferWithLimit {

    private static final int DECODED_BYTES_PER_WINDOW = 2;
    private static final int HEX_CHARS_PER_BYTE = 2;

    /**
     * Allocate a ByteBuffer.
     *
     * <p>The default implementation uses {@link ByteBuffer#allocate(int)}.
     * The method is overridden in AllocateDirectHexTest to use
     * {@link ByteBuffer#allocateDirect(int)}
     *
     * @param capacity the capacity
     * @return the byte buffer
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    /**
     * Encodes the given string into a byte buffer using the UTF-8 charset.
     *
     * <p>The buffer is allocated using {@link #allocate(int)}.
     *
     * @param string the String to encode
     * @return the byte buffer
     */
    private ByteBuffer getByteBufferUtf8(final String string) {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer bb = allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        return bb;
    }

    @Test
    void testDecodeByteBufferWithLimit() throws DecoderException {
        final ByteBuffer bb = getByteBufferUtf8("000102030405060708090a0b0c0d0e0f");
        final byte[] expected = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

        for (int i = 0; i < 15; i++) {
            final int windowStart = i * HEX_CHARS_PER_BYTE;
            final int windowLimit = windowStart + DECODED_BYTES_PER_WINDOW * HEX_CHARS_PER_BYTE;

            bb.position(windowStart);
            bb.limit(windowLimit);

            final byte[] expectedWindow = Arrays.copyOfRange(expected, i, i + DECODED_BYTES_PER_WINDOW);
            assertEquals(new String(expectedWindow), new String(new Hex().decode(bb)));
            assertEquals(0, bb.remaining());
        }
    }
}
