package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testHashCode {

    private static final int TEST_BOM_1_BYTE_SUM = 1;
    private static final int TEST_BOM_2_BYTE_SUM = 1 + 2;
    private static final int TEST_BOM_3_BYTE_SUM = 1 + 2 + 3;

    private static final ByteOrderMark TEST_BOM_1 = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark TEST_BOM_2 = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark TEST_BOM_3 = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Tests {@link ByteOrderMark#hashCode()}.
     */
    @Test
    void testHashCode() {
        final int bomClassHash = ByteOrderMark.class.hashCode();

        assertEquals(bomClassHash + TEST_BOM_1_BYTE_SUM, TEST_BOM_1.hashCode(), "hash test1 ");
        assertEquals(bomClassHash + TEST_BOM_2_BYTE_SUM, TEST_BOM_2.hashCode(), "hash test2 ");
        assertEquals(bomClassHash + TEST_BOM_3_BYTE_SUM, TEST_BOM_3.hashCode(), "hash test3 ");
    }
}
