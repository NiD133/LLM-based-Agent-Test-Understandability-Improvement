package org.apache.commons.csv;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link CSVRecord#toMap()} on a "short" record: one whose number of
 * values is smaller than the number of declared headers.
 */
public class CSVRecordTest_testToMapWithShortRecord {

    /**
     * The input {@code "a,b"} has only two values, but the format declares three
     * headers ({@code A, B, C}). Converting such a short record to a map must not
     * fail, even though the trailing header {@code C} has no corresponding value.
     */
    @Test
    void testToMapWithShortRecord() throws Exception {
        final CSVFormat formatWithExtraHeader = CSVFormat.DEFAULT.withHeader("A", "B", "C");

        try (CSVParser parser = CSVParser.parse("a,b", formatWithExtraHeader)) {
            final CSVRecord shortRecord = parser.iterator().next();

            // Must complete without throwing, despite the missing third value.
            shortRecord.toMap();
        }
    }
}
