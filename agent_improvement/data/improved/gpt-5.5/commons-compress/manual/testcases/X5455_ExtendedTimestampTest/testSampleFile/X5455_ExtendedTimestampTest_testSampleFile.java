package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import org.apache.commons.compress.AbstractTest;
import org.junit.jupiter.api.Test;

public class X5455_ExtendedTimestampTest_testSampleFile {

    private static final ZipShort X5455 = new ZipShort(0x5455);
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

    private static Integer readYearFromEntryName(final ZipArchiveEntry entry) {
        final String name = entry.getName();
        final int lastDirectorySeparator = name.lastIndexOf('/');
        final String yearString = name.substring(lastDirectorySeparator + 1);
        try {
            return Integer.valueOf(yearString);
        } catch (final NumberFormatException nfe) {
            return null;
        }
    }

    private static boolean zipTimeUsesExtendedTimestamp(final Date rawZipTime, final Date modifyTime, final int year) {
        final boolean zipTimeUsesExtendedTimestampCorrectly = rawZipTime.equals(modifyTime);
        final boolean zipTimeUsesExtendedTimestampButUnsigned = year > 2037 && rawZipTime.getSeconds() == 1;
        return zipTimeUsesExtendedTimestampCorrectly || zipTimeUsesExtendedTimestampButUnsigned;
    }

    private static void assertZipTimeBeforeExtendedTimestampFallback(final int year, final String zipTime) {
        // X5455 time is good from epoch (1970) to 2037. Zip time is good from 1980 to 2107.
        if (year < 1980) {
            assertEquals("1980-01-01/08:00:00 +0000", zipTime);
        } else {
            assertEquals(year + "-01-01/00:00:02 +0000", zipTime);
        }
    }

    private static void assertExpectedTimes(final int year, final boolean zipTimeUsesExtendedTimestamp, final String zipTime, final String modTime,
            final String accTime) {
        switch (year) {
        case 2109:
            // All three timestamps have overflowed by 2109.
            if (!zipTimeUsesExtendedTimestamp) {
                assertEquals("1981-01-01/00:00:02 +0000", zipTime);
            }
            break;
        default:
            if (!zipTimeUsesExtendedTimestamp) {
                assertZipTimeBeforeExtendedTimestampFallback(year, zipTime);
            }
            if (year < 2038) {
                assertEquals(year + "-01-01/00:00:01 +0000", modTime);
                assertEquals(year + "-01-01/00:00:03 +0000", accTime);
            }
            break;
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
            final Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
            // Every non-directory year entry in this ZIP file should contain extra field 0x5455.
            while (entries.hasMoreElements()) {
                final ZipArchiveEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }

                final Integer year = readYearFromEntryName(entry);
                if (year == null) {
                    // setTime.sh, skip
                    continue;
                }

                final X5455_ExtendedTimestamp timestamp = (X5455_ExtendedTimestamp) entry.getExtraField(X5455);
                final Date rawZipTime = entry.getLastModifiedDate();
                final Date modifyTime = timestamp.getModifyJavaTime();
                /*
                 * We must distinguish three cases:
                 * - Java has read the extended time field itself and agrees with us (Java9 or Java8 and years prior to 2038).
                 * - Java has read the extended time field but found a year >= 2038 (Java8).
                 * - Java has not read the extended time field at all (Java7 or early Java8).
                 */
                final boolean zipTimeUsesExtendedTimestamp = zipTimeUsesExtendedTimestamp(rawZipTime, modifyTime, year.intValue());
                final Date comparableZipTime = zipTimeUsesExtendedTimestamp ? rawZipTime : adjustFromGMTToExpectedOffset(rawZipTime);

                final String zipTime = DATE_FORMAT.format(comparableZipTime);
                final String modTime = DATE_FORMAT.format(modifyTime);
                final String accTime = DATE_FORMAT.format(timestamp.getAccessJavaTime());

                assertExpectedTimes(year.intValue(), zipTimeUsesExtendedTimestamp, zipTime, modTime, accTime);
            }
        }
    }
}
