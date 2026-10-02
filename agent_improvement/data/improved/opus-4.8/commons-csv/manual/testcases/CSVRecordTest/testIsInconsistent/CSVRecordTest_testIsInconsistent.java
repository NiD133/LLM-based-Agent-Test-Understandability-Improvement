package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#isConsistent()}.
 *
 * <p>
 * A record is consistent only while the number of values it holds matches the
 * size of the header mapping. This test makes the header larger than the record
 * after the record has been parsed and verifies that {@code isConsistent()}
 * reports the mismatch.
 * </p>
 */
public class CSVRecordTest_testIsInconsistent {

    @Test
    void testIsInconsistent() throws IOException {
        // A row with three values parsed against a matching three-column header.
        final String[] headers = { "first", "second", "third" };
        final String rowData = "A,B,C";

        try (CSVParser parser = CSVFormat.DEFAULT.withHeader(headers).parse(new StringReader(rowData))) {
            final Map<String, Integer> headerMap = parser.getHeaderMapRaw();
            final CSVRecord record = parser.iterator().next();

            // Add a fourth header column so the header (4) no longer matches the
            // record's three values, making the record inconsistent.
            headerMap.put("fourth", Integer.valueOf(4));

            assertFalse(record.isConsistent());
        }
    }
}
