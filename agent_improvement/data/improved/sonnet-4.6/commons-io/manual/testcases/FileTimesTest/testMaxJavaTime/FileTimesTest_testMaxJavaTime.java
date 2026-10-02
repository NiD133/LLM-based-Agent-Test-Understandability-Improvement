package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class FileTimesTest_testMaxJavaTime {

    /**
     * Verifies that converting Long.MAX_VALUE (as Java epoch-milliseconds) to NTFS time
     * and back to an Instant preserves the original epoch-millisecond value,
     * unless the NTFS conversion saturates to Long.MAX_VALUE to avoid overflow.
     */
    @Test
    void testMaxJavaTime() {
        final long javaTime = Long.MAX_VALUE;

        // Confirm that Long.MAX_VALUE round-trips through Instant.ofEpochMilli without loss.
        final Instant originalInstant = Instant.ofEpochMilli(javaTime);
        assertEquals(javaTime, originalInstant.toEpochMilli(),
                "Instant.ofEpochMilli should preserve Long.MAX_VALUE");

        // Convert to NTFS time. The implementation saturates to Long.MAX_VALUE to prevent overflow.
        final long ntfsTime = FileTimes.toNtfsTime(javaTime);

        // Only verify the round-trip when no saturation occurred.
        if (ntfsTime != Long.MAX_VALUE) {
            final Instant roundTrippedInstant = FileTimes.ntfsTimeToInstant(ntfsTime);
            assertEquals(javaTime, roundTrippedInstant.toEpochMilli(),
                    "Round-trip through NTFS time should preserve the original epoch-millisecond value");
        }
    }
}
