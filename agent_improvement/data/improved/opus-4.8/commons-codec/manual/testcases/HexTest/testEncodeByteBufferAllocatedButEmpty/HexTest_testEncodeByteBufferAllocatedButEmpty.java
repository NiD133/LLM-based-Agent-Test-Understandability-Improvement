package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link Hex#encode(ByteBuffer)} behaves when given a buffer that has
 * capacity but no readable content (i.e. {@code remaining() == 0}).
 */
public class HexTest_testEncodeByteBufferAllocatedButEmpty {

    /** No data is written before reading, so the buffer encodes to nothing. */
    private static final byte[] EXPECTED_EMPTY_ENCODING = new byte[0];

    @Test
    void testEncodeByteBufferAllocatedButEmpty() {
        // Allocate a buffer with capacity, then flip it without writing anything.
        // flip() sets the limit to the current position (0), making remaining() == 0,
        // so the buffer is effectively empty even though it has capacity.
        final ByteBuffer emptyBuffer = ByteBuffer.allocate(10);
        emptyBuffer.flip();

        final byte[] encoded = new Hex().encode(emptyBuffer);

        assertArrayEquals(EXPECTED_EMPTY_ENCODING, encoded);
        // Encoding consumes all remaining bytes; here there were none to begin with.
        assertEquals(0, emptyBuffer.remaining());
    }
}
