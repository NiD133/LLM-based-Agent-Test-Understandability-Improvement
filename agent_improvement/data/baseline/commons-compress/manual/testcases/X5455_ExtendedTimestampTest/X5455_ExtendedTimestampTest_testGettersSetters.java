package org.apache.commons.compress.archivers.zip;

import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.ACCESS_TIME_BIT;
import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.CREATE_TIME_BIT;
import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.MODIFY_TIME_BIT;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.TimeZone;
import java.util.zip.ZipException;
import org.apache.commons.compress.AbstractTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class X5455_ExtendedTimestampTest_testGettersSetters {

    private static final ZipShort X5455 = new ZipShort(0x5455);

    private static final ZipLong ZERO_TIME = new ZipLong(0);

    private static final ZipLong MAX_TIME_SECONDS = new ZipLong(Integer.MAX_VALUE);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss Z");

    /**
     * InfoZIP seems to adjust the time stored inside the LFH and CD to GMT when writing ZIPs while java.util.zip.ZipEntry thinks it was in local time.
     *
     * The archive read in {@link #testSampleFile} has been created with GMT-8, so we need to adjust for the difference.
     */
    private static Date adjustFromGMTToExpectedOffset(final Date from) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        cal.add(Calendar.MILLISECOND, cal.get(Calendar.ZONE_OFFSET));
        if (cal.getTimeZone().inDaylightTime(from)) {
            cal.add(Calendar.MILLISECOND, cal.get(Calendar.DST_OFFSET));
        }
        cal.add(Calendar.HOUR, 8);
        return cal.getTime();
    }

    private static boolean isFlagSet(final byte data, final byte flag) {
        return (data & flag) == flag;
    }

    /**
     * The extended field (xf) we are testing.
     */
    private X5455_ExtendedTimestamp xf;

    @TempDir
    private File tmpDir;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    private void parseReparse(final byte providedFlags, final ZipLong time, final byte expectedFlags, final byte[] expectedLocal, final byte[] almostExpectedCentral) throws ZipException {
        // We're responsible for expectedCentral's flags. Too annoying to set in caller.
        final byte[] expectedCentral = new byte[almostExpectedCentral.length];
        System.arraycopy(almostExpectedCentral, 0, expectedCentral, 0, almostExpectedCentral.length);
        expectedCentral[0] = expectedFlags;
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        xf.setFlags(providedFlags);
        byte[] result = xf.getLocalFileDataData();
        assertArrayEquals(expectedLocal, result);
        // And now we re-parse:
        xf.parseFromLocalFileData(result, 0, result.length);
        assertEquals(expectedFlags, xf.getFlags());
        if (isFlagSet(expectedFlags, MODIFY_TIME_BIT)) {
            assertTrue(xf.isBit0_modifyTimePresent());
            assertEquals(time, xf.getModifyTime());
        }
        if (isFlagSet(expectedFlags, ACCESS_TIME_BIT)) {
            assertTrue(xf.isBit1_accessTimePresent());
            assertEquals(time, xf.getAccessTime());
        }
        if (isFlagSet(expectedFlags, CREATE_TIME_BIT)) {
            assertTrue(xf.isBit2_createTimePresent());
            assertEquals(time, xf.getCreateTime());
        }
        // Do the same as above, but with Central Directory data:
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        xf.setFlags(providedFlags);
        result = xf.getCentralDirectoryData();
        assertArrayEquals(expectedCentral, result);
        // And now we re-parse:
        xf.parseFromCentralDirectoryData(result, 0, result.length);
        assertEquals(expectedFlags, xf.getFlags());
        // Central Directory never contains ACCESS or CREATE, but
        // may contain MODIFY.
        if (isFlagSet(expectedFlags, MODIFY_TIME_BIT)) {
            assertTrue(xf.isBit0_modifyTimePresent());
            assertEquals(time, xf.getModifyTime());
        }
    }

    private void parseReparse(final ZipLong time, final byte[] expectedLocal, final byte[] almostExpectedCentral) throws ZipException {
        parseReparse(expectedLocal[0], time, expectedLocal[0], expectedLocal, almostExpectedCentral);
    }

    @AfterEach
    public void removeTempFiles() {
        if (tmpDir != null) {
            AbstractTest.forceDelete(tmpDir);
        }
    }

    @Test
    void testGettersSetters() {
        // X5455 is concerned with time, so let's
        // get a timestamp to play with (Jan 1st, 2000).
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        final long timeMillis = cal.getTimeInMillis();
        final ZipLong time = new ZipLong(timeMillis / 1000);
        // set too big
        // Java time is 1000 x larger (milliseconds).
        assertThrows(IllegalArgumentException.class, () -> xf.setModifyJavaTime(new Date(1000L * (MAX_TIME_SECONDS.getValue() + 1L))), "Time too big for 32 bits!");
        // get/set modify time
        xf.setModifyTime(time);
        assertEquals(time, xf.getModifyTime());
        assertEquals(timeMillis, xf.getModifyJavaTime().getTime());
        assertEquals(timeMillis, xf.getModifyFileTime().toMillis());
        assertTrue(xf.isBit0_modifyTimePresent());
        xf.setModifyJavaTime(new Date(timeMillis));
        assertEquals(time, xf.getModifyTime());
        assertEquals(timeMillis, xf.getModifyJavaTime().getTime());
        assertEquals(timeMillis, xf.getModifyFileTime().toMillis());
        assertTrue(xf.isBit0_modifyTimePresent());
        // Make sure milliseconds get zeroed out:
        xf.setModifyJavaTime(new Date(timeMillis + 123));
        assertEquals(time, xf.getModifyTime());
        assertEquals(timeMillis, xf.getModifyJavaTime().getTime());
        assertEquals(timeMillis, xf.getModifyFileTime().toMillis());
        assertTrue(xf.isBit0_modifyTimePresent());
        // Make sure it works correctly for FileTime
        xf.setModifyFileTime(FileTime.fromMillis(timeMillis + 123));
        assertEquals(time, xf.getModifyTime());
        assertEquals(timeMillis, xf.getModifyJavaTime().getTime());
        assertEquals(timeMillis, xf.getModifyFileTime().toMillis());
        assertTrue(xf.isBit0_modifyTimePresent());
        // Null
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
        // get/set access time
        xf.setAccessTime(time);
        assertEquals(time, xf.getAccessTime());
        assertEquals(timeMillis, xf.getAccessJavaTime().getTime());
        assertEquals(timeMillis, xf.getAccessFileTime().toMillis());
        assertTrue(xf.isBit1_accessTimePresent());
        xf.setAccessJavaTime(new Date(timeMillis));
        assertEquals(time, xf.getAccessTime());
        assertEquals(timeMillis, xf.getAccessJavaTime().getTime());
        assertEquals(timeMillis, xf.getAccessFileTime().toMillis());
        assertTrue(xf.isBit1_accessTimePresent());
        // Make sure milliseconds get zeroed out:
        xf.setAccessJavaTime(new Date(timeMillis + 123));
        assertEquals(time, xf.getAccessTime());
        assertEquals(timeMillis, xf.getAccessJavaTime().getTime());
        assertEquals(timeMillis, xf.getAccessFileTime().toMillis());
        assertTrue(xf.isBit1_accessTimePresent());
        // Make sure it works correctly for FileTime
        xf.setAccessFileTime(FileTime.fromMillis(timeMillis + 123));
        assertEquals(time, xf.getAccessTime());
        assertEquals(timeMillis, xf.getAccessJavaTime().getTime());
        assertEquals(timeMillis, xf.getAccessFileTime().toMillis());
        assertTrue(xf.isBit1_accessTimePresent());
        // Null
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
        // get/set create time
        xf.setCreateTime(time);
        assertEquals(time, xf.getCreateTime());
        assertEquals(timeMillis, xf.getCreateJavaTime().getTime());
        assertEquals(timeMillis, xf.getCreateFileTime().toMillis());
        assertTrue(xf.isBit2_createTimePresent());
        xf.setCreateJavaTime(new Date(timeMillis));
        assertEquals(time, xf.getCreateTime());
        assertEquals(timeMillis, xf.getCreateJavaTime().getTime());
        assertEquals(timeMillis, xf.getCreateFileTime().toMillis());
        assertTrue(xf.isBit2_createTimePresent());
        // Make sure milliseconds get zeroed out:
        xf.setCreateJavaTime(new Date(timeMillis + 123));
        assertEquals(time, xf.getCreateTime());
        assertEquals(timeMillis, xf.getCreateJavaTime().getTime());
        assertEquals(timeMillis, xf.getCreateFileTime().toMillis());
        assertTrue(xf.isBit2_createTimePresent());
        // Make sure it works correctly for FileTime
        xf.setCreateFileTime(FileTime.fromMillis(timeMillis + 123));
        assertEquals(time, xf.getCreateTime());
        assertEquals(timeMillis, xf.getCreateJavaTime().getTime());
        assertEquals(timeMillis, xf.getCreateFileTime().toMillis());
        assertTrue(xf.isBit2_createTimePresent());
        // Null
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
        // initialize for flags
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        // get/set flags: 000
        xf.setFlags((byte) 0);
        assertEquals(0, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        // Local length=1, Central length=1 (flags only!)
        assertEquals(1, xf.getLocalFileDataLength().getValue());
        assertEquals(1, xf.getCentralDirectoryLength().getValue());
        // get/set flags: 001
        xf.setFlags((byte) 1);
        assertEquals(1, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        // Local length=5, Central length=5 (flags + mod)
        assertEquals(5, xf.getLocalFileDataLength().getValue());
        assertEquals(5, xf.getCentralDirectoryLength().getValue());
        // get/set flags: 010
        xf.setFlags((byte) 2);
        assertEquals(2, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        // Local length=5, Central length=1
        assertEquals(5, xf.getLocalFileDataLength().getValue());
        assertEquals(1, xf.getCentralDirectoryLength().getValue());
        // get/set flags: 100
        xf.setFlags((byte) 4);
        assertEquals(4, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        // Local length=5, Central length=1
        assertEquals(5, xf.getLocalFileDataLength().getValue());
        assertEquals(1, xf.getCentralDirectoryLength().getValue());
        // get/set flags: 111
        xf.setFlags((byte) 7);
        assertEquals(7, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        // Local length=13, Central length=5
        assertEquals(13, xf.getLocalFileDataLength().getValue());
        assertEquals(5, xf.getCentralDirectoryLength().getValue());
        // get/set flags: 11111111
        xf.setFlags((byte) -1);
        assertEquals(-1, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        // Local length=13, Central length=5
        assertEquals(13, xf.getLocalFileDataLength().getValue());
        assertEquals(5, xf.getCentralDirectoryLength().getValue());
    }
}
