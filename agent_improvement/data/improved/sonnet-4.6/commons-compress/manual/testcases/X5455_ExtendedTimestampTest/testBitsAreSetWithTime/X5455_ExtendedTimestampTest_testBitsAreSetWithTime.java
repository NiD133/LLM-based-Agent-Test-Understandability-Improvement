package org.apache.commons.compress.archivers.zip;

import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.ACCESS_TIME_BIT;
import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.CREATE_TIME_BIT;
import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.MODIFY_TIME_BIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class X5455_ExtendedTimestampTest_testBitsAreSetWithTime {

    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    /**
     * Verifies that setting a timestamp via the JavaTime setter automatically sets the
     * corresponding bit in the flags byte, and that clearing it (null) clears the bit.
     * Bits are accumulated as each timestamp type is set, and decremented as each is cleared.
     */
    @Test
    void testBitsAreSetWithTime() {
        // Setting modify time sets bit 0 only → flags = 0b001
        xf.setModifyJavaTime(new Date(1111));
        assertTrue(xf.isBit0_modifyTimePresent());
        assertEquals(MODIFY_TIME_BIT, xf.getFlags());

        // Adding access time sets bit 1 as well → flags = 0b011
        xf.setAccessJavaTime(new Date(2222));
        assertTrue(xf.isBit1_accessTimePresent());
        assertEquals(MODIFY_TIME_BIT | ACCESS_TIME_BIT, xf.getFlags());

        // Adding create time sets bit 2 as well → flags = 0b111
        xf.setCreateJavaTime(new Date(3333));
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(MODIFY_TIME_BIT | ACCESS_TIME_BIT | CREATE_TIME_BIT, xf.getFlags());

        // Clearing modify time clears bit 0 → flags = 0b110
        xf.setModifyJavaTime(null);
        assertFalse(xf.isBit0_modifyTimePresent());
        assertEquals(ACCESS_TIME_BIT | CREATE_TIME_BIT, xf.getFlags());

        // Clearing access time clears bit 1 → flags = 0b100
        xf.setAccessJavaTime(null);
        assertFalse(xf.isBit1_accessTimePresent());
        assertEquals(CREATE_TIME_BIT, xf.getFlags());

        // Clearing create time clears bit 2 → flags = 0b000
        xf.setCreateJavaTime(null);
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(0, xf.getFlags());
    }
}
