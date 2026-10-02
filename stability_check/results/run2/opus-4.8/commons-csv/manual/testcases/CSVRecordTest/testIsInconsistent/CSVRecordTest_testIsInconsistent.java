package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CSVRecord#isConsistent()} detects a record whose value
 * count no longer matches the parser's header mapping.
 */
public class CSVRecordTest_testIsInconsistent {

    @Test
    void testIsInconsistent() throws IOException {
        // Parse a single row that lines up one-to-one with a three-column header.
        final String[] headers = { "first", "second", "third" };
        final String rowData = "A,B,C";

        try (CSVParser parser = CSVFormat.DEFAULT.withHeader(headers).parse(new StringReader(rowData))) {
            final CSVRecord record = parser.iterator().next();

            // At this point header size (3) equals the record's value count (3).
            // Add an extra header entry so the mapping no longer matches the values.
            final Map<String, Integer> headerMap = parser.getHeaderMapRaw();
            headerMap.put("fourth", Integer.valueOf(4));

            // The record now has 3 values but 4 mapped columns, so it is inconsistent.
            assertFalse(record.isConsistent());
        }
    }
}
