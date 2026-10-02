package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class FileTimesTest_testPlusNanos {

    @Test
    void testPlusNanos_positiveOffset_shiftsInstantByThatManyNanos() {
        final long nanosToAdd = 2;
        final Instant expected = Instant.EPOCH.plusNanos(nanosToAdd);

        final FileTime result = FileTimes.plusNanos(FileTimes.EPOCH, nanosToAdd);

        assertEquals(expected, result.toInstant());
    }

    @Test
    void testPlusNanos_zeroOffset_returnsEpoch() {
        final FileTime result = FileTimes.plusNanos(FileTimes.EPOCH, 0);

        assertEquals(Instant.EPOCH, result.toInstant());
    }
}
