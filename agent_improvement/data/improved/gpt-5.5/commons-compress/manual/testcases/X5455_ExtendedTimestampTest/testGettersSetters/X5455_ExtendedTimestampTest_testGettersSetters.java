package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.compress.AbstractTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class X5455_ExtendedTimestampTest_testGettersSetters {

    private static final ZipLong MAX_TIME_SECONDS = new ZipLong(Integer.MAX_VALUE);

    private X5455_ExtendedTimestamp xf;

    @TempDir
    private File tmpDir;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @AfterEach
    public void removeTempFiles() {
        if (tmpDir != null) {
            AbstractTest.forceDelete(tmpDir);
        }
    }

    @Test
    void testGettersSetters() {
        final long timeMillis = utcStartOfJanuaryFirst2000();
        final ZipLong time = new ZipLong(timeMillis / 1000);

        assertThrows(IllegalArgumentException.class, () -> xf.setModifyJavaTime(new Date(1000L * (MAX_TIME_SECONDS.getValue() + 1L))),
                "Time too big for 32 bits!");

        assertModifyTimeSetters(time, timeMillis);
        assertModifyTimeNullSetters();

        assertAccessTimeSetters(time, timeMillis);
        assertAccessTimeNullSetters();

        assertCreateTimeSetters(time, timeMillis);
        assertCreateTimeNullSetters();

        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);

        assertFlags((byte) 0, 0, false, false, false, 1, 1);
        assertFlags((byte) 1, 1, true, false, false, 5, 5);
        assertFlags((byte) 2, 2, false, true, false, 5, 1);
        assertFlags((byte) 4, 4, false, false, true, 5, 1);
        assertFlags((byte) 7, 7, true, true, true, 13, 5);
        assertFlags((byte) -1, -1, true, true, true, 13, 5);
    }

    private static long utcStartOfJanuaryFirst2000() {
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private void assertModifyTimeSetters(final ZipLong time, final long timeMillis) {
        xf.setModifyTime(time);
        assertModifyTimePresent(time, timeMillis);

        xf.setModifyJavaTime(new Date(timeMillis));
        assertModifyTimePresent(time, timeMillis);

        xf.setModifyJavaTime(new Date(timeMillis + 123));
        assertModifyTimePresent(time, timeMillis);

        xf.setModifyFileTime(FileTime.fromMillis(timeMillis + 123));
        assertModifyTimePresent(time, timeMillis);
    }

    private void assertModifyTimePresent(final ZipLong time, final long timeMillis) {
        assertEquals(time, xf.getModifyTime());
        assertEquals(timeMillis, xf.getModifyJavaTime().getTime());
        assertEquals(timeMillis, xf.getModifyFileTime().toMillis());
        assertTrue(xf.isBit0_modifyTimePresent());
    }

    private void assertModifyTimeNullSetters() {
        xf.setModifyTime(null);
        assertNull(xf.getModifyJavaTime());
        assertNull(xf.getModifyFileTime());
        assertFalse(xf.isBit0_modifyTimePresent());

        xf.setModifyJavaTime(null);
        assertNull(xf.getModifyTime());
        assertNull(xf.getModifyFileTime());
        assertFalse(xf.isBit0_modifyTimePresent());

        xf.setModifyFileTime(null);
        assertNull(xf.getModifyJavaTime());
        assertNull(xf.getModifyTime());
        assertFalse(xf.isBit0_modifyTimePresent());
    }

    private void assertAccessTimeSetters(final ZipLong time, final long timeMillis) {
        xf.setAccessTime(time);
        assertAccessTimePresent(time, timeMillis);

        xf.setAccessJavaTime(new Date(timeMillis));
        assertAccessTimePresent(time, timeMillis);

        xf.setAccessJavaTime(new Date(timeMillis + 123));
        assertAccessTimePresent(time, timeMillis);

        xf.setAccessFileTime(FileTime.fromMillis(timeMillis + 123));
        assertAccessTimePresent(time, timeMillis);
    }

    private void assertAccessTimePresent(final ZipLong time, final long timeMillis) {
        assertEquals(time, xf.getAccessTime());
        assertEquals(timeMillis, xf.getAccessJavaTime().getTime());
        assertEquals(timeMillis, xf.getAccessFileTime().toMillis());
        assertTrue(xf.isBit1_accessTimePresent());
    }

    private void assertAccessTimeNullSetters() {
        xf.setAccessTime(null);
        assertNull(xf.getAccessJavaTime());
        assertNull(xf.getAccessFileTime());
        assertFalse(xf.isBit1_accessTimePresent());

        xf.setAccessJavaTime(null);
        assertNull(xf.getAccessTime());
        assertNull(xf.getAccessFileTime());
        assertFalse(xf.isBit1_accessTimePresent());

        xf.setAccessFileTime(null);
        assertNull(xf.getAccessJavaTime());
        assertNull(xf.getAccessTime());
        assertFalse(xf.isBit1_accessTimePresent());
    }

    private void assertCreateTimeSetters(final ZipLong time, final long timeMillis) {
        xf.setCreateTime(time);
        assertCreateTimePresent(time, timeMillis);

        xf.setCreateJavaTime(new Date(timeMillis));
        assertCreateTimePresent(time, timeMillis);

        xf.setCreateJavaTime(new Date(timeMillis + 123));
        assertCreateTimePresent(time, timeMillis);

        xf.setCreateFileTime(FileTime.fromMillis(timeMillis + 123));
        assertCreateTimePresent(time, timeMillis);
    }

    private void assertCreateTimePresent(final ZipLong time, final long timeMillis) {
        assertEquals(time, xf.getCreateTime());
        assertEquals(timeMillis, xf.getCreateJavaTime().getTime());
        assertEquals(timeMillis, xf.getCreateFileTime().toMillis());
        assertTrue(xf.isBit2_createTimePresent());
    }

    private void assertCreateTimeNullSetters() {
        xf.setCreateTime(null);
        assertNull(xf.getCreateJavaTime());
        assertNull(xf.getCreateFileTime());
        assertFalse(xf.isBit2_createTimePresent());

        xf.setCreateJavaTime(null);
        assertNull(xf.getCreateTime());
        assertNull(xf.getCreateFileTime());
        assertFalse(xf.isBit2_createTimePresent());

        xf.setCreateFileTime(null);
        assertNull(xf.getCreateJavaTime());
        assertNull(xf.getCreateTime());
        assertFalse(xf.isBit2_createTimePresent());
    }

    private void assertFlags(final byte flags, final int expectedFlags, final boolean modifyTimePresent, final boolean accessTimePresent,
            final boolean createTimePresent, final int expectedLocalLength, final int expectedCentralLength) {
        xf.setFlags(flags);
        assertEquals(expectedFlags, xf.getFlags());
        assertEquals(modifyTimePresent, xf.isBit0_modifyTimePresent());
        assertEquals(accessTimePresent, xf.isBit1_accessTimePresent());
        assertEquals(createTimePresent, xf.isBit2_createTimePresent());
        assertEquals(expectedLocalLength, xf.getLocalFileDataLength().getValue());
        assertEquals(expectedCentralLength, xf.getCentralDirectoryLength().getValue());
    }
}
