package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testMinusNanos {

    @Test
    void testMinusNanos() {
        final int nanosToSubtract = 2;
        final Instant expectedInstant = Instant.EPOCH.minusNanos(nanosToSubtract);
        final Instant actualInstant = FileTimes.minusNanos(FileTimes.EPOCH, nanosToSubtract).toInstant();

        assertEquals(expectedInstant, actualInstant);
        assertEquals(Instant.EPOCH, FileTimes.minusNanos(FileTimes.EPOCH, 0).toInstant());
    }
}
