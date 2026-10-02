package org.apache.commons.csv;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CSVRecord#toMap()} handles a record that has fewer values
 * than the number of declared header columns without throwing an exception.
 */
public class CSVRecordTest_testToMapWithShortRecord {

    @Test
    void testToMapWithShortRecord() throws Exception {
        // "a,b" has only 2 values, but the header declares 3 columns ("A", "B", "C").
        // toMap() must silently omit the missing column rather than throwing.
        try (CSVParser parser = CSVParser.parse("a,b", CSVFormat.DEFAULT.withHeader("A", "B", "C"))) {
            final CSVRecord shortRec = parser.iterator().next();
            shortRec.toMap();
        }
    }
}
