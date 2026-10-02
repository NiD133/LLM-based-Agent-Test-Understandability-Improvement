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
     * InfoZIP stores timestamps in GMT inside LFH/CD, but java.util.zip.ZipEntry
     * treats them as local time. The archive was created in GMT-8, so we add
     * 8 hours plus any local zone/DST offset to reconstruct the expected time.
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

    @Test
    void testSampleFile() throws Exception {
        /*
         * COMPRESS-210_unix_time_zip_test.zip holds entries named by the year whose
         * timestamp was targeted (Jan 1st midnight UTC). Each non-directory entry carries:
         *   - zip time  (2-second granularity): Jan 1 <year> 00:00:02
         *   - modify time (X5455, per-second): Jan 1 <year> 00:00:01
         *   - access time (X5455, per-second): Jan 1 <year> 00:00:03
         *
         * Starting with a patch release of Java 8, the JVM reads the X5455 field
         * directly for the zip entry's last-modified date:
         *   https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/90df6756406f
         *
         * Starting with Java 9, the X5455 parser was corrected to use signed integers
         * (the format required by the spec), which Compress 1.15 also adopted.
         */
        final File archive = AbstractTest.getFile("COMPRESS-210_unix_time_zip_test.zip");
        try (ZipFile zipFile = ZipFile.builder().setFile(archive).get()) {
            final Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
            while (entries.hasMoreElements()) {
                final ZipArchiveEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                final String name = entry.getName();
                final int lastSlash = name.lastIndexOf('/');
                final String yearString = name.substring(lastSlash + 1);
                final int year;
                try {
                    year = Integer.parseInt(yearString);
                } catch (final NumberFormatException nfe) {
                    // Non-year entries such as setTime.sh — skip
                    continue;
                }

                final X5455_ExtendedTimestamp extendedTimestamp =
                        (X5455_ExtendedTimestamp) entry.getExtraField(X5455);
                final Date rawZipTime = entry.getLastModifiedDate();
                final Date modifyTime = extendedTimestamp.getModifyJavaTime();

                /*
                 * Detect whether the JVM already consumed the X5455 field when it
                 * populated the entry's last-modified date:
                 *
                 *   zipTimeMatchesModify       — Java 9+ (or Java 8 for years ≤ 2037)
                 *                                read the field correctly.
                 *   zipTimeUsedUnsignedExtended — Java 8 read the field but treated
                 *                                 the seconds as unsigned; for years >
                 *                                 2037 this causes the value to overflow
                 *                                 back to 1 second.
                 */
                final boolean zipTimeMatchesModify = rawZipTime.equals(modifyTime);
                final boolean zipTimeUsedUnsignedExtended = year > 2037 && rawZipTime.getSeconds() == 1;
                final boolean jvmReadExtendedTimestamp = zipTimeMatchesModify || zipTimeUsedUnsignedExtended;

                // When the JVM did not read the X5455 field, rawZipTime reflects
                // InfoZIP's GMT-stored value and requires an 8-hour offset correction.
                final Date zipTime = jvmReadExtendedTimestamp
                        ? rawZipTime
                        : adjustFromGMTToExpectedOffset(rawZipTime);
                final Date accessTime = extendedTimestamp.getAccessJavaTime();

                final String formattedZipTime    = DATE_FORMAT.format(zipTime);
                final String formattedModifyTime = DATE_FORMAT.format(modifyTime);
                final String formattedAccessTime = DATE_FORMAT.format(accessTime);

                switch (year) {
                    case 2109:
                        // All three timestamps have overflowed by 2109 (32-bit signed seconds exhausted).
                        if (!jvmReadExtendedTimestamp) {
                            assertEquals("1981-01-01/00:00:02 +0000", formattedZipTime);
                        }
                        break;
                    default:
                        if (!jvmReadExtendedTimestamp) {
                            // X5455 covers epoch (1970) through 2037; zip format covers 1980–2107.
                            if (year < 1980) {
                                assertEquals("1980-01-01/08:00:00 +0000", formattedZipTime);
                            } else {
                                assertEquals(year + "-01-01/00:00:02 +0000", formattedZipTime);
                            }
                        }
                        if (year < 2038) {
                            assertEquals(year + "-01-01/00:00:01 +0000", formattedModifyTime);
                            assertEquals(year + "-01-01/00:00:03 +0000", formattedAccessTime);
                        }
                        break;
                }
            }
        }
    }
}
