package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that setting (and clearing) the modify / access / create timestamps on an
 * {@link X5455_ExtendedTimestamp} keeps the corresponding "present" flag bits in sync.
 *
 * <p>The flags byte packs three independent bits:</p>
 * <ul>
 *   <li>bit 0 (value 1) - modify time present</li>
 *   <li>bit 1 (value 2) - access time present</li>
 *   <li>bit 2 (value 4) - create time present</li>
 * </ul>
 *
 * <p>Each {@code setXxxJavaTime(Date)} call must turn the matching bit on, and each
 * {@code setXxxJavaTime(null)} call must turn it back off, leaving the other bits untouched.</p>
 */
public class X5455_ExtendedTimestampTest_testBitsAreSetWithTime {

    /** Flag bit value contributed by a present modify time. */
    private static final int MODIFY_BIT = 1;

    /** Flag bit value contributed by a present access time. */
    private static final int ACCESS_BIT = 2;

    /** Flag bit value contributed by a present create time. */
    private static final int CREATE_BIT = 4;

    /** The extended-timestamp field under test. */
    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testBitsAreSetWithTime() {
        // Setting each timestamp turns its bit on, accumulating into the flags byte.
        xf.setModifyJavaTime(new Date(1111));
        assertTrue(xf.isBit0_modifyTimePresent());
        assertEquals(MODIFY_BIT, xf.getFlags());

        xf.setAccessJavaTime(new Date(2222));
        assertTrue(xf.isBit1_accessTimePresent());
        assertEquals(MODIFY_BIT | ACCESS_BIT, xf.getFlags());

        xf.setCreateJavaTime(new Date(3333));
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(MODIFY_BIT | ACCESS_BIT | CREATE_BIT, xf.getFlags());

        // Clearing each timestamp (null) turns its bit off, leaving the others set.
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
