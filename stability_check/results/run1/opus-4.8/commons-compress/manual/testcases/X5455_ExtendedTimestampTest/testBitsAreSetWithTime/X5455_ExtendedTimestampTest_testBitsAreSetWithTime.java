package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that setting or clearing each Java-time value on an
 * {@link X5455_ExtendedTimestamp} toggles the matching presence bit and updates
 * the combined flags byte accordingly.
 *
 * <p>The flags byte packs three independent bits:</p>
 * <ul>
 *   <li>bit 0 (value 1) - modify time present</li>
 *   <li>bit 1 (value 2) - access time present</li>
 *   <li>bit 2 (value 4) - create time present</li>
 * </ul>
 */
public class X5455_ExtendedTimestampTest_testBitsAreSetWithTime {

    /** Flag value with only the modify-time bit set. */
    private static final int MODIFY = 1;

    /** Flag value with only the access-time bit set. */
    private static final int ACCESS = 2;

    /** Flag value with only the create-time bit set. */
    private static final int CREATE = 4;

    /** The extended timestamp field under test. */
    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testBitsAreSetWithTime() {
        // Setting a time turns its bit on; the flags byte accumulates the bits.
        xf.setModifyJavaTime(new Date(1111));
        assertTrue(xf.isBit0_modifyTimePresent());
        assertEquals(MODIFY, xf.getFlags());

        xf.setAccessJavaTime(new Date(2222));
        assertTrue(xf.isBit1_accessTimePresent());
        assertEquals(MODIFY | ACCESS, xf.getFlags());

        xf.setCreateJavaTime(new Date(3333));
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(MODIFY | ACCESS | CREATE, xf.getFlags());

        // Clearing a time (null) turns its bit off; the flags byte drops that bit.
        xf.setModifyJavaTime(null);
        assertFalse(xf.isBit0_modifyTimePresent());
        assertEquals(ACCESS | CREATE, xf.getFlags());

        xf.setAccessJavaTime(null);
        assertFalse(xf.isBit1_accessTimePresent());
        assertEquals(CREATE, xf.getFlags());

        xf.setCreateJavaTime(null);
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(0, xf.getFlags());
    }
}
