package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CSVRecord#toList()} returns the record's values
 * in their original column order.
 */
public class CSVRecordTest_testToListFor {

    /** The expected values, in column order, of the single parsed record. */
    private static final String[] EXPECTED_VALUES = { "A", "B", "C" };

    /** The record parsed from the CSV row "A,B,C". */
    private CSVRecord record;

    @BeforeEach
    public void setUp() throws Exception {
        final String csvRow = String.join(",", EXPECTED_VALUES);
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(csvRow))) {
            record = parser.iterator().next();
        }
    }

    @Test
    void testToListFor() {
        int columnIndex = 0;
        for (final String value : record.toList()) {
            assertEquals(EXPECTED_VALUES[columnIndex], value);
            columnIndex++;
        }
    }
}
