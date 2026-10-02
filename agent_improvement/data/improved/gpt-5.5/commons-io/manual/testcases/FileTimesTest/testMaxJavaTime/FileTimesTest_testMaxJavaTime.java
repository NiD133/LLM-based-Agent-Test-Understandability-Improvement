package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testMaxJavaTime {

    @Test
    void testMaxJavaTime() {
        final long maxJavaTimeMillis = Long.MAX_VALUE;
        final Instant maxJavaInstant = Instant.ofEpochMilli(maxJavaTimeMillis);

        assertEquals(maxJavaTimeMillis, maxJavaInstant.toEpochMilli());

        final long ntfsTime = FileTimes.toNtfsTime(maxJavaTimeMillis);
        final Instant roundTrippedInstant = FileTimes.ntfsTimeToInstant(ntfsTime);
        final boolean ntfsTimeWasClamped = ntfsTime == Long.MAX_VALUE;

        if (!ntfsTimeWasClamped) {
            assertEquals(maxJavaTimeMillis, roundTrippedInstant.toEpochMilli());
        }
    }
}
