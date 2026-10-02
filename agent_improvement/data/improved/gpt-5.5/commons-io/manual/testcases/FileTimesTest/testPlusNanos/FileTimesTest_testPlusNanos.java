package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testPlusNanos {

    private static final long TWO_NANOSECONDS = 2L;

    @Test
    void testPlusNanos() {
        assertEquals(Instant.EPOCH.plusNanos(TWO_NANOSECONDS),
                FileTimes.plusNanos(FileTimes.EPOCH, TWO_NANOSECONDS).toInstant());
        assertEquals(Instant.EPOCH, FileTimes.plusNanos(FileTimes.EPOCH, 0).toInstant());
    }
}
