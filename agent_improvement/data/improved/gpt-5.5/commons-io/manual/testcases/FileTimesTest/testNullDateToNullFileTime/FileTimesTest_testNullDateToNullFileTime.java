package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class FileTimesTest_testNullDateToNullFileTime {

    @Test
    void testNullDateToNullFileTime() {
        assertNull(FileTimes.toFileTime(null));
    }
}
