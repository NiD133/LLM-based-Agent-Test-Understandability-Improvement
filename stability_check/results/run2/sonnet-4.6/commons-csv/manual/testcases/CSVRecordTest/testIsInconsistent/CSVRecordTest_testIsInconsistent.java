package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testIsInconsistent {

    // Three values that will be parsed as a single CSV record
    private static final String[] RECORD_VALUES = { "A", "B", "C" };

    @Test
    void testIsInconsistent() throws IOException {
        final String[] headers = { "first", "second", "third" };
        final String rowData = StringUtils.join(RECORD_VALUES, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.withHeader(headers).parse(new StringReader(rowData))) {
            // Obtain the live header map and the parsed record before mutating
            final Map<String, Integer> headerMap = parser.getHeaderMapRaw();
            final CSVRecord record = parser.iterator().next();

            // Inject an extra header entry so the header count (4) exceeds the record's
            // value count (3), making the record inconsistent
            headerMap.put("fourth", Integer.valueOf(4));

            assertFalse(record.isConsistent());
        }
    }
}
