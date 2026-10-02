package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileTimes#minusMillis(java.nio.file.attribute.FileTime, long)}.
 */
public class FileTimesTest_testMinusMillis {

    @Test
    void testMinusMillis() {
        final int millisToSubtract = 2;

        // Subtracting a positive amount moves the time backwards by that many milliseconds.
        final Instant expectedAfterSubtracting = Instant.EPOCH.minusMillis(millisToSubtract);
        final Instant actualAfterSubtracting = FileTimes.minusMillis(FileTimes.EPOCH, millisToSubtract).toInstant();
        assertEquals(expectedAfterSubtracting, actualAfterSubtracting);

        // Subtracting zero leaves the time unchanged.
        final Instant actualAfterSubtractingZero = FileTimes.minusMillis(FileTimes.EPOCH, 0).toInstant();
        assertEquals(Instant.EPOCH, actualAfterSubtractingZero);
    }
}
