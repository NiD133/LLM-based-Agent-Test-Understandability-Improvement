package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class X5455_ExtendedTimestampTest_testBitsAreSetWithTime {

    // The flags byte encodes which timestamps are present: bit0=modify(1), bit1=access(2), bit2=create(4).
    // Binary literals below make the bit patterns explicit and self-documenting.
    private static final int FLAGS_NONE          = 0b000; // 0
    private static final int FLAGS_MODIFY_ONLY   = 0b001; // 1
    private static final int FLAGS_MOD_ACCESS    = 0b011; // 3
    private static final int FLAGS_ALL           = 0b111; // 7
    private static final int FLAGS_ACCESS_CREATE = 0b110; // 6
    private static final int FLAGS_CREATE_ONLY   = 0b100; // 4

    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    /**
     * Verifies that the flags byte is automatically updated whenever a Java timestamp is set or
     * cleared via the setXxxJavaTime methods: a non-null value sets the corresponding bit;
     * a null value clears it. Timestamps are accumulated one at a time, and then removed one
     * at a time, exercising all intermediate flag states.
     */
    @Test
    void testBitsAreSetWithTime() {
        // Set modify time — bit0 is turned on; only the modify bit (0b001) should be set.
        xf.setModifyJavaTime(new Date(1111));
        assertTrue(xf.isBit0_modifyTimePresent());
        assertEquals(FLAGS_MODIFY_ONLY, xf.getFlags());

        // Add access time — bit1 is also turned on; flags are now 0b011 (modify + access).
        xf.setAccessJavaTime(new Date(2222));
        assertTrue(xf.isBit1_accessTimePresent());
        assertEquals(FLAGS_MOD_ACCESS, xf.getFlags());

        // Add create time — bit2 is also turned on; all three bits are set (0b111).
        xf.setCreateJavaTime(new Date(3333));
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(FLAGS_ALL, xf.getFlags());

        // Clear modify time — bit0 is turned off; remaining flags are 0b110 (access + create).
        xf.setModifyJavaTime(null);
        assertFalse(xf.isBit0_modifyTimePresent());
        assertEquals(FLAGS_ACCESS_CREATE, xf.getFlags());

        // Clear access time — bit1 is turned off; only the create bit remains (0b100).
        xf.setAccessJavaTime(null);
        assertFalse(xf.isBit1_accessTimePresent());
        assertEquals(FLAGS_CREATE_ONLY, xf.getFlags());

        // Clear create time — bit2 is turned off; all bits are now clear (0b000).
        xf.setCreateJavaTime(null);
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(FLAGS_NONE, xf.getFlags());
    }
}
