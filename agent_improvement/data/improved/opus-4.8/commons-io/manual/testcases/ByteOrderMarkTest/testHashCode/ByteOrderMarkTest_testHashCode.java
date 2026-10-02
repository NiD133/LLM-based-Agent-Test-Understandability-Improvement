package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ByteOrderMark#hashCode()}.
 *
 * <p>By contract, {@code hashCode()} returns the hash code of the
 * {@link ByteOrderMark} class plus the sum of the BOM's bytes:</p>
 * <pre>
 * hashCode == ByteOrderMark.class.hashCode() + (byte[0] + byte[1] + ...)
 * </pre>
 */
public class ByteOrderMarkTest_testHashCode {

    /** A BOM whose single byte sums to 1. */
    private static final ByteOrderMark BOM_WITH_ONE_BYTE = new ByteOrderMark("test1", 1);

    /** A BOM whose two bytes sum to 1 + 2 = 3. */
    private static final ByteOrderMark BOM_WITH_TWO_BYTES = new ByteOrderMark("test2", 1, 2);

    /** A BOM whose three bytes sum to 1 + 2 + 3 = 6. */
    private static final ByteOrderMark BOM_WITH_THREE_BYTES = new ByteOrderMark("test3", 1, 2, 3);

    @Test
    void testHashCode() {
        // The base of every BOM hash code is the hash code of the class itself.
        final int classHashCode = ByteOrderMark.class.hashCode();

        // Each hash code is the class hash plus the sum of that BOM's bytes.
        assertEquals(classHashCode + 1, BOM_WITH_ONE_BYTE.hashCode(), "hash test1 ");
        assertEquals(classHashCode + 3, BOM_WITH_TWO_BYTES.hashCode(), "hash test2 ");
        assertEquals(classHashCode + 6, BOM_WITH_THREE_BYTES.hashCode(), "hash test3 ");
    }
}
