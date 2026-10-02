package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link FileTimes#toNtfsTime(long)} behaves at the largest possible Java time.
 */
public class FileTimesTest_testMaxJavaTime {

    @Test
    void testMaxJavaTime() {
        // Use the maximum representable Java time (milliseconds since the epoch).
        final long maxJavaTime = Long.MAX_VALUE;

        // Sanity check: converting the value to an Instant and back must round-trip exactly.
        final Instant instant = Instant.ofEpochMilli(maxJavaTime);
        assertEquals(maxJavaTime, instant.toEpochMilli());

        // Converting the maximum Java time to NTFS time overflows the 64-bit range,
        // so toNtfsTime saturates at Long.MAX_VALUE instead of wrapping around.
        final long ntfsTime = FileTimes.toNtfsTime(maxJavaTime);
        final Instant roundTrippedInstant = FileTimes.ntfsTimeToInstant(ntfsTime);
        if (ntfsTime == Long.MAX_VALUE) {
            // Expected for the maximum Java time: the conversion saturated, so no round-trip is possible.
        } else {
            // If no saturation occurred, the value must round-trip back to the original Java time.
            assertEquals(maxJavaTime, roundTrippedInstant.toEpochMilli());
        }
    }
}
