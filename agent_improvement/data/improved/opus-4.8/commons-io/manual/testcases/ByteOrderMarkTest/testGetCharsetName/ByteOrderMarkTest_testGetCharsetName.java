package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ByteOrderMark#getCharsetName()}.
 *
 * <p>Verifies that {@code getCharsetName()} returns exactly the charset name
 * that was supplied to the constructor, regardless of how many BOM bytes the
 * instance was created with.</p>
 */
public class ByteOrderMarkTest_testGetCharsetName {

    /** A BOM defined with a single byte. */
    private static final ByteOrderMark BOM_WITH_ONE_BYTE = new ByteOrderMark("test1", 1);

    /** A BOM defined with two bytes. */
    private static final ByteOrderMark BOM_WITH_TWO_BYTES = new ByteOrderMark("test2", 1, 2);

    /** A BOM defined with three bytes. */
    private static final ByteOrderMark BOM_WITH_THREE_BYTES = new ByteOrderMark("test3", 1, 2, 3);

    @Test
    void testGetCharsetName() {
        assertEquals("test1", BOM_WITH_ONE_BYTE.getCharsetName(),
            "getCharsetName() should return the name passed to the constructor");
        assertEquals("test2", BOM_WITH_TWO_BYTES.getCharsetName(),
            "getCharsetName() should return the name passed to the constructor");
        assertEquals("test3", BOM_WITH_THREE_BYTES.getCharsetName(),
            "getCharsetName() should return the name passed to the constructor");
    }
}
