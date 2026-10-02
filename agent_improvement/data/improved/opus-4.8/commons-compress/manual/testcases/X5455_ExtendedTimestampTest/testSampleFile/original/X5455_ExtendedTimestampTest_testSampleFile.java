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

public class X5455_ExtendedTimestampTest_testSampleFile {

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
    void testSampleFile() throws Exception {
        /*
         * Contains entries with zipTime, accessTime, and modifyTime. The file name tells you the year we tried to set the time to (Jan 1st, Midnight, UTC).
         *
         * For example:
         *
         * COMPRESS-210_unix_time_zip_test/1999 COMPRESS-210_unix_time_zip_test/2000 COMPRESS-210_unix_time_zip_test/2108
         *
         * File's last-modified is 1st second after midnight. Zip-time's 2-second granularity rounds that up to 2nd second. File's last-access is 3rd second
         * after midnight.
         *
         * So, from example above:
         *
         * 1999's zip time: Jan 1st, 1999-01-01/00:00:02 1999's mod time: Jan 1st, 1999-01-01/00:00:01 1999's acc time: Jan 1st, 1999-01-01/00:00:03
         *
         * Starting with a patch release of Java8, "zip time" actually uses the extended time stamp field itself and should be the same as "mod time".
         * https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/90df6756406f
         *
         * Starting with Java9 the parser for extended time stamps has been fixed to use signed integers which was detected during the triage of COMPRESS-416.
         * Signed integers is the correct format and Compress 1.15 has started to use signed integers as well.
         */
        final File archive = AbstractTest.getFile("COMPRESS-210_unix_time_zip_test.zip");
        try (ZipFile zipFile = ZipFile.builder().setFile(archive).get()) {
            final Enumeration<ZipArchiveEntry> en = zipFile.getEntries();
            // We expect EVERY entry of this ZIP file
            // to contain extra field 0x5455.
            while (en.hasMoreElements()) {
                final ZipArchiveEntry zae = en.nextElement();
                if (zae.isDirectory()) {
                    continue;
                }
                final String name = zae.getName();
                final int x = name.lastIndexOf('/');
                final String yearString = name.substring(x + 1);
                final int year;
                try {
                    year = Integer.parseInt(yearString);
                } catch (final NumberFormatException nfe) {
                    // setTime.sh, skip
                    continue;
                }
                final X5455_ExtendedTimestamp xf = (X5455_ExtendedTimestamp) zae.getExtraField(X5455);
                final Date rawZ = zae.getLastModifiedDate();
                final Date m = xf.getModifyJavaTime();
                /*
                 * We must distinguish three cases: - Java has read the extended time field itself and agrees with us (Java9 or Java8 and years prior to 2038) -
                 * Java has read the extended time field but found a year >= 2038 (Java8) - Java hasn't read the extended time field at all (Java7- or early
                 * Java8)
                 */
                final boolean zipTimeUsesExtendedTimestampCorrectly = rawZ.equals(m);
                final boolean zipTimeUsesExtendedTimestampButUnsigned = year > 2037 && rawZ.getSeconds() == 1;
                final boolean zipTimeUsesExtendedTimestamp = zipTimeUsesExtendedTimestampCorrectly || zipTimeUsesExtendedTimestampButUnsigned;
                final Date z = zipTimeUsesExtendedTimestamp ? rawZ : adjustFromGMTToExpectedOffset(rawZ);
                final Date a = xf.getAccessJavaTime();
                final String zipTime = DATE_FORMAT.format(z);
                final String modTime = DATE_FORMAT.format(m);
                final String accTime = DATE_FORMAT.format(a);
                switch(year) {
                    case 2109:
                        // All three timestamps have overflowed by 2109.
                        if (!zipTimeUsesExtendedTimestamp) {
                            assertEquals("1981-01-01/00:00:02 +0000", zipTime);
                        }
                        break;
                    default:
                        if (!zipTimeUsesExtendedTimestamp) {
                            // X5455 time is good from epoch (1970) to 2037.
                            // Zip time is good from 1980 to 2107.
                            if (year < 1980) {
                                assertEquals("1980-01-01/08:00:00 +0000", zipTime);
                            } else {
                                assertEquals(year + "-01-01/00:00:02 +0000", zipTime);
                            }
                        }
                        if (year < 2038) {
                            assertEquals(year + "-01-01/00:00:01 +0000", modTime);
                            assertEquals(year + "-01-01/00:00:03 +0000", accTime);
                        }
                        break;
                }
            }
        }
    }
}
