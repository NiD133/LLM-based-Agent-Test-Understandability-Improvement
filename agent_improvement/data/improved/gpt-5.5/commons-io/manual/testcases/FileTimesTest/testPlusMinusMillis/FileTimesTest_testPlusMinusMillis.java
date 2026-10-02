package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testPlusMinusMillis {

    @Test
    void testPlusMinusMillis() {
        final int millis = 2;
        final Instant expectedEpochPlusMillis = Instant.EPOCH.plusMillis(millis);
        final Instant actualEpochPlusMillis = FileTimes.plusMillis(FileTimes.EPOCH, millis).toInstant();

        assertEquals(expectedEpochPlusMillis, actualEpochPlusMillis);

        final Instant actualEpochPlusZeroMillis = FileTimes.plusMillis(FileTimes.EPOCH, 0).toInstant();

        assertEquals(Instant.EPOCH, actualEpochPlusZeroMillis);
    }
}
