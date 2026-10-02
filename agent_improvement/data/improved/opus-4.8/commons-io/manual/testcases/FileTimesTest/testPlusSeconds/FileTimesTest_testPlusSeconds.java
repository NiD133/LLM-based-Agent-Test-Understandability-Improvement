package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileTimes#plusSeconds(java.nio.file.attribute.FileTime, long)}.
 */
public class FileTimesTest_testPlusSeconds {

    /**
     * Verifies that adding seconds to a {@link FileTimes#EPOCH} produces a FileTime
     * whose instant equals the epoch instant advanced by the same number of seconds.
     */
    @Test
    void testPlusSeconds() {
        final int secondsToAdd = 2;

        // Adding a positive number of seconds shifts the instant forward by that amount.
        final Instant expectedAfterAdding = Instant.EPOCH.plusSeconds(secondsToAdd);
        final Instant actualAfterAdding = FileTimes.plusSeconds(FileTimes.EPOCH, secondsToAdd).toInstant();
        assertEquals(expectedAfterAdding, actualAfterAdding);

        // Adding zero seconds leaves the instant unchanged at the epoch.
        final Instant actualAfterAddingZero = FileTimes.plusSeconds(FileTimes.EPOCH, 0).toInstant();
        assertEquals(Instant.EPOCH, actualAfterAddingZero);
    }
}
