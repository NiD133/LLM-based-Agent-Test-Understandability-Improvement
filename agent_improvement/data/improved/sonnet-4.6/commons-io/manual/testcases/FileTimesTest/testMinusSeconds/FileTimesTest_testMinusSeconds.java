package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class FileTimesTest_testMinusSeconds {

    @Test
    void testMinusSeconds() {
        final long secondsToSubtract = 2;

        // Subtracting a positive number of seconds shifts the FileTime backward
        Instant expectedShiftedBack = Instant.EPOCH.minusSeconds(secondsToSubtract);
        Instant actualShiftedBack = FileTimes.minusSeconds(FileTimes.EPOCH, secondsToSubtract).toInstant();
        assertEquals(expectedShiftedBack, actualShiftedBack);

        // Subtracting zero seconds leaves the FileTime unchanged
        Instant expectedUnchanged = Instant.EPOCH;
        Instant actualUnchanged = FileTimes.minusSeconds(FileTimes.EPOCH, 0).toInstant();
        assertEquals(expectedUnchanged, actualUnchanged);
    }
}
