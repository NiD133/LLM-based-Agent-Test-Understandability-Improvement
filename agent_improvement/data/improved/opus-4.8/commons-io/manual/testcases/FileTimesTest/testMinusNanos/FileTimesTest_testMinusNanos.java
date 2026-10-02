package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileTimes#minusNanos(java.nio.file.attribute.FileTime, long)}.
 */
public class FileTimesTest_testMinusNanos {

    @Test
    void testMinusNanos() {
        final long nanosToSubtract = 2;

        // Subtracting a positive number of nanoseconds shifts the instant back by that amount.
        assertEquals(Instant.EPOCH.minusNanos(nanosToSubtract),
                FileTimes.minusNanos(FileTimes.EPOCH, nanosToSubtract).toInstant());

        // Subtracting zero nanoseconds leaves the instant unchanged.
        assertEquals(Instant.EPOCH,
                FileTimes.minusNanos(FileTimes.EPOCH, 0).toInstant());
    }
}
