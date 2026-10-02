package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testMinusMillis {

    private static final FileTime EPOCH = FileTimes.EPOCH;

    @Test
    void testMinusMillis_subtractingPositiveAmountShiftsTimeBack() {
        final long millisToSubtract = 2;
        final Instant expected = Instant.EPOCH.minusMillis(millisToSubtract);

        final Instant actual = FileTimes.minusMillis(EPOCH, millisToSubtract).toInstant();

        assertEquals(expected, actual);
    }

    @Test
    void testMinusMillis_subtractingZeroLeavesTimeUnchanged() {
        final Instant actual = FileTimes.minusMillis(EPOCH, 0).toInstant();

        assertEquals(Instant.EPOCH, actual);
    }
}
