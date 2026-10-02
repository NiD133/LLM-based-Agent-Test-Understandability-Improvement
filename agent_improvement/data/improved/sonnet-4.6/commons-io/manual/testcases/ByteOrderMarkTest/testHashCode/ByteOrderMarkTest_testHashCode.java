package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testHashCode {

    // Byte values are chosen so their sum equals a predictable offset above the
    // class hash baseline: {1} -> sum=1, {1,2} -> sum=3, {1,2,3} -> sum=6.
    private static final ByteOrderMark BOM_SINGLE_BYTE  = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark BOM_TWO_BYTES    = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark BOM_THREE_BYTES  = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Tests {@link ByteOrderMark#hashCode()}.
     *
     * The implementation computes {@code classHash + sum(bytes)}, where
     * {@code classHash} is {@code ByteOrderMark.class.hashCode()}.
     * Expected offsets: 1 (= 1), 3 (= 1+2), 6 (= 1+2+3).
     */
    @Test
    void testHashCode() {
        final int classHashBase = ByteOrderMark.class.hashCode();

        // bytes {1}: byte sum = 1
        assertEquals(classHashBase + 1, BOM_SINGLE_BYTE.hashCode(),
                "hashCode for BOM with bytes {1} should equal classHash + 1");

        // bytes {1, 2}: byte sum = 1 + 2 = 3
        assertEquals(classHashBase + 3, BOM_TWO_BYTES.hashCode(),
                "hashCode for BOM with bytes {1, 2} should equal classHash + 3");

        // bytes {1, 2, 3}: byte sum = 1 + 2 + 3 = 6
        assertEquals(classHashBase + 6, BOM_THREE_BYTES.hashCode(),
                "hashCode for BOM with bytes {1, 2, 3} should equal classHash + 6");
    }
}
