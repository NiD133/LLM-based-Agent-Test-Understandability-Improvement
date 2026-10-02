package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Exercises the getters/setters of {@link X5455_ExtendedTimestamp} for the modify, access and create
 * timestamps, plus the flags byte that controls how much data the field serializes.
 */
public class X5455_ExtendedTimestampTest_testGettersSetters {

    /** The largest value (in seconds since epoch) that still fits in a signed 32-bit integer. */
    private static final ZipLong MAX_TIME_SECONDS = new ZipLong(Integer.MAX_VALUE);

    /** The extended timestamp field under test; freshly created before each test. */
    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testGettersSetters() {
        // X5455 is all about time, so pick a fixed timestamp to play with: midnight Jan 1st, 2000 UTC.
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

        // The field stores seconds in 32 signed bits; a Java time one second past the max must be rejected.
        assertThrows(IllegalArgumentException.class,
                () -> xf.setModifyJavaTime(new Date(1000L * (MAX_TIME_SECONDS.getValue() + 1L))),
                "Time too big for 32 bits!");

        // Each timestamp (modify / access / create) shares the same getter/setter contract, so we
        // verify them all through the same helper, wiring up the bit-specific methods via references.
        assertTimestampContract(time, timeMillis,
                xf::setModifyTime, xf::setModifyJavaTime, xf::setModifyFileTime,
                xf::getModifyTime, xf::getModifyJavaTime, xf::getModifyFileTime,
                xf::isBit0_modifyTimePresent);
        assertTimestampContract(time, timeMillis,
                xf::setAccessTime, xf::setAccessJavaTime, xf::setAccessFileTime,
                xf::getAccessTime, xf::getAccessJavaTime, xf::getAccessFileTime,
                xf::isBit1_accessTimePresent);
        assertTimestampContract(time, timeMillis,
                xf::setCreateTime, xf::setCreateJavaTime, xf::setCreateFileTime,
                xf::getCreateTime, xf::getCreateJavaTime, xf::getCreateFileTime,
                xf::isBit2_createTimePresent);

        // Give all three timestamps a value so the flags below decide what actually gets serialized.
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);

        // The flags byte drives which timestamps are present and how long the serialized data is.
        // Local data can hold modify + access + create; central data only ever holds modify.
        // Each field contributes 4 bytes on top of the mandatory 1-byte flags header.
        //
        //                          flags        modify access create  localLen centralLen
        assertFlagsContract((byte) 0,            false, false, false,  1,       1);
        assertFlagsContract((byte) 1,            true,  false, false,  5,       5);
        assertFlagsContract((byte) 2,            false, true,  false,  5,       1);
        assertFlagsContract((byte) 4,            false, false, true,   5,       1);
        assertFlagsContract((byte) 7,            true,  true,  true,   13,      5);
        assertFlagsContract((byte) -1,           true,  true,  true,   13,      5);
    }

    /**
     * Verifies the getter/setter round-trips for a single timestamp slot (modify, access or create).
     * The CUT exposes each timestamp through three equivalent representations - {@link ZipLong} (raw
     * seconds), {@link Date} (Java millis) and {@link FileTime} - all of which must agree, truncate
     * sub-second precision, and clear the presence bit when set to null.
     */
    private void assertTimestampContract(final ZipLong time, final long timeMillis,
            final Consumer<ZipLong> setTime, final Consumer<Date> setJavaTime, final Consumer<FileTime> setFileTime,
            final Supplier<ZipLong> getTime, final Supplier<Date> getJavaTime, final Supplier<FileTime> getFileTime,
            final BooleanSupplier isPresent) {

        // Set via the raw ZipLong: all three representations report the same instant.
        setTime.accept(time);
        assertAllRepresentations(time, timeMillis, getTime, getJavaTime, getFileTime, isPresent);

        // Set via java.util.Date: same result.
        setJavaTime.accept(new Date(timeMillis));
        assertAllRepresentations(time, timeMillis, getTime, getJavaTime, getFileTime, isPresent);

        // Set via Date carrying stray milliseconds: they must be zeroed out (per-second precision).
        setJavaTime.accept(new Date(timeMillis + 123));
        assertAllRepresentations(time, timeMillis, getTime, getJavaTime, getFileTime, isPresent);

        // Set via FileTime carrying stray milliseconds: also truncated to the second.
        setFileTime.accept(FileTime.fromMillis(timeMillis + 123));
        assertAllRepresentations(time, timeMillis, getTime, getJavaTime, getFileTime, isPresent);

        // Clearing through any representation clears the others and unsets the presence bit.
        setTime.accept(null);
        assertNull(getJavaTime.get());
        assertNull(getFileTime.get());
        assertFalse(isPresent.getAsBoolean());

        setJavaTime.accept(null);
        assertNull(getTime.get());
        assertNull(getFileTime.get());
        assertFalse(isPresent.getAsBoolean());

        setFileTime.accept(null);
        assertNull(getJavaTime.get());
        assertNull(getTime.get());
        assertFalse(isPresent.getAsBoolean());
    }

    /**
     * Asserts that the raw, Java-Date and FileTime views of a timestamp all report the same instant
     * and that the presence bit is set.
     */
    private void assertAllRepresentations(final ZipLong time, final long timeMillis,
            final Supplier<ZipLong> getTime, final Supplier<Date> getJavaTime, final Supplier<FileTime> getFileTime,
            final BooleanSupplier isPresent) {
        assertEquals(time, getTime.get());
        assertEquals(timeMillis, getJavaTime.get().getTime());
        assertEquals(timeMillis, getFileTime.get().toMillis());
        assertTrue(isPresent.getAsBoolean());
    }

    /**
     * Sets the flags byte and asserts which presence bits it activates and how long the resulting
     * local-file and central-directory data become.
     */
    private void assertFlagsContract(final byte flags, final boolean modifyPresent, final boolean accessPresent,
            final boolean createPresent, final int localLength, final int centralLength) {
        xf.setFlags(flags);
        assertEquals(flags, xf.getFlags());
        assertEquals(modifyPresent, xf.isBit0_modifyTimePresent());
        assertEquals(accessPresent, xf.isBit1_accessTimePresent());
        assertEquals(createPresent, xf.isBit2_createTimePresent());
        assertEquals(localLength, xf.getLocalFileDataLength().getValue());
        assertEquals(centralLength, xf.getCentralDirectoryLength().getValue());
    }
}
