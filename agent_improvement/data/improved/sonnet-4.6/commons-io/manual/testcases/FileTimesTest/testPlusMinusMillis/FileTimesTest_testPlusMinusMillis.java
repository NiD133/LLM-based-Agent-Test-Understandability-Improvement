package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class FileTimesTest_testPlusMinusMillis {

    @Test
    void testPlusMinusMillis() {
        final int millisToAdd = 2;

        // Adding 2 ms to EPOCH should shift the instant forward by exactly 2 ms
        Instant expectedAfterAdd = Instant.EPOCH.plusMillis(millisToAdd);
        FileTime resultAfterAdd = FileTimes.plusMillis(FileTimes.EPOCH, millisToAdd);
        assertEquals(expectedAfterAdd, resultAfterAdd.toInstant());

        // Adding 0 ms should leave the instant unchanged
        FileTime resultAfterZero = FileTimes.plusMillis(FileTimes.EPOCH, 0);
        assertEquals(Instant.EPOCH, resultAfterZero.toInstant());
    }
}
