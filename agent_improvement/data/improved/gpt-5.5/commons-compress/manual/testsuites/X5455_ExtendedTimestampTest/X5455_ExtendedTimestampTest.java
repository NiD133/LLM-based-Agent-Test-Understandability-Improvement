/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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

class X5455_ExtendedTimestampTest {
    private static final ZipShort X5455 = new ZipShort(0x5455);

    private static final ZipLong ZERO_TIME = new ZipLong(0);
    private static final ZipLong MAX_TIME_SECONDS = new ZipLong(Integer.MAX_VALUE);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss Z");

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

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

    private static long firstSecondOfJan1st2000Utc() {
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

    private void assertAccessTime(final ZipLong time, final long timeMillis) {
        assertEquals(time, xf.getAccessTime());
        assertEquals(timeMillis, xf.getAccessJavaTime().getTime());
        assertEquals(timeMillis, xf.getAccessFileTime().toMillis());
        assertTrue(xf.isBit1_accessTimePresent());
    }

    private void assertCreateTime(final ZipLong time, final long timeMillis) {
        assertEquals(time, xf.getCreateTime());
        assertEquals(timeMillis, xf.getCreateJavaTime().getTime());
        assertEquals(timeMillis, xf.getCreateFileTime().toMillis());
        assertTrue(xf.isBit2_createTimePresent());
    }

    private void assertModifyTime(final ZipLong time, final long timeMillis) {
        assertEquals(time, xf.getModifyTime());
        assertEquals(timeMillis, xf.getModifyJavaTime().getTime());
        assertEquals(timeMillis, xf.getModifyFileTime().toMillis());
        assertTrue(xf.isBit0_modifyTimePresent());
    }

    private void assertParsedAccessTime(final byte expectedFlags, final ZipLong time) {
        if (isFlagSet(expectedFlags, ACCESS_TIME_BIT)) {
            assertTrue(xf.isBit1_accessTimePresent());
            assertEquals(time, xf.getAccessTime());
        }
    }

    private void assertParsedCreateTime(final byte expectedFlags, final ZipLong time) {
        if (isFlagSet(expectedFlags, CREATE_TIME_BIT)) {
            assertTrue(xf.isBit2_createTimePresent());
            assertEquals(time, xf.getCreateTime());
        }
    }

    private void assertParsedModifyTime(final byte expectedFlags, final ZipLong time) {
        if (isFlagSet(expectedFlags, MODIFY_TIME_BIT)) {
            assertTrue(xf.isBit0_modifyTimePresent());
            assertEquals(time, xf.getModifyTime());
        }
    }

    private void assertTimestampStringsAreAbsent() {
        assertFalse(xf.toString().contains(" Modify:"));
        assertFalse(xf.toString().contains(" Access:"));
        assertFalse(xf.toString().contains(" Create:"));
    }

    private void assertTimestampStringsArePresent() {
        assertTrue(xf.toString().contains(" Modify:"));
        assertTrue(xf.toString().contains(" Access:"));
        assertTrue(xf.toString().contains(" Create:"));
    }

    private void parseReparse(final byte providedFlags, final ZipLong time, final byte expectedFlags, final byte[] expectedLocal,
            final byte[] almostExpectedCentral) throws ZipException {

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

        xf.parseFromLocalFileData(result, 0, result.length);
        assertEquals(expectedFlags, xf.getFlags());
        assertParsedModifyTime(expectedFlags, time);
        assertParsedAccessTime(expectedFlags, time);
        assertParsedCreateTime(expectedFlags, time);

        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        xf.setFlags(providedFlags);
        result = xf.getCentralDirectoryData();
        assertArrayEquals(expectedCentral, result);

        xf.parseFromCentralDirectoryData(result, 0, result.length);
        assertEquals(expectedFlags, xf.getFlags());
        // Central Directory never contains ACCESS or CREATE, but may contain MODIFY.
        assertParsedModifyTime(expectedFlags, time);
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

    @Test
    void testGetHeaderId() {
        assertEquals(X5455, xf.getHeaderId());
    }

    @Test
    void testGettersSetters() {
        final long timeMillis = firstSecondOfJan1st2000Utc();
        final ZipLong time = new ZipLong(timeMillis / 1000);

        assertThrows(IllegalArgumentException.class, () -> xf.setModifyJavaTime(new Date(1000L * (MAX_TIME_SECONDS.getValue() + 1L))),
                "Time too big for 32 bits!");

        xf.setModifyTime(time);
        assertModifyTime(time, timeMillis);
        xf.setModifyJavaTime(new Date(timeMillis));
        assertModifyTime(time, timeMillis);
        xf.setModifyJavaTime(new Date(timeMillis + 123));
        assertModifyTime(time, timeMillis);
        xf.setModifyFileTime(FileTime.fromMillis(timeMillis + 123));
        assertModifyTime(time, timeMillis);

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

        xf.setAccessTime(time);
        assertAccessTime(time, timeMillis);
        xf.setAccessJavaTime(new Date(timeMillis));
        assertAccessTime(time, timeMillis);
        xf.setAccessJavaTime(new Date(timeMillis + 123));
        assertAccessTime(time, timeMillis);
        xf.setAccessFileTime(FileTime.fromMillis(timeMillis + 123));
        assertAccessTime(time, timeMillis);

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

        xf.setCreateTime(time);
        assertCreateTime(time, timeMillis);
        xf.setCreateJavaTime(new Date(timeMillis));
        assertCreateTime(time, timeMillis);
        xf.setCreateJavaTime(new Date(timeMillis + 123));
        assertCreateTime(time, timeMillis);
        xf.setCreateFileTime(FileTime.fromMillis(timeMillis + 123));
        assertCreateTime(time, timeMillis);

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

        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);

        xf.setFlags((byte) 0);
        assertEquals(0, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(1, xf.getLocalFileDataLength().getValue());
        assertEquals(1, xf.getCentralDirectoryLength().getValue());

        xf.setFlags((byte) 1);
        assertEquals(1, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(5, xf.getLocalFileDataLength().getValue());
        assertEquals(5, xf.getCentralDirectoryLength().getValue());

        xf.setFlags((byte) 2);
        assertEquals(2, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(5, xf.getLocalFileDataLength().getValue());
        assertEquals(1, xf.getCentralDirectoryLength().getValue());

        xf.setFlags((byte) 4);
        assertEquals(4, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(5, xf.getLocalFileDataLength().getValue());
        assertEquals(1, xf.getCentralDirectoryLength().getValue());

        xf.setFlags((byte) 7);
        assertEquals(7, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(13, xf.getLocalFileDataLength().getValue());
        assertEquals(5, xf.getCentralDirectoryLength().getValue());

        xf.setFlags((byte) -1);
        assertEquals(-1, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(13, xf.getLocalFileDataLength().getValue());
        assertEquals(5, xf.getCentralDirectoryLength().getValue());
    }

    @Test
    void testMisc() throws Exception {
        assertNotEquals(xf, new Object());
        assertTrue(xf.toString().startsWith("0x5455 Zip Extra Field"));
        assertTimestampStringsAreAbsent();
        Object o = xf.clone();
        assertEquals(o.hashCode(), xf.hashCode());
        assertEquals(xf, o);

        xf.setModifyJavaTime(new Date(1111));
        xf.setAccessJavaTime(new Date(2222));
        xf.setCreateJavaTime(new Date(3333));
        xf.setFlags((byte) 7);
        assertNotEquals(xf, o);
        assertTrue(xf.toString().startsWith("0x5455 Zip Extra Field"));
        assertTimestampStringsArePresent();
        o = xf.clone();
        assertEquals(o.hashCode(), xf.hashCode());
        assertEquals(xf, o);
    }

    @Test
    void testParseReparse() throws ZipException {
        /*
         * X5455 stores a flags byte followed by modification, access, and creation timestamps when their flag bits are set.
         * Central-directory data only carries the modification timestamp bytes.
         */
        final byte[] nullFlags = { 0 };
        final byte[] accessCentral = { 2 };
        final byte[] createCentral = { 4 };

        final byte[] modifyZero = { 1, 0, 0, 0, 0 };
        final byte[] modifyMax = { 1, -1, -1, -1, 0x7f };
        final byte[] accessZero = { 2, 0, 0, 0, 0 };
        final byte[] accessMax = { 2, -1, -1, -1, 0x7f };
        final byte[] createZero = { 4, 0, 0, 0, 0 };
        final byte[] createMax = { 4, -1, -1, -1, 0x7f };
        final byte[] modifyAccessZero = { 3, 0, 0, 0, 0, 0, 0, 0, 0 };
        final byte[] modifyAccessMax = { 3, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };
        final byte[] modifyAccessCreateZero = { 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        final byte[] modifyAccessCreateMax = { 7, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };

        parseReparse(null, nullFlags, nullFlags);
        parseReparse(ZERO_TIME, modifyZero, modifyZero);
        parseReparse(MAX_TIME_SECONDS, modifyMax, modifyMax);
        parseReparse(ZERO_TIME, accessZero, accessCentral);
        parseReparse(MAX_TIME_SECONDS, accessMax, accessCentral);
        parseReparse(ZERO_TIME, createZero, createCentral);
        parseReparse(MAX_TIME_SECONDS, createMax, createCentral);
        parseReparse(ZERO_TIME, modifyAccessZero, modifyZero);
        parseReparse(MAX_TIME_SECONDS, modifyAccessMax, modifyMax);
        parseReparse(ZERO_TIME, modifyAccessCreateZero, modifyZero);
        parseReparse(MAX_TIME_SECONDS, modifyAccessCreateMax, modifyMax);

        // As far as the spec is concerned (December 2012) all of these flags are spurious versions of 7 (binary 00000111).
        parseReparse((byte) 15, MAX_TIME_SECONDS, (byte) 7, modifyAccessCreateMax, modifyMax);
        parseReparse((byte) 31, MAX_TIME_SECONDS, (byte) 7, modifyAccessCreateMax, modifyMax);
        parseReparse((byte) 63, MAX_TIME_SECONDS, (byte) 7, modifyAccessCreateMax, modifyMax);
        parseReparse((byte) 71, MAX_TIME_SECONDS, (byte) 7, modifyAccessCreateMax, modifyMax);
        parseReparse((byte) 127, MAX_TIME_SECONDS, (byte) 7, modifyAccessCreateMax, modifyMax);
        parseReparse((byte) -1, MAX_TIME_SECONDS, (byte) 7, modifyAccessCreateMax, modifyMax);
    }

    @Test
    void testResetsFlagsWhenLocalFileArrayIsTooShort() throws Exception {
        final byte[] local = { 7 }; // claims all three time values would be present, but they are not
        xf.parseFromLocalFileData(local, 0, 1);
        assertArrayEquals(new byte[1], xf.getLocalFileDataData());
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
                    continue;
                }

                final X5455_ExtendedTimestamp xf = (X5455_ExtendedTimestamp) zae.getExtraField(X5455);
                final Date rawZ = zae.getLastModifiedDate();
                final Date m = xf.getModifyJavaTime();

                /*
                 * We must distinguish three cases: Java has read the extended time field correctly, Java has read it but treats years >= 2038 as unsigned,
                 * or Java has not read the extended time field at all.
                 */
                final boolean zipTimeUsesExtendedTimestampCorrectly = rawZ.equals(m);
                final boolean zipTimeUsesExtendedTimestampButUnsigned = year > 2037 && rawZ.getSeconds() == 1;
                final boolean zipTimeUsesExtendedTimestamp = zipTimeUsesExtendedTimestampCorrectly || zipTimeUsesExtendedTimestampButUnsigned;

                final Date z = zipTimeUsesExtendedTimestamp ? rawZ : adjustFromGMTToExpectedOffset(rawZ);
                final Date a = xf.getAccessJavaTime();

                final String zipTime = DATE_FORMAT.format(z);
                final String modTime = DATE_FORMAT.format(m);
                final String accTime = DATE_FORMAT.format(a);

                switch (year) {
                case 2109:
                    // All three timestamps have overflowed by 2109.
                    if (!zipTimeUsesExtendedTimestamp) {
                        assertEquals("1981-01-01/00:00:02 +0000", zipTime);
                    }
                    break;
                default:
                    if (!zipTimeUsesExtendedTimestamp) {
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

    @Test
    void testWriteReadRoundtrip() throws IOException {
        final File output = new File(tmpDir, "write_rewrite.zip");
        final Calendar instance = Calendar.getInstance();
        instance.clear();
        instance.set(1997, 8, 24, 15, 10, 2);
        final Date date = instance.getTime();
        try (OutputStream out = Files.newOutputStream(output.toPath());
                ZipArchiveOutputStream os = new ZipArchiveOutputStream(out)) {
            final ZipArchiveEntry ze = new ZipArchiveEntry("foo");
            xf.setModifyJavaTime(date);
            xf.setFlags((byte) 1);
            ze.addExtraField(xf);
            os.putArchiveEntry(ze);
            os.closeArchiveEntry();
        }

        try (ZipFile zf = ZipFile.builder().setFile(output).get()) {
            final ZipArchiveEntry ze = zf.getEntry("foo");
            final X5455_ExtendedTimestamp ext = (X5455_ExtendedTimestamp) ze.getExtraField(X5455);
            assertNotNull(ext);
            assertTrue(ext.isBit0_modifyTimePresent());
            assertEquals(date, ext.getModifyJavaTime());
        }
    }
}
