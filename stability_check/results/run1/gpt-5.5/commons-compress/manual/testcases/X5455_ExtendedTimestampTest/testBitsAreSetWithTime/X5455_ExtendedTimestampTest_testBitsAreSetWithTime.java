package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class X5455_ExtendedTimestampTest_testBitsAreSetWithTime {

    private static final Date MODIFY_TIME = new Date(1111);
    private static final Date ACCESS_TIME = new Date(2222);
    private static final Date CREATE_TIME = new Date(3333);

    private static final int NO_TIMES = 0;
    private static final int MODIFY_TIME_ONLY = 1;
    private static final int MODIFY_AND_ACCESS_TIMES = 3;
    private static final int ALL_TIMES = 7;
    private static final int ACCESS_AND_CREATE_TIMES = 6;
    private static final int CREATE_TIME_ONLY = 4;

    /**
     * The extended field (xf) we are testing.
     */
    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testBitsAreSetWithTime() {
        xf.setModifyJavaTime(MODIFY_TIME);
        assertModifyTimePresent();
        assertFlags(MODIFY_TIME_ONLY);

        xf.setAccessJavaTime(ACCESS_TIME);
        assertAccessTimePresent();
        assertFlags(MODIFY_AND_ACCESS_TIMES);

        xf.setCreateJavaTime(CREATE_TIME);
        assertCreateTimePresent();
        assertFlags(ALL_TIMES);

        xf.setModifyJavaTime(null);
        assertModifyTimeAbsent();
        assertFlags(ACCESS_AND_CREATE_TIMES);

        xf.setAccessJavaTime(null);
        assertAccessTimeAbsent();
        assertFlags(CREATE_TIME_ONLY);

        xf.setCreateJavaTime(null);
        assertCreateTimeAbsent();
        assertFlags(NO_TIMES);
    }

    private void assertFlags(final int expectedFlags) {
        assertEquals(expectedFlags, xf.getFlags());
    }

    private void assertAccessTimeAbsent() {
        assertFalse(xf.isBit1_accessTimePresent());
    }

    private void assertAccessTimePresent() {
        assertTrue(xf.isBit1_accessTimePresent());
    }

    private void assertCreateTimeAbsent() {
        assertFalse(xf.isBit2_createTimePresent());
    }

    private void assertCreateTimePresent() {
        assertTrue(xf.isBit2_createTimePresent());
    }

    private void assertModifyTimeAbsent() {
        assertFalse(xf.isBit0_modifyTimePresent());
    }

    private void assertModifyTimePresent() {
        assertTrue(xf.isBit0_modifyTimePresent());
    }
}
