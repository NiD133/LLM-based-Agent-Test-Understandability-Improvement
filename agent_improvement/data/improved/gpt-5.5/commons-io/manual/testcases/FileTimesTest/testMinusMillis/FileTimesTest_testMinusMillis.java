package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testMinusMillis {

    @Test
    void testMinusMillis() {
        final int millisToSubtract = 2;

        assertEquals(Instant.EPOCH.minusMillis(millisToSubtract), FileTimes.minusMillis(FileTimes.EPOCH, millisToSubtract).toInstant());
        assertEquals(Instant.EPOCH, FileTimes.minusMillis(FileTimes.EPOCH, 0).toInstant());
    }
}
