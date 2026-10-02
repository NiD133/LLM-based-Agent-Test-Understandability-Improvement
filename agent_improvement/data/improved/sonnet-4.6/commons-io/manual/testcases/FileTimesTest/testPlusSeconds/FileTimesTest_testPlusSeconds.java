package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class FileTimesTest_testPlusSeconds {

    @Test
    void testPlusSeconds_positiveOffset_advancesTimeByThatManySeconds() {
        final long secondsToAdd = 2;
        final Instant expectedInstant = Instant.EPOCH.plusSeconds(secondsToAdd);

        final FileTime result = FileTimes.plusSeconds(FileTimes.EPOCH, secondsToAdd);

        assertEquals(expectedInstant, result.toInstant(),
            "Adding " + secondsToAdd + " seconds to EPOCH should advance the instant by the same amount");
    }

    @Test
    void testPlusSeconds_zeroOffset_returnsOriginalTime() {
        final Instant expectedInstant = Instant.EPOCH;

        final FileTime result = FileTimes.plusSeconds(FileTimes.EPOCH, 0);

        assertEquals(expectedInstant, result.toInstant(),
            "Adding zero seconds to EPOCH should leave the instant unchanged");
    }
}
