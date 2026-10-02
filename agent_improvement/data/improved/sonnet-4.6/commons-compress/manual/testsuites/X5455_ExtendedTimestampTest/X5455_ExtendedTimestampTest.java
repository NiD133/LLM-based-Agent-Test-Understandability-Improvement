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

    // Date formatter used in sample-file assertions; UTC enforced so results are timezone-independent.
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss Z");

    // Arbitrary, distinct millisecond timestamps used to exercise setter/flag coupling.
    // Sub-second precision is deliberate: the field must silently truncate millis to whole seconds.
    private static final long ARBITRARY_MILLIS_A = 1111L;
    private static final long ARBITRARY_MILLIS_B = 2222L;
    private static final long ARBITRARY_MILLIS_C = 3333L;

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * InfoZIP adjusts timestamps to GMT when writing, while java.util.zip.ZipEntry treats them
     * as local time. The sample file was created with GMT-8, so we add the local offset (plus
     * DST if active) and 8 hours to derive the expected timestamp.
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

    /** The extra-field instance under test; recreated fresh before every test. */
    private X5455_ExtendedTimestamp xf;

    @TempDir
    private File tmpDir;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    /**
     * Sets all three timestamps to {@code time}, applies {@code providedFlags}, then verifies
     * that the serialised local-file-data matches {@code expectedLocal} and the central-directory
     * data matches {@code expectedCentral} (with the flags byte filled in automatically).
     * Finally re-parses both byte streams and asserts the timestamps survive the round-trip.
     *
     * <p>Note: central-directory data never includes access or create timestamp bytes,
     * even when those flag bits are set.</p>
     */
    private void parseReparse(final byte providedFlags, final ZipLong time, final byte expectedFlags, final byte[] expectedLocal,
            final byte[] almostExpectedCentral) throws ZipException {

        // Build the full expected central bytes; caller supplies the data portion, we supply the flags byte.
        final byte[] expectedCentral = new byte[almostExpectedCentral.length];
        System.arraycopy(almostExpectedCentral, 0, expectedCentral, 0, almostExpectedCentral.length);
        expectedCentral[0] = expectedFlags;

        // --- Local file data round-trip ---
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        xf.setFlags(providedFlags);
        byte[] result = xf.getLocalFileDataData();
        assertArrayEquals(expectedLocal, result);

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

        // --- Central directory data round-trip (access + create are never stored centrally) ---
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        xf.setFlags(providedFlags);
        result = xf.getCentralDirectoryData();
        assertArrayEquals(expectedCentral, result);

        xf.parseFromCentralDirectoryData(result, 0, result.length);
        assertEquals(expectedFlags, xf.getFlags());
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

    /**
     * Setting a timestamp via any setter must also set the corresponding flag bit.
     * Clearing a timestamp (null) must clear the bit. All three timestamp types
     * (modify / access / create) are exercised in order, then cleared in order.
     */
    @Test
    void testBitsAreSetWithTime() {
        // Setting modify time sets bit0 → flags = 001
        xf.setModifyJavaTime(new Date(ARBITRARY_MILLIS_A));
        assertTrue(xf.isBit0_modifyTimePresent());
        assertEquals(MODIFY_TIME_BIT, xf.getFlags());

        // Setting access time also sets bit1 → flags = 011
        xf.setAccessJavaTime(new Date(ARBITRARY_MILLIS_B));
        assertTrue(xf.isBit1_accessTimePresent());
        assertEquals(MODIFY_TIME_BIT | ACCESS_TIME_BIT, xf.getFlags());

        // Setting create time also sets bit2 → flags = 111
        xf.setCreateJavaTime(new Date(ARBITRARY_MILLIS_C));
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(MODIFY_TIME_BIT | ACCESS_TIME_BIT | CREATE_TIME_BIT, xf.getFlags());

        // Clearing modify (null) clears bit0 → flags = 110
        xf.setModifyJavaTime(null);
        assertFalse(xf.isBit0_modifyTimePresent());
        assertEquals(ACCESS_TIME_BIT | CREATE_TIME_BIT, xf.getFlags());

        // Clearing access (null) clears bit1 → flags = 100
        xf.setAccessJavaTime(null);
        assertFalse(xf.isBit1_accessTimePresent());
        assertEquals(CREATE_TIME_BIT, xf.getFlags());

        // Clearing create (null) clears bit2 → flags = 000
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
        // Build a well-known reference timestamp: midnight UTC on Jan 1st, 2000.
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

        // Timestamps beyond Integer.MAX_VALUE seconds cannot be stored in a signed 32-bit field.
        assertThrows(IllegalArgumentException.class, () -> xf.setModifyJavaTime(new Date(1000L * (MAX_TIME_SECONDS.getValue() + 1L))),
                "Time too big for 32 bits!");

        // --- modify time: ZipLong, Date, and FileTime setters are all equivalent ---
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
        // Sub-second precision must be silently truncated (milliseconds zeroed out).
        xf.setModifyJavaTime(new Date(timeMillis + 123));
        assertEquals(time, xf.getModifyTime());
        assertEquals(timeMillis, xf.getModifyJavaTime().getTime());
        assertEquals(timeMillis, xf.getModifyFileTime().toMillis());
        assertTrue(xf.isBit0_modifyTimePresent());
        xf.setModifyFileTime(FileTime.fromMillis(timeMillis + 123));
        assertEquals(time, xf.getModifyTime());
        assertEquals(timeMillis, xf.getModifyJavaTime().getTime());
        assertEquals(timeMillis, xf.getModifyFileTime().toMillis());
        assertTrue(xf.isBit0_modifyTimePresent());
        // Setting null clears the field and the corresponding flag bit.
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

        // --- access time ---
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
        // Sub-second precision must be silently truncated.
        xf.setAccessJavaTime(new Date(timeMillis + 123));
        assertEquals(time, xf.getAccessTime());
        assertEquals(timeMillis, xf.getAccessJavaTime().getTime());
        assertEquals(timeMillis, xf.getAccessFileTime().toMillis());
        assertTrue(xf.isBit1_accessTimePresent());
        xf.setAccessFileTime(FileTime.fromMillis(timeMillis + 123));
        assertEquals(time, xf.getAccessTime());
        assertEquals(timeMillis, xf.getAccessJavaTime().getTime());
        assertEquals(timeMillis, xf.getAccessFileTime().toMillis());
        assertTrue(xf.isBit1_accessTimePresent());
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

        // --- create time ---
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
        // Sub-second precision must be silently truncated.
        xf.setCreateJavaTime(new Date(timeMillis + 123));
        assertEquals(time, xf.getCreateTime());
        assertEquals(timeMillis, xf.getCreateJavaTime().getTime());
        assertEquals(timeMillis, xf.getCreateFileTime().toMillis());
        assertTrue(xf.isBit2_createTimePresent());
        xf.setCreateFileTime(FileTime.fromMillis(timeMillis + 123));
        assertEquals(time, xf.getCreateTime());
        assertEquals(timeMillis, xf.getCreateJavaTime().getTime());
        assertEquals(timeMillis, xf.getCreateFileTime().toMillis());
        assertTrue(xf.isBit2_createTimePresent());
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

        // Populate all three timestamps so the flags tests below have data to work with.
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);

        // --- flags byte controls which fields are serialised and the resulting record length ---

        // flags = 000: no timestamps; local and central data each hold only the flags byte (1 byte)
        xf.setFlags((byte) 0);
        assertEquals(0, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(1, xf.getLocalFileDataLength().getValue());
        assertEquals(1, xf.getCentralDirectoryLength().getValue());

        // flags = 001: modify only; local and central both carry the modify timestamp (flags + 4 bytes)
        xf.setFlags(MODIFY_TIME_BIT);
        assertEquals(MODIFY_TIME_BIT, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(5, xf.getLocalFileDataLength().getValue());
        assertEquals(5, xf.getCentralDirectoryLength().getValue());

        // flags = 010: access only; local carries the access timestamp; central omits it
        xf.setFlags(ACCESS_TIME_BIT);
        assertEquals(ACCESS_TIME_BIT, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(5, xf.getLocalFileDataLength().getValue());   // flags + 4-byte access
        assertEquals(1, xf.getCentralDirectoryLength().getValue()); // central never stores access time

        // flags = 100: create only; local carries the create timestamp; central omits it
        xf.setFlags(CREATE_TIME_BIT);
        assertEquals(CREATE_TIME_BIT, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(5, xf.getLocalFileDataLength().getValue());   // flags + 4-byte create
        assertEquals(1, xf.getCentralDirectoryLength().getValue()); // central never stores create time

        // flags = 111: all three timestamps present
        xf.setFlags((byte) (MODIFY_TIME_BIT | ACCESS_TIME_BIT | CREATE_TIME_BIT));
        assertEquals(MODIFY_TIME_BIT | ACCESS_TIME_BIT | CREATE_TIME_BIT, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(13, xf.getLocalFileDataLength().getValue());  // flags + 3 × 4-byte fields
        assertEquals(5, xf.getCentralDirectoryLength().getValue()); // central: flags + modify only

        // flags = 11111111 (all bits set): upper bits are legal; only the lower three matter
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
        // A freshly created field should not equal a plain Object and its toString
        // should indicate the extra-field type but list no timestamps.
        assertNotEquals(xf, new Object());
        assertTrue(xf.toString().startsWith("0x5455 Zip Extra Field"));
        assertFalse(xf.toString().contains(" Modify:"));
        assertFalse(xf.toString().contains(" Access:"));
        assertFalse(xf.toString().contains(" Create:"));
        Object o = xf.clone();
        assertEquals(o.hashCode(), xf.hashCode());
        assertEquals(xf, o);

        // After populating all three timestamps, the clone should diverge and toString
        // should include all three timestamp labels.
        xf.setModifyJavaTime(new Date(ARBITRARY_MILLIS_A));
        xf.setAccessJavaTime(new Date(ARBITRARY_MILLIS_B));
        xf.setCreateJavaTime(new Date(ARBITRARY_MILLIS_C));
        // flags must be set explicitly: setters update bits but toString only renders
        // timestamps whose corresponding flag bit is set.
        xf.setFlags((byte) 7);
        assertNotEquals(xf, o);
        assertTrue(xf.toString().startsWith("0x5455 Zip Extra Field"));
        assertTrue(xf.toString().contains(" Modify:"));
        assertTrue(xf.toString().contains(" Access:"));
        assertTrue(xf.toString().contains(" Create:"));
        o = xf.clone();
        assertEquals(o.hashCode(), xf.hashCode());
        assertEquals(xf, o);
    }

    @Test
    void testParseReparse() throws ZipException {
        /*
         * X5455 local-file-data layout (from the spec):
         *
         *   byte[0]    = flags  (bit0 = modify, bit1 = access, bit2 = create)
         *   bytes[1-4] = modify time  (present when bit0 is set)
         *   bytes[5-8] = access time  (present when bit1 is set; local data only)
         *   bytes[9-12]= create time  (present when bit2 is set; local data only)
         *
         * Central-directory data uses the same flags byte but ONLY ever includes
         * the modify timestamp — access and create bytes are omitted.
         */
        final byte[] NULL_FLAGS     = { 0 };
        final byte[] AC_CENTRAL     = { 2 }; // central: access flag recorded but no timestamp bytes
        final byte[] CR_CENTRAL     = { 4 }; // central: create flag recorded but no timestamp bytes

        final byte[] MOD_ZERO       = { 1, 0, 0, 0, 0 };
        final byte[] MOD_MAX        = { 1, -1, -1, -1, 0x7f };
        final byte[] AC_ZERO        = { 2, 0, 0, 0, 0 };
        final byte[] AC_MAX         = { 2, -1, -1, -1, 0x7f };
        final byte[] CR_ZERO        = { 4, 0, 0, 0, 0 };
        final byte[] CR_MAX         = { 4, -1, -1, -1, 0x7f };
        final byte[] MOD_AC_ZERO    = { 3, 0, 0, 0, 0, 0, 0, 0, 0 };
        final byte[] MOD_AC_MAX     = { 3, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };
        final byte[] MOD_AC_CR_ZERO = { 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        final byte[] MOD_AC_CR_MAX  = { 7, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };

        parseReparse(null,             NULL_FLAGS,     NULL_FLAGS);
        parseReparse(ZERO_TIME,        MOD_ZERO,       MOD_ZERO);
        parseReparse(MAX_TIME_SECONDS, MOD_MAX,        MOD_MAX);
        parseReparse(ZERO_TIME,        AC_ZERO,        AC_CENTRAL);
        parseReparse(MAX_TIME_SECONDS, AC_MAX,         AC_CENTRAL);
        parseReparse(ZERO_TIME,        CR_ZERO,        CR_CENTRAL);
        parseReparse(MAX_TIME_SECONDS, CR_MAX,         CR_CENTRAL);
        parseReparse(ZERO_TIME,        MOD_AC_ZERO,    MOD_ZERO);
        parseReparse(MAX_TIME_SECONDS, MOD_AC_MAX,     MOD_MAX);
        parseReparse(ZERO_TIME,        MOD_AC_CR_ZERO, MOD_ZERO);
        parseReparse(MAX_TIME_SECONDS, MOD_AC_CR_MAX,  MOD_MAX);

        // Per the spec (December 2012), only the lower three flag bits are defined.
        // Flags with extra high bits set (15, 31, 63, …) are treated as equivalent to 7 (binary 00000111).
        parseReparse((byte) 15,  MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 31,  MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 63,  MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 71,  MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 127, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) -1,  MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
    }

    @Test
    void testResetsFlagsWhenLocalFileArrayIsTooShort() throws Exception {
        // A flags byte that promises all three timestamps (flags=7) but with no actual
        // timestamp bytes should produce an empty (flags=0) record rather than a parse error.
        final byte[] local = { 7 };
        xf.parseFromLocalFileData(local, 0, 1);
        assertArrayEquals(new byte[1], xf.getLocalFileDataData());
    }

    @Test
    void testSampleFile() throws Exception {
        /*
         * The test archive (COMPRESS-210_unix_time_zip_test.zip) was created with InfoZIP on GMT-8.
         * Each entry is named after the year its timestamps target (Jan 1st, midnight, UTC):
         *   e.g. COMPRESS-210_unix_time_zip_test/1999
         *        COMPRESS-210_unix_time_zip_test/2000
         *        COMPRESS-210_unix_time_zip_test/2108
         *
         * Within each entry the timestamps are staggered by one second:
         *   last-modified = 1st second after midnight  (stored in the X5455 mod-time field)
         *   zip-time      = 2nd second (DOS 2-second granularity rounds up from the 1st second)
         *   last-access   = 3rd second after midnight  (stored in the X5455 access-time field)
         *
         * Different JVM versions interpret the extended-timestamp field differently:
         *   Java 9+ / patched Java 8: zip-time == mod-time (correctly reads the signed 32-bit field)
         *   Early Java 8 on years >= 2038: field is read but treated as unsigned (off by ~136 years)
         *   Java 7 / very early Java 8: extended field is not read; zip-time uses DOS 2-second granularity
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
                    // setTime.sh and similar helper files — not year entries, skip
                    continue;
                }

                final X5455_ExtendedTimestamp xf = (X5455_ExtendedTimestamp) zae.getExtraField(X5455);
                final Date rawZ = zae.getLastModifiedDate();
                final Date m = xf.getModifyJavaTime();

                // Detect which JVM extended-timestamp behaviour we are running under.
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
                    // All three timestamps overflow signed 32 bits well before 2109.
                    if (!zipTimeUsesExtendedTimestamp) {
                        assertEquals("1981-01-01/00:00:02 +0000", zipTime);
                    }
                    break;
                default:
                    if (!zipTimeUsesExtendedTimestamp) {
                        // X5455 timestamps are valid from epoch (1970) through 2037.
                        // ZIP DOS timestamps cover 1980–2107.
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
