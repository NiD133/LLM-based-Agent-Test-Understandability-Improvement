package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.zip.ZipException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that X5455_ExtendedTimestamp clears all timestamp-presence flags when
 * the local file data buffer claims timestamps are present but is too short to
 * actually contain any of them.
 */
public class X5455_ExtendedTimestampTest_testResetsFlagsWhenLocalFileArrayIsTooShort {

    // flags byte value 7 = 0b00000111, meaning all three bits are set:
    //   bit0 (MODIFY_TIME_BIT), bit1 (ACCESS_TIME_BIT), bit2 (CREATE_TIME_BIT)
    private static final byte ALL_TIMESTAMPS_CLAIMED_PRESENT = 7;

    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void setUp() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testResetsFlagsWhenLocalFileArrayIsTooShort() throws ZipException {
        // A 1-byte buffer containing only the flags byte (no actual timestamp data).
        // The flags claim all three timestamps are present, but there is no room for
        // the required 4-byte Unix timestamps that would follow.
        final byte[] tooShortLocalData = { ALL_TIMESTAMPS_CLAIMED_PRESENT };

        xf.parseFromLocalFileData(tooShortLocalData, 0, tooShortLocalData.length);

        // Because none of the timestamps could be read, all flags must be cleared.
        // The resulting serialized form should be a single zero byte (flags = 0, no timestamps).
        assertArrayEquals(new byte[1], xf.getLocalFileDataData());
    }
}
