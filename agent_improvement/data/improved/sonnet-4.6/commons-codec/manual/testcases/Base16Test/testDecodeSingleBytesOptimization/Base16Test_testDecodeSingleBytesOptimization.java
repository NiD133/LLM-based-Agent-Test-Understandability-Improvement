package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class Base16Test_testDecodeSingleBytesOptimization {

    /**
     * Verifies that when decode() is called one byte at a time, the codec correctly
     * buffers the first hex nibble in ibitWorkArea (stored as value+1 to distinguish
     * from the empty-value 0) and only emits a decoded byte once both nibbles of a
     * hex pair have been received.
     *
     * The optimization path is triggered when exactly one hex character is available
     * in the current call and there is no previously buffered nibble, so the codec
     * defers allocation and output until the second nibble arrives.
     */
    @Test
    void testDecodeSingleBytesOptimization() {
        final BaseNCodec.Context context = new BaseNCodec.Context();
        // Initial state: no nibble buffered, no output buffer allocated yet
        assertEquals(0, context.ibitWorkArea);
        assertNull(context.buffer);

        final Base16 b16 = new Base16();
        final byte[] singleByte = new byte[1];

        // Feed the high nibble 'E' (decimal 14).
        // The optimization stores it as 14+1=15 in ibitWorkArea and skips buffer allocation.
        singleByte[0] = (byte) 'E';
        b16.decode(singleByte, 0, 1, context);
        assertEquals(15, context.ibitWorkArea); // 'E'=14, stored as 14+1=15
        assertNull(context.buffer);             // no output yet; buffer still unallocated

        // Feed the low nibble 'F' (decimal 15).
        // Now both nibbles are available: the codec combines them into byte 0xEF and resets ibitWorkArea.
        singleByte[0] = (byte) 'F';
        b16.decode(singleByte, 0, 1, context);
        assertEquals(0, context.ibitWorkArea);      // work area reset after completing the hex pair
        assertEquals((byte) 0xEF, context.buffer[0]); // "EF" decoded to byte value 0xEF
    }
}
