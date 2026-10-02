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

    // -----------------------------------------------------------------------
    // Byte-level representations of the X5455 extra field data.
    //
    // The local-file-data format is:
    //   byte[0]      = flags  (bit0=modify, bit1=access, bit2=create)
    //   bytes[1..4]  = modify time  (present only when bit0 is set)
    //   bytes[5..8]  = access time  (present only when bit1 is set)
    //   bytes[9..12] = create time  (present only when bit2 is set)
    //
    // The central-directory format is identical except it never carries
    // access or create timestamps — only the flags byte (and optionally
    // the modify timestamp when bit0 is set).
    // -----------------------------------------------------------------------

    /** No timestamps present; flags byte is zero. */
    private static final byte[] NO_TIMESTAMPS = { 0 };

    /**
     * Central-directory payload when only the access-time bit is set.
     * The CD omits the actual access-time value, so only the flag byte remains.
     */
    private static final byte[] ACCESS_ONLY_CENTRAL = { 2 };

    /**
     * Central-directory payload when only the create-time bit is set.
     * The CD omits the actual create-time value, so only the flag byte remains.
     */
    private static final byte[] CREATE_ONLY_CENTRAL = { 4 };

    // --- single-timestamp local payloads (flags byte + 4-byte Unix timestamp) ---

    /** Local payload: modify-time bit set, timestamp = 0 (Unix epoch). */
    private static final byte[] MODIFY_ZERO       = { 1, 0, 0, 0, 0 };

    /** Local payload: modify-time bit set, timestamp = Integer.MAX_VALUE. */
    private static final byte[] MODIFY_MAX        = { 1, -1, -1, -1, 0x7f };

    /** Local payload: access-time bit set, timestamp = 0. */
    private static final byte[] ACCESS_ZERO       = { 2, 0, 0, 0, 0 };

    /** Local payload: access-time bit set, timestamp = Integer.MAX_VALUE. */
    private static final byte[] ACCESS_MAX        = { 2, -1, -1, -1, 0x7f };

    /** Local payload: create-time bit set, timestamp = 0. */
    private static final byte[] CREATE_ZERO       = { 4, 0, 0, 0, 0 };

    /** Local payload: create-time bit set, timestamp = Integer.MAX_VALUE. */
    private static final byte[] CREATE_MAX        = { 4, -1, -1, -1, 0x7f };

    // --- two-timestamp local payloads (flags byte + 8 bytes of timestamps) ---

    /** Local payload: modify + access bits set, both timestamps = 0. */
    private static final byte[] MODIFY_ACCESS_ZERO = { 3, 0, 0, 0, 0, 0, 0, 0, 0 };

    /** Local payload: modify + access bits set, both timestamps = Integer.MAX_VALUE. */
    private static final byte[] MODIFY_ACCESS_MAX  = { 3, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };

    // --- three-timestamp local payloads (flags byte + 12 bytes of timestamps) ---

    /** Local payload: modify + access + create bits set, all timestamps = 0. */
    private static final byte[] MODIFY_ACCESS_CREATE_ZERO = { 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    /** Local payload: modify + access + create bits set, all timestamps = Integer.MAX_VALUE. */
    private static final byte[] MODIFY_ACCESS_CREATE_MAX  = { 7, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };

    // -----------------------------------------------------------------------

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

    /**
     * Core round-trip helper: configure the extra field with the given flags and timestamp, serialize it to local-file-data and central-directory bytes,
     * assert the bytes match the expected arrays, then re-parse the bytes and assert that the flags and timestamps survive the round-trip intact.
     *
     * @param providedFlags       the raw flags byte to pass to {@code xf.setFlags()}
     * @param time                the timestamp to store for modify, access, and create slots
     * @param expectedFlags       the flags byte we expect after re-parsing (upper bits may be masked off)
     * @param expectedLocal       the exact byte array expected from {@code getLocalFileDataData()}
     * @param almostExpectedCentral the expected central-directory bytes (index 0 will be overwritten with {@code expectedFlags})
     */
    private void parseReparse(final byte providedFlags, final ZipLong time, final byte expectedFlags,
                              final byte[] expectedLocal, final byte[] almostExpectedCentral) throws ZipException {
        // Build the expected central-directory array with the correct flags byte.
        final byte[] expectedCentral = new byte[almostExpectedCentral.length];
        System.arraycopy(almostExpectedCentral, 0, expectedCentral, 0, almostExpectedCentral.length);
        expectedCentral[0] = expectedFlags;

        // --- Local-file-data round-trip ---
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        xf.setFlags(providedFlags);

        byte[] serializedLocal = xf.getLocalFileDataData();
        assertArrayEquals(expectedLocal, serializedLocal);

        xf.parseFromLocalFileData(serializedLocal, 0, serializedLocal.length);
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

        // --- Central-directory round-trip ---
        // Central directory never includes access or create timestamps, but may include modify.
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        xf.setFlags(providedFlags);

        byte[] serializedCentral = xf.getCentralDirectoryData();
        assertArrayEquals(expectedCentral, serializedCentral);

        xf.parseFromCentralDirectoryData(serializedCentral, 0, serializedCentral.length);
        assertEquals(expectedFlags, xf.getFlags());
        if (isFlagSet(expectedFlags, MODIFY_TIME_BIT)) {
            assertTrue(xf.isBit0_modifyTimePresent());
            assertEquals(time, xf.getModifyTime());
        }
    }

    /**
     * Convenience overload that derives the provided/expected flags from the first byte of {@code expectedLocal}.
     */
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
     * Verifies that each combination of timestamp flags and time values round-trips correctly through both the local-file-data and central-directory
     * serialization paths defined by the X5455 ("UT") ZIP extra-field spec.
     *
     * <p>The X5455 format (local-file-data variant):
     * <pre>
     * 0x5455   Short  tag ("UT")
     * TSize    Short  total data size
     * Flags    Byte   info bits (bit0=modify, bit1=access, bit2=create)
     * (ModTime) Long  time of last modification (UTC, seconds since epoch)
     * (AcTime)  Long  time of last access       (UTC, seconds since epoch)
     * (CrTime)  Long  time of original creation (UTC, seconds since epoch)
     * </pre>
     * The central-directory variant omits access and create timestamps.
     */
    @Test
    void testParseReparse() throws ZipException {

        // --- No timestamps ---
        parseReparse(null, NO_TIMESTAMPS, NO_TIMESTAMPS);

        // --- Single modify-time timestamp ---
        parseReparse(ZERO_TIME,        MODIFY_ZERO, MODIFY_ZERO);
        parseReparse(MAX_TIME_SECONDS, MODIFY_MAX,  MODIFY_MAX);

        // --- Single access-time timestamp ---
        // Central directory stores only the flags byte for access time (no data).
        parseReparse(ZERO_TIME,        ACCESS_ZERO, ACCESS_ONLY_CENTRAL);
        parseReparse(MAX_TIME_SECONDS, ACCESS_MAX,  ACCESS_ONLY_CENTRAL);

        // --- Single create-time timestamp ---
        // Central directory stores only the flags byte for create time (no data).
        parseReparse(ZERO_TIME,        CREATE_ZERO, CREATE_ONLY_CENTRAL);
        parseReparse(MAX_TIME_SECONDS, CREATE_MAX,  CREATE_ONLY_CENTRAL);

        // --- Modify + access timestamps ---
        // Central directory keeps only the modify timestamp; access is dropped.
        parseReparse(ZERO_TIME,        MODIFY_ACCESS_ZERO, MODIFY_ZERO);
        parseReparse(MAX_TIME_SECONDS, MODIFY_ACCESS_MAX,  MODIFY_MAX);

        // --- Modify + access + create timestamps ---
        // Central directory keeps only the modify timestamp; access and create are dropped.
        parseReparse(ZERO_TIME,        MODIFY_ACCESS_CREATE_ZERO, MODIFY_ZERO);
        parseReparse(MAX_TIME_SECONDS, MODIFY_ACCESS_CREATE_MAX,  MODIFY_MAX);

        // --- Spurious high bits in flags are silently masked to the lower 3 bits ---
        // According to the spec (December 2012) only bits 0-2 are defined.
        // Any flag value with bits 0-2 all set (i.e. 0x07 after masking) should
        // behave the same as flags = 7 (all three timestamps present).
        parseReparse((byte) 15,  MAX_TIME_SECONDS, (byte) 7, MODIFY_ACCESS_CREATE_MAX, MODIFY_MAX);
        parseReparse((byte) 31,  MAX_TIME_SECONDS, (byte) 7, MODIFY_ACCESS_CREATE_MAX, MODIFY_MAX);
        parseReparse((byte) 63,  MAX_TIME_SECONDS, (byte) 7, MODIFY_ACCESS_CREATE_MAX, MODIFY_MAX);
        parseReparse((byte) 71,  MAX_TIME_SECONDS, (byte) 7, MODIFY_ACCESS_CREATE_MAX, MODIFY_MAX);
        parseReparse((byte) 127, MAX_TIME_SECONDS, (byte) 7, MODIFY_ACCESS_CREATE_MAX, MODIFY_MAX);
        parseReparse((byte) -1,  MAX_TIME_SECONDS, (byte) 7, MODIFY_ACCESS_CREATE_MAX, MODIFY_MAX);
    }
}
