package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileTimes#plusNanos(java.nio.file.attribute.FileTime, long)}.
 *
 * <p>The method should add the given number of nanoseconds to a {@link java.nio.file.attribute.FileTime},
 * mirroring {@link Instant#plusNanos(long)} on the underlying instant.</p>
 */
public class FileTimesTest_testPlusNanos {

    @Test
    void testPlusNanos() {
        // Adding a positive number of nanoseconds shifts the instant forward by that amount.
        final long nanosToAdd = 2;
        assertEquals(
            Instant.EPOCH.plusNanos(nanosToAdd),
            FileTimes.plusNanos(FileTimes.EPOCH, nanosToAdd).toInstant());

        // Adding zero nanoseconds leaves the instant unchanged.
        assertEquals(
            Instant.EPOCH,
            FileTimes.plusNanos(FileTimes.EPOCH, 0).toInstant());
    }
}
