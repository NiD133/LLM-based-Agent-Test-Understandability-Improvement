package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testMinusSeconds {

    @Test
    void testMinusSeconds() {
        final int secondsToSubtract = 2;
        final Instant twoSecondsBeforeEpoch = Instant.EPOCH.minusSeconds(secondsToSubtract);

        assertEquals(twoSecondsBeforeEpoch, FileTimes.minusSeconds(FileTimes.EPOCH, secondsToSubtract).toInstant());
        assertEquals(Instant.EPOCH, FileTimes.minusSeconds(FileTimes.EPOCH, 0).toInstant());
    }
}
