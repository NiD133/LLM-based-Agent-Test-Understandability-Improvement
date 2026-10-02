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

public class X5455_ExtendedTimestampTest_testParseReparse {

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
    void testParseReparse() throws ZipException {
        /*
         * Recall the spec:
         *
         * 0x5455 Short tag for this extra block type ("UT") TSize Short total data size for this block Flags Byte info bits (ModTime) Long time of last
         * modification (UTC/GMT) (AcTime) Long time of last access (UTC/GMT) (CrTime) Long time of original creation (UTC/GMT)
         */
        final byte[] NULL_FLAGS = { 0 };
        // central data only contains the AC flag and no actual data
        final byte[] AC_CENTRAL = { 2 };
        // central data only contains the CR flag and no actual data
        final byte[] CR_CENTRAL = { 4 };
        final byte[] MOD_ZERO = { 1, 0, 0, 0, 0 };
        final byte[] MOD_MAX = { 1, -1, -1, -1, 0x7f };
        final byte[] AC_ZERO = { 2, 0, 0, 0, 0 };
        final byte[] AC_MAX = { 2, -1, -1, -1, 0x7f };
        final byte[] CR_ZERO = { 4, 0, 0, 0, 0 };
        final byte[] CR_MAX = { 4, -1, -1, -1, 0x7f };
        final byte[] MOD_AC_ZERO = { 3, 0, 0, 0, 0, 0, 0, 0, 0 };
        final byte[] MOD_AC_MAX = { 3, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };
        final byte[] MOD_AC_CR_ZERO = { 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        final byte[] MOD_AC_CR_MAX = { 7, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };
        parseReparse(null, NULL_FLAGS, NULL_FLAGS);
        parseReparse(ZERO_TIME, MOD_ZERO, MOD_ZERO);
        parseReparse(MAX_TIME_SECONDS, MOD_MAX, MOD_MAX);
        parseReparse(ZERO_TIME, AC_ZERO, AC_CENTRAL);
        parseReparse(MAX_TIME_SECONDS, AC_MAX, AC_CENTRAL);
        parseReparse(ZERO_TIME, CR_ZERO, CR_CENTRAL);
        parseReparse(MAX_TIME_SECONDS, CR_MAX, CR_CENTRAL);
        parseReparse(ZERO_TIME, MOD_AC_ZERO, MOD_ZERO);
        parseReparse(MAX_TIME_SECONDS, MOD_AC_MAX, MOD_MAX);
        parseReparse(ZERO_TIME, MOD_AC_CR_ZERO, MOD_ZERO);
        parseReparse(MAX_TIME_SECONDS, MOD_AC_CR_MAX, MOD_MAX);
        // As far as the spec is concerned (December 2012) all of these flags
        // are spurious versions of 7 (a.k.a. binary 00000111).
        parseReparse((byte) 15, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 31, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 63, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 71, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 127, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) -1, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
    }
}
