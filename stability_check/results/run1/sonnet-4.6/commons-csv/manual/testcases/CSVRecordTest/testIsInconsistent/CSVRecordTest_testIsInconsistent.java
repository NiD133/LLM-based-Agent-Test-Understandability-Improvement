package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that CSVRecord.isConsistent() returns false when the header map has
 * more entries than the record has values.
 */
public class CSVRecordTest_testIsInconsistent {

    // Three column values used across tests
    private String[] values;

    @BeforeEach
    public void setUp() {
        values = new String[] { "A", "B", "C" };
    }

    /**
     * A record is "inconsistent" when the number of headers differs from the
     * number of values. This test verifies that injecting an extra header entry
     * into the shared header map causes isConsistent() to return false.
     */
    @Test
    void testIsInconsistent() throws IOException {
        final String[] headers = { "first", "second", "third" };
        // Join the three values into "A,B,C" for parsing
        final String rowData = StringUtils.join(values, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.withHeader(headers).parse(new StringReader(rowData))) {
            final Map<String, Integer> headerMap = parser.getHeaderMapRaw();
            final CSVRecord record = parser.iterator().next();

            // Add a fourth header while the record still has only three values,
            // making the header count (4) inconsistent with the value count (3).
            headerMap.put("fourth", Integer.valueOf(4));

            assertFalse(record.isConsistent());
        }
    }
}
