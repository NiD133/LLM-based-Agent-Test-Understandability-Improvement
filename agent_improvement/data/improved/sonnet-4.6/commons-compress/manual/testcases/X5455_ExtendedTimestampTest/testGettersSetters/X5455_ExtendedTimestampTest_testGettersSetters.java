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

public class X5455_ExtendedTimestampTest_testGettersSetters {

    private static final ZipLong MAX_TIME_SECONDS = new ZipLong(Integer.MAX_VALUE);

    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    /** Returns milliseconds since epoch for midnight UTC on January 1st, 2000. */
    private static long jan1st2000Millis() {
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * Verifies that all three setter forms (ZipLong, Date, FileTime) correctly store and retrieve
     * a timestamp, truncate sub-second precision, and that setting null clears the timestamp and
     * its presence flag.
     */
    private void assertTimestampGettersAndSetters(
            ZipLong expectedZipLong, long expectedMillis,
            Consumer<ZipLong> setZipLong, Supplier<ZipLong> getZipLong,
            Consumer<Date> setJavaTime, Supplier<Date> getJavaTime,
            Consumer<FileTime> setFileTime, Supplier<FileTime> getFileTime,
            BooleanSupplier isBitPresent) {

        // Set via ZipLong
        setZipLong.accept(expectedZipLong);
        assertEquals(expectedZipLong, getZipLong.get());
        assertEquals(expectedMillis, getJavaTime.get().getTime());
        assertEquals(expectedMillis, getFileTime.get().toMillis());
        assertTrue(isBitPresent.getAsBoolean());

        // Set via Date (exact second boundary)
        setJavaTime.accept(new Date(expectedMillis));
        assertEquals(expectedZipLong, getZipLong.get());
        assertEquals(expectedMillis, getJavaTime.get().getTime());
        assertEquals(expectedMillis, getFileTime.get().toMillis());
        assertTrue(isBitPresent.getAsBoolean());

        // Set via Date with sub-second milliseconds — must be truncated to whole seconds
        setJavaTime.accept(new Date(expectedMillis + 123));
        assertEquals(expectedZipLong, getZipLong.get());
        assertEquals(expectedMillis, getJavaTime.get().getTime());
        assertEquals(expectedMillis, getFileTime.get().toMillis());
        assertTrue(isBitPresent.getAsBoolean());

        // Set via FileTime with sub-second milliseconds — must be truncated to whole seconds
        setFileTime.accept(FileTime.fromMillis(expectedMillis + 123));
        assertEquals(expectedZipLong, getZipLong.get());
        assertEquals(expectedMillis, getJavaTime.get().getTime());
        assertEquals(expectedMillis, getFileTime.get().toMillis());
        assertTrue(isBitPresent.getAsBoolean());

        // Null via ZipLong — clears all representations and the presence flag
        setZipLong.accept(null);
        assertNull(getJavaTime.get());
        assertNull(getFileTime.get());
        assertFalse(isBitPresent.getAsBoolean());

        // Null via Date — clears all representations and the presence flag
        setJavaTime.accept(null);
        assertNull(getZipLong.get());
        assertNull(getFileTime.get());
        assertFalse(isBitPresent.getAsBoolean());

        // Null via FileTime — clears all representations and the presence flag
        setFileTime.accept(null);
        assertNull(getJavaTime.get());
        assertNull(getZipLong.get());
        assertFalse(isBitPresent.getAsBoolean());
    }

    @Test
    void testGettersSetters() {
        final long timeMillis = jan1st2000Millis();
        final ZipLong time = new ZipLong(timeMillis / 1000);

        // Timestamps beyond 32-bit signed integer range must be rejected
        assertThrows(IllegalArgumentException.class,
                () -> xf.setModifyJavaTime(new Date(1000L * (MAX_TIME_SECONDS.getValue() + 1L))));

        // Verify modify time setters and getters (bit 0)
        assertTimestampGettersAndSetters(time, timeMillis,
                xf::setModifyTime, xf::getModifyTime,
                xf::setModifyJavaTime, xf::getModifyJavaTime,
                xf::setModifyFileTime, xf::getModifyFileTime,
                xf::isBit0_modifyTimePresent);

        // Verify access time setters and getters (bit 1)
        assertTimestampGettersAndSetters(time, timeMillis,
                xf::setAccessTime, xf::getAccessTime,
                xf::setAccessJavaTime, xf::getAccessJavaTime,
                xf::setAccessFileTime, xf::getAccessFileTime,
                xf::isBit1_accessTimePresent);

        // Verify create time setters and getters (bit 2)
        assertTimestampGettersAndSetters(time, timeMillis,
                xf::setCreateTime, xf::getCreateTime,
                xf::setCreateJavaTime, xf::getCreateJavaTime,
                xf::setCreateFileTime, xf::getCreateFileTime,
                xf::isBit2_createTimePresent);

        // Populate all three timestamps to make flag interactions observable
        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);

        // flags=000: no timestamps active — both local and central hold only the 1-byte flags field
        xf.setFlags((byte) 0);
        assertEquals(0, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(1, xf.getLocalFileDataLength().getValue());
        assertEquals(1, xf.getCentralDirectoryLength().getValue());

        // flags=001: only modify time — local and central both carry the 4-byte modify timestamp
        xf.setFlags((byte) 1);
        assertEquals(1, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(5, xf.getLocalFileDataLength().getValue());
        assertEquals(5, xf.getCentralDirectoryLength().getValue());

        // flags=010: only access time — local carries it, but central directory never stores access time
        xf.setFlags((byte) 2);
        assertEquals(2, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertFalse(xf.isBit2_createTimePresent());
        assertEquals(5, xf.getLocalFileDataLength().getValue());
        assertEquals(1, xf.getCentralDirectoryLength().getValue());

        // flags=100: only create time — local carries it, but central directory never stores create time
        xf.setFlags((byte) 4);
        assertEquals(4, xf.getFlags());
        assertFalse(xf.isBit0_modifyTimePresent());
        assertFalse(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(5, xf.getLocalFileDataLength().getValue());
        assertEquals(1, xf.getCentralDirectoryLength().getValue());

        // flags=111: all three timestamps — local holds all three (13 bytes), central holds only modify (5 bytes)
        xf.setFlags((byte) 7);
        assertEquals(7, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(13, xf.getLocalFileDataLength().getValue());
        assertEquals(5, xf.getCentralDirectoryLength().getValue());

        // flags=11111111: upper bits are ignored; only the low 3 bits determine which timestamps are active
        xf.setFlags((byte) -1);
        assertEquals(-1, xf.getFlags());
        assertTrue(xf.isBit0_modifyTimePresent());
        assertTrue(xf.isBit1_accessTimePresent());
        assertTrue(xf.isBit2_createTimePresent());
        assertEquals(13, xf.getLocalFileDataLength().getValue());
        assertEquals(5, xf.getCentralDirectoryLength().getValue());
    }
}
