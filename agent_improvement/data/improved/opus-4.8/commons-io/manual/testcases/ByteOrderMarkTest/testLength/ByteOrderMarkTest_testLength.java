package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ByteOrderMark#length()}.
 * <p>
 * {@code length()} should return the number of bytes that were supplied to the
 * constructor, so a BOM built from N bytes is expected to report a length of N.
 * </p>
 */
public class ByteOrderMarkTest_testLength {

    /** A BOM constructed from a single byte; expected length is 1. */
    private static final ByteOrderMark BOM_WITH_ONE_BYTE = new ByteOrderMark("test1", 1);

    /** A BOM constructed from two bytes; expected length is 2. */
    private static final ByteOrderMark BOM_WITH_TWO_BYTES = new ByteOrderMark("test2", 1, 2);

    /** A BOM constructed from three bytes; expected length is 3. */
    private static final ByteOrderMark BOM_WITH_THREE_BYTES = new ByteOrderMark("test3", 1, 2, 3);

    @Test
    void testLength() {
        assertEquals(1, BOM_WITH_ONE_BYTE.length(), "length of a one-byte BOM");
        assertEquals(2, BOM_WITH_TWO_BYTES.length(), "length of a two-byte BOM");
        assertEquals(3, BOM_WITH_THREE_BYTES.length(), "length of a three-byte BOM");
    }
}
