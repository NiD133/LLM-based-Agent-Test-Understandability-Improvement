package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link X5455_ExtendedTimestamp} keeps its {@code flags} byte in sync with the three
 * timestamps it can hold. Each timestamp owns one bit in the flags byte:
 *
 * <ul>
 *   <li>modify time -&gt; bit 0 (value 1)</li>
 *   <li>access time -&gt; bit 1 (value 2)</li>
 *   <li>create time -&gt; bit 2 (value 4)</li>
 * </ul>
 *
 * Setting a timestamp turns its bit on; clearing it (passing {@code null}) turns its bit off.
 */
public class X5455_ExtendedTimestampTest_testBitsAreSetWithTime {

    /** Bit contributed to the flags byte by the modify timestamp. */
    private static final int MODIFY_BIT = 1;

    /** Bit contributed to the flags byte by the access timestamp. */
    private static final int ACCESS_BIT = 2;

    /** Bit contributed to the flags byte by the create timestamp. */
    private static final int CREATE_BIT = 4;

    /** The extended timestamp field under test. */
    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testBitsAreSetWithTime() {
        // Setting each timestamp turns its dedicated bit on, so the flags accumulate 1 -> 3 -> 7.
        xf.setModifyJavaTime(new Date(1111));
        assertTrue(xf.isBit0_modifyTimePresent());
        assertEquals(MODIFY_BIT, xf.getFlags());

        xf.setAccessJavaTime(new Date(2222));
        assertTrue(xf.isBit1_accessTimePresent());
        assertEquals(MODIFY_BIT | ACCESS_BIT, xf.getFlags());

        xf.setCreateJavaTime(new Date(3333));
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(MODIFY_BIT | ACCESS_BIT | CREATE_BIT, xf.getFlags());

        // Clearing each timestamp (null) turns its bit back off, so the flags drain 6 -> 4 -> 0.
        xf.setModifyJavaTime(null);
        assertFalse(xf.isBit0_modifyTimePresent());
        assertEquals(ACCESS_BIT | CREATE_BIT, xf.getFlags());

        xf.setAccessJavaTime(null);
        assertFalse(xf.isBit1_accessTimePresent());
        assertEquals(CREATE_BIT, xf.getFlags());

        xf.setCreateJavaTime(null);
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(0, xf.getFlags());
    }
}
