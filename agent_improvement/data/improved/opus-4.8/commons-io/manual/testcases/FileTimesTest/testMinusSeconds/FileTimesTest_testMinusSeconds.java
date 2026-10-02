package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileTimes#minusSeconds(java.nio.file.attribute.FileTime, long)}.
 */
public class FileTimesTest_testMinusSeconds {

    /**
     * Subtracting seconds from a {@link FileTime} should shift the corresponding {@link Instant}
     * back by the same number of seconds, and subtracting zero should leave it unchanged.
     */
    @Test
    void testMinusSeconds() {
        final int secondsToSubtract = 2;

        // Subtracting N seconds from the epoch yields an instant N seconds before the epoch.
        final Instant expectedAfterSubtraction = Instant.EPOCH.minusSeconds(secondsToSubtract);
        final Instant actualAfterSubtraction = FileTimes.minusSeconds(FileTimes.EPOCH, secondsToSubtract).toInstant();
        assertEquals(expectedAfterSubtraction, actualAfterSubtraction);

        // Subtracting zero seconds leaves the epoch unchanged.
        final Instant actualAfterNoChange = FileTimes.minusSeconds(FileTimes.EPOCH, 0).toInstant();
        assertEquals(Instant.EPOCH, actualAfterNoChange);
    }
}
