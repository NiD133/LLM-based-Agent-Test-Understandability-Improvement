package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testHasSpace {

    private static final int ONE_BYTE_CAPACITY = 1;
    private static final byte FIRST_BYTE = 1;
    private static final byte SECOND_BYTE = 2;

    @Test
    void testHasSpace() {
        final CircularByteBuffer buffer = new CircularByteBuffer(ONE_BYTE_CAPACITY);
        assertTrue(buffer.hasSpace());

        buffer.add(FIRST_BYTE);
        assertFalse(buffer.hasSpace());
        assertEquals(FIRST_BYTE, buffer.read());
        assertTrue(buffer.hasSpace());

        buffer.add(SECOND_BYTE);
        assertFalse(buffer.hasSpace());
        assertEquals(SECOND_BYTE, buffer.read());
        assertTrue(buffer.hasSpace());
    }
}
