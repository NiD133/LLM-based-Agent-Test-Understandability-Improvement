package org.apache.commons.compress.archivers.zip;

import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.ACCESS_TIME_BIT;
import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.CREATE_TIME_BIT;
import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.MODIFY_TIME_BIT;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class X5455_ExtendedTimestampTest_testResetsFlagsWhenLocalFileArrayIsTooShort {

    private static final byte ALL_TIMESTAMPS_PRESENT = MODIFY_TIME_BIT | ACCESS_TIME_BIT | CREATE_TIME_BIT;

    private X5455_ExtendedTimestamp extraField;

    @BeforeEach
    public void before() {
        extraField = new X5455_ExtendedTimestamp();
    }

    @Test
    void testResetsFlagsWhenLocalFileArrayIsTooShort() throws Exception {
        final byte[] localFileDataWithFlagsOnly = { ALL_TIMESTAMPS_PRESENT };

        extraField.parseFromLocalFileData(localFileDataWithFlagsOnly, 0, 1);

        assertArrayEquals(new byte[1], extraField.getLocalFileDataData());
    }
}
