package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testEquals {

    // Custom BOMs used across multiple tests: 1-byte, 2-byte, and 3-byte variants
    private static final ByteOrderMark TEST_BOM_1 = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark TEST_BOM_2 = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark TEST_BOM_3 = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Each standard BOM constant must be equal to itself (reflexivity).
     */
    @SuppressWarnings("EqualsWithItself")
    @Test
    void testEquals_standardBomIsEqualToItself() {
        assertEquals(ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16BE);
        assertEquals(ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_16LE);
        assertEquals(ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32BE);
        assertEquals(ByteOrderMark.UTF_32LE, ByteOrderMark.UTF_32LE);
        assertEquals(ByteOrderMark.UTF_8,    ByteOrderMark.UTF_8);
    }

    /**
     * UTF-8 BOM must not equal any of the UTF-16/32 BOMs because their byte
     * sequences are different.
     */
    @Test
    void testEquals_utf8BomIsNotEqualToOtherStandardBoms() {
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_16LE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_32BE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_32LE);
    }

    /**
     * Each custom BOM must be equal to itself (reflexivity).
     */
    @SuppressWarnings("EqualsWithItself")
    @Test
    void testEquals_customBomIsEqualToItself() {
        assertEquals(TEST_BOM_1, TEST_BOM_1, "test1 equals");
        assertEquals(TEST_BOM_2, TEST_BOM_2, "test2 equals");
        assertEquals(TEST_BOM_3, TEST_BOM_3, "test3 equals");
    }

    /**
     * A BOM must not equal a plain Object — equals() must check the type before
     * comparing bytes.
     */
    @Test
    void testEquals_bomIsNotEqualToArbitraryObject() {
        assertNotEquals(TEST_BOM_1, new Object(), "Object not equal");
    }

    /**
     * Two 1-byte BOMs with different byte values must not be equal.
     */
    @Test
    void testEquals_singleByteBoms_withDifferentBytes_areNotEqual() {
        assertNotEquals(TEST_BOM_1, new ByteOrderMark("1a", 2), "test1-1 not equal");
    }

    /**
     * A 1-byte BOM and a 2-byte BOM must not be equal even when the first byte
     * matches — length alone causes inequality.
     */
    @Test
    void testEquals_bomsWithDifferentLengths_areNotEqual() {
        assertNotEquals(TEST_BOM_1, new ByteOrderMark("1b", 1, 2), "test1-2 not test2");
    }

    /**
     * Two 2-byte BOMs that share the first byte but differ in the second must
     * not be equal.
     */
    @Test
    void testEquals_twoByteBoms_withDifferentSecondByte_areNotEqual() {
        assertNotEquals(TEST_BOM_2, new ByteOrderMark("2", 1, 1), "test2 not equal");
    }

    /**
     * Two 3-byte BOMs that share the first two bytes but differ in the third
     * must not be equal.
     */
    @Test
    void testEquals_threeByteBoms_withDifferentThirdByte_areNotEqual() {
        assertNotEquals(TEST_BOM_3, new ByteOrderMark("3", 1, 2, 4), "test3 not equal");
    }
}
