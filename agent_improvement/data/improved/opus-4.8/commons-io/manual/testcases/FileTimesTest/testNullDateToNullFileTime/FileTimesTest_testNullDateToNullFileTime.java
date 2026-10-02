package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileTimes#toFileTime(Date)} for its documented null-handling
 * contract: converting a {@code null} Date must yield a {@code null} FileTime.
 */
public class FileTimesTest_testNullDateToNullFileTime {

    @Test
    void testNullDateToNullFileTime() {
        final Date nullDate = null;

        // A null input Date must convert to a null FileTime.
        assertNull(FileTimes.toFileTime(nullDate));
    }
}
