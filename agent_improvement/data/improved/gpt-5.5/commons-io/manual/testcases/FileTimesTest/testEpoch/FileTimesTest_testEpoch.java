package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testEpoch {

    @Test
    void testEpoch() {
        assertEquals(0, FileTimes.EPOCH.toMillis());
    }
}
