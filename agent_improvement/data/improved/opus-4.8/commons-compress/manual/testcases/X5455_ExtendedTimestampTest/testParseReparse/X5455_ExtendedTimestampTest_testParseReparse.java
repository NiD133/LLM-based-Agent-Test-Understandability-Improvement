package org.apache.commons.compress.archivers.zip;

import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.ACCESS_TIME_BIT;
import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.CREATE_TIME_BIT;
import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.MODIFY_TIME_BIT;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.zip.ZipException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link X5455_ExtendedTimestamp} can serialize its timestamp data and then parse it
 * back into an equivalent state ("parse / reparse" round trip), for both the local-file-data and the
 * central-directory layouts.
 *
 * <p>Recall the on-disk layout of the 0x5455 ("UT") extra field:</p>
 * <pre>
 * Flags     Byte    info bits: bit0 = modify time, bit1 = access time, bit2 = create time
 * (ModTime) Long    time of last modification (UTC/GMT)   - present when bit0 is set
 * (AcTime)  Long    time of last access       (UTC/GMT)   - present when bit1 is set (local data only)
 * (CrTime)  Long    time of original creation (UTC/GMT)   - present when bit2 is set (local data only)
 * </pre>
 *
 * <p>The central directory only ever carries the modify time, even when the access/create bits are set.</p>
 */
public class X5455_ExtendedTimestampTest_testParseReparse {

    private static final ZipLong ZERO_TIME = new ZipLong(0);

    /** The largest timestamp that still fits in a signed 32 bit integer of seconds. */
    private static final ZipLong MAX_TIME_SECONDS = new ZipLong(Integer.MAX_VALUE);

    /** The extra field under test. */
    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    private static boolean isFlagSet(final byte data, final byte flag) {
        return (data & flag) == flag;
    }

    /**
     * Sets all three timestamps to {@code time}, applies {@code providedFlags}, serializes, then parses
     * the bytes back and asserts the result.
     *
     * <p>The exercise runs twice: once over {@link X5455_ExtendedTimestamp#getLocalFileDataData()} and once
     * over {@link X5455_ExtendedTimestamp#getCentralDirectoryData()}.</p>
     *
     * @param providedFlags         the flags byte we feed in (may contain spurious high bits).
     * @param time                  the timestamp value written to every enabled field.
     * @param expectedFlags         the flags byte we expect to read back (high bits dropped).
     * @param expectedLocal         the expected serialized local-file-data bytes.
     * @param almostExpectedCentral the expected serialized central-directory bytes, except for byte 0
     *                              (the flags), which this method overwrites with {@code expectedFlags}.
     */
    private void parseReparse(final byte providedFlags, final ZipLong time, final byte expectedFlags, final byte[] expectedLocal,
            final byte[] almostExpectedCentral) throws ZipException {
        // The caller can't conveniently set the flags byte of the central data, so we patch it in here.
        final byte[] expectedCentral = almostExpectedCentral.clone();
        expectedCentral[0] = expectedFlags;

        // --- Local file data round trip ---
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

        // --- Central directory data round trip (same inputs) ---
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        xf.setFlags(providedFlags);

        result = xf.getCentralDirectoryData();
        assertArrayEquals(expectedCentral, result);

        xf.parseFromCentralDirectoryData(result, 0, result.length);
        assertEquals(expectedFlags, xf.getFlags());
        // Central data never carries access or create time, but may carry modify time.
        if (isFlagSet(expectedFlags, MODIFY_TIME_BIT)) {
            assertTrue(xf.isBit0_modifyTimePresent());
            assertEquals(time, xf.getModifyTime());
        }
    }

    /**
     * Convenience overload for the common case where the provided and expected flags are identical and equal
     * to the flags byte of the expected local data.
     */
    private void parseReparse(final ZipLong time, final byte[] expectedLocal, final byte[] almostExpectedCentral) throws ZipException {
        parseReparse(expectedLocal[0], time, expectedLocal[0], expectedLocal, almostExpectedCentral);
    }

    @Test
    void testParseReparse() throws ZipException {
        // Each byte[] is the expected serialized form: byte 0 is the flags byte, followed by 4-byte
        // little-endian timestamps for each present field. -1,-1,-1,0x7f encodes Integer.MAX_VALUE.

        // No flags set: a single zero flags byte, no timestamps.
        final byte[] NULL_FLAGS = { 0 };
        // Central data carrying only the access (bit1) flag and no timestamp payload.
        final byte[] AC_CENTRAL = { 2 };
        // Central data carrying only the create (bit2) flag and no timestamp payload.
        final byte[] CR_CENTRAL = { 4 };

        // Single-field local layouts: flags byte + one 4-byte timestamp.
        final byte[] MOD_ZERO = { 1, 0, 0, 0, 0 };
        final byte[] MOD_MAX = { 1, -1, -1, -1, 0x7f };
        final byte[] AC_ZERO = { 2, 0, 0, 0, 0 };
        final byte[] AC_MAX = { 2, -1, -1, -1, 0x7f };
        final byte[] CR_ZERO = { 4, 0, 0, 0, 0 };
        final byte[] CR_MAX = { 4, -1, -1, -1, 0x7f };

        // Two-field local layouts: flags byte + two 4-byte timestamps.
        final byte[] MOD_AC_ZERO = { 3, 0, 0, 0, 0, 0, 0, 0, 0 };
        final byte[] MOD_AC_MAX = { 3, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };

        // Three-field local layouts: flags byte + three 4-byte timestamps.
        final byte[] MOD_AC_CR_ZERO = { 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        final byte[] MOD_AC_CR_MAX = { 7, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };

        // No timestamps at all.
        parseReparse(null, NULL_FLAGS, NULL_FLAGS);

        // Modify time only -> central data mirrors local data.
        parseReparse(ZERO_TIME, MOD_ZERO, MOD_ZERO);
        parseReparse(MAX_TIME_SECONDS, MOD_MAX, MOD_MAX);

        // Access time only -> central keeps the flag but drops the payload.
        parseReparse(ZERO_TIME, AC_ZERO, AC_CENTRAL);
        parseReparse(MAX_TIME_SECONDS, AC_MAX, AC_CENTRAL);

        // Create time only -> central keeps the flag but drops the payload.
        parseReparse(ZERO_TIME, CR_ZERO, CR_CENTRAL);
        parseReparse(MAX_TIME_SECONDS, CR_MAX, CR_CENTRAL);

        // Modify + access -> central keeps only the modify time.
        parseReparse(ZERO_TIME, MOD_AC_ZERO, MOD_ZERO);
        parseReparse(MAX_TIME_SECONDS, MOD_AC_MAX, MOD_MAX);

        // Modify + access + create -> central keeps only the modify time.
        parseReparse(ZERO_TIME, MOD_AC_CR_ZERO, MOD_ZERO);
        parseReparse(MAX_TIME_SECONDS, MOD_AC_CR_MAX, MOD_MAX);

        // As far as the spec is concerned (December 2012) only the low 3 bits are meaningful, so every
        // flags byte below is just a "spurious" variant of 7 (binary 00000111) and must normalize to 7.
        parseReparse((byte) 15, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 31, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 63, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 71, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) 127, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
        parseReparse((byte) -1, MAX_TIME_SECONDS, (byte) 7, MOD_AC_CR_MAX, MOD_MAX);
    }
}
