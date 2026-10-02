package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testMinusNanos {

    @Test
    void testMinusNanos_subtractsPositiveNanos() {
        final long nanosToSubtract = 2;
        final Instant expected = Instant.EPOCH.minusNanos(nanosToSubtract);

        final FileTime result = FileTimes.minusNanos(FileTimes.EPOCH, nanosToSubtract);

        assertEquals(expected, result.toInstant());
    }

    @Test
    void testMinusNanos_subtractingZeroLeavesEpochUnchanged() {
        final FileTime result = FileTimes.minusNanos(FileTimes.EPOCH, 0);

        assertEquals(Instant.EPOCH, result.toInstant());
    }
}
