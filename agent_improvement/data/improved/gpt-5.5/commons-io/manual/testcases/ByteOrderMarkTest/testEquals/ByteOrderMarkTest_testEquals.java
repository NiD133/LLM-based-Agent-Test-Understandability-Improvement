package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testEquals {

    private static final ByteOrderMark TEST_BOM_1 = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark TEST_BOM_2 = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark TEST_BOM_3 = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Tests {@link ByteOrderMark#equals(Object)}.
     */
    @SuppressWarnings("EqualsWithItself")
    @Test
    void testEquals() {
        assertStandardBomConstantsEqualThemselves();
        assertUtf8DiffersFromOtherStandardBomConstants();
        assertCustomBomEqualityUsesOnlyTheByteSequence();
    }

    private void assertStandardBomConstantsEqualThemselves() {
        assertEquals(ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16BE);
        assertEquals(ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_16LE);
        assertEquals(ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32BE);
        assertEquals(ByteOrderMark.UTF_32LE, ByteOrderMark.UTF_32LE);
        assertEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_8);
    }

    private void assertUtf8DiffersFromOtherStandardBomConstants() {
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_16LE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_32BE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_32LE);
    }

    private void assertCustomBomEqualityUsesOnlyTheByteSequence() {
        assertEquals(TEST_BOM_1, TEST_BOM_1, "test1 equals");
        assertEquals(TEST_BOM_2, TEST_BOM_2, "test2 equals");
        assertEquals(TEST_BOM_3, TEST_BOM_3, "test3 equals");
        assertNotEquals(TEST_BOM_1, new Object(), "Object not equal");
        assertNotEquals(TEST_BOM_1, new ByteOrderMark("1a", 2), "test1-1 not equal");
        assertNotEquals(TEST_BOM_1, new ByteOrderMark("1b", 1, 2), "test1-2 not test2");
        assertNotEquals(TEST_BOM_2, new ByteOrderMark("2", 1, 1), "test2 not equal");
        assertNotEquals(TEST_BOM_3, new ByteOrderMark("3", 1, 2, 4), "test3 not equal");
    }
}
