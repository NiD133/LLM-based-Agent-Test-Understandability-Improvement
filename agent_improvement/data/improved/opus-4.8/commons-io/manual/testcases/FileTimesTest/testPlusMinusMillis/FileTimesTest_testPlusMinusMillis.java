package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileTimes#plusMillis(java.nio.file.attribute.FileTime, long)}.
 */
public class FileTimesTest_testPlusMinusMillis {

    @Test
    void testPlusMinusMillis() {
        // Adding a positive number of milliseconds to the epoch FileTime
        // should move the resulting instant forward by exactly that amount.
        final int millisToAdd = 2;
        assertEquals(
            Instant.EPOCH.plusMillis(millisToAdd),
            FileTimes.plusMillis(FileTimes.EPOCH, millisToAdd).toInstant());

        // Adding zero milliseconds should leave the epoch instant unchanged.
        assertEquals(
            Instant.EPOCH,
            FileTimes.plusMillis(FileTimes.EPOCH, 0).toInstant());
    }
}
