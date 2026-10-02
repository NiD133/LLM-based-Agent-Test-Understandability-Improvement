package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testPlusSeconds {

    @Test
    void testPlusSeconds() {
        final int seconds = 2;
        final Instant expectedTwoSecondsAfterEpoch = Instant.EPOCH.plusSeconds(seconds);
        final Instant actualTwoSecondsAfterEpoch = FileTimes.plusSeconds(FileTimes.EPOCH, seconds).toInstant();
        assertEquals(expectedTwoSecondsAfterEpoch, actualTwoSecondsAfterEpoch);

        final Instant actualEpochWithNoSecondsAdded = FileTimes.plusSeconds(FileTimes.EPOCH, 0).toInstant();
        assertEquals(Instant.EPOCH, actualEpochWithNoSecondsAdded);
    }
}
