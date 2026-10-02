package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileTimesTest_testNullFileTimeToNullDate {

    @Test
    @DisplayName("toDate(null) returns null — null FileTime input maps to null Date output")
    void testNullFileTimeToNullDate() {
        assertNull(FileTimes.toDate(null));
    }
}
