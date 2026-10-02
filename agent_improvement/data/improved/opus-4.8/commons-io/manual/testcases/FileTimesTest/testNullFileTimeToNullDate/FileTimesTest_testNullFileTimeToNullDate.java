package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.attribute.FileTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileTimes#toDate(FileTime)} for the null-input edge case.
 */
public class FileTimesTest_testNullFileTimeToNullDate {

    /**
     * Converting a {@code null} {@link FileTime} should yield a {@code null} {@link Date}.
     */
    @Test
    void testNullFileTimeToNullDate() {
        final Date result = FileTimes.toDate(null);

        assertNull(result, "Converting a null FileTime should return a null Date");
    }
}
