package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class X5455_ExtendedTimestampTest_testResetsFlagsWhenLocalFileArrayIsTooShort {

    /** The extended-timestamp extra field under test. */
    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testResetsFlagsWhenLocalFileArrayIsTooShort() throws Exception {
        // The flags byte 7 (bits 0, 1 and 2) claims that all three timestamps
        // (modify, access and create) are present, but the array is only a
        // single byte long, so none of the four-byte timestamps actually follow.
        final byte[] flagsOnlyButClaimsAllThreeTimestamps = { 7 };

        xf.parseFromLocalFileData(flagsOnlyButClaimsAllThreeTimestamps, 0, 1);

        // Parsing must clear every flag whose timestamp was missing, so the
        // re-serialized local data shrinks back to a single, all-zero flags byte.
        assertArrayEquals(new byte[1], xf.getLocalFileDataData());
    }
}
