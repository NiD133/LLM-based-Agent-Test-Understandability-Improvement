package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileTimesTest_testNullFileTimeToNullDate {

    @Test
    @DisplayName("toDate returns null when the FileTime input is null")
    void testNullFileTimeToNullDate() {
        assertNull(FileTimes.toDate(null));
    }
}
