package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#isConsistent()}.
 *
 * <p>
 * A record is consistent only while the number of header columns matches the
 * number of parsed values. This test forces an inconsistency by adding an extra
 * header entry after parsing, so the header count (4) no longer matches the
 * record's value count (3).
 * </p>
 */
public class CSVRecordTest_testIsInconsistent {

    /** The three values used to build the single CSV row under test. */
    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
    }

    @Test
    void testIsInconsistent() throws IOException {
        final String[] headers = { "first", "second", "third" };
        final String rowData = StringUtils.join(values, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.withHeader(headers).parse(new StringReader(rowData))) {
            // The record starts out consistent: 3 headers, 3 values.
            final CSVRecord record = parser.iterator().next();

            // Add a fourth header so the header count no longer matches the value count.
            final Map<String, Integer> headerMap = parser.getHeaderMapRaw();
            headerMap.put("fourth", Integer.valueOf(4));

            assertFalse(record.isConsistent(), "Record should be inconsistent once the header count exceeds the value count");
        }
    }
}
