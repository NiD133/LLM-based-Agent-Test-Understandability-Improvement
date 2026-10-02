package org.apache.commons.compress.archivers.zip;

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

    @Test
    void testBitsAreSetWithTime() {
        xf.setModifyJavaTime(new Date(1111));
        assertTrue(xf.isBit0_modifyTimePresent());
        assertEquals(1, xf.getFlags());

        xf.setAccessJavaTime(new Date(2222));
        assertTrue(xf.isBit1_accessTimePresent());
        assertEquals(3, xf.getFlags());

        xf.setCreateJavaTime(new Date(3333));
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(7, xf.getFlags());

        xf.setModifyJavaTime(null);
        assertFalse(xf.isBit0_modifyTimePresent());
        assertEquals(6, xf.getFlags());

        xf.setAccessJavaTime(null);
        assertFalse(xf.isBit1_accessTimePresent());
        assertEquals(4, xf.getFlags());

        xf.setCreateJavaTime(null);
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(0, xf.getFlags());
    }
}
