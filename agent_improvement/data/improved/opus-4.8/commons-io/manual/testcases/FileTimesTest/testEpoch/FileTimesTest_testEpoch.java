package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileTimes#EPOCH}.
 */
public class FileTimesTest_testEpoch {

    /**
     * Verifies that the {@link FileTimes#EPOCH} constant represents the Unix epoch
     * ({@code 1970-01-01T00:00:00Z}), i.e. zero milliseconds since the epoch.
     */
    @Test
    void testEpoch() {
        final long expectedMillisSinceEpoch = 0;
        assertEquals(expectedMillisSinceEpoch, FileTimes.EPOCH.toMillis());
    }
}
