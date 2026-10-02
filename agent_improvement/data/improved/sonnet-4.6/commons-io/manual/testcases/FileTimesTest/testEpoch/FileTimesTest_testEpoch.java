package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileTimesTest_testEpoch {

    @Test
    @DisplayName("EPOCH constant represents the Unix epoch (1970-01-01T00:00:00Z) with 0 milliseconds")
    void testEpoch() {
        assertEquals(0, FileTimes.EPOCH.toMillis(),
                "FileTimes.EPOCH should represent the Unix epoch, which is 0 milliseconds since the epoch");
    }
}
