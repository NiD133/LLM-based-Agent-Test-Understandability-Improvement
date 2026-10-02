package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ByteOrderMark#toString()}.
 *
 * <p>{@code toString()} is expected to render a BOM as
 * {@code ByteOrderMark[<charsetName>: <hex bytes>]}, where each byte is
 * formatted as an upper-case hexadecimal value prefixed with {@code 0x} and
 * multiple bytes are separated by commas.</p>
 */
public class ByteOrderMarkTest_testToString {

    /** A BOM with a single byte (0x1). */
    private static final ByteOrderMark SINGLE_BYTE_BOM = new ByteOrderMark("test1", 1);

    /** A BOM with two bytes (0x1, 0x2). */
    private static final ByteOrderMark TWO_BYTE_BOM = new ByteOrderMark("test2", 1, 2);

    /** A BOM with three bytes (0x1, 0x2, 0x3). */
    private static final ByteOrderMark THREE_BYTE_BOM = new ByteOrderMark("test3", 1, 2, 3);

    @Test
    void testToString() {
        // A single-byte BOM lists just that byte.
        assertEquals("ByteOrderMark[test1: 0x1]", SINGLE_BYTE_BOM.toString(),
                "single-byte BOM");

        // A two-byte BOM lists its bytes separated by a comma.
        assertEquals("ByteOrderMark[test2: 0x1,0x2]", TWO_BYTE_BOM.toString(),
                "two-byte BOM");

        // A three-byte BOM lists all three bytes separated by commas.
        assertEquals("ByteOrderMark[test3: 0x1,0x2,0x3]", THREE_BYTE_BOM.toString(),
                "three-byte BOM");
    }
}
