package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CSVRecord} can be iterated over, yielding each parsed
 * value in column order.
 */
public class CSVRecordTest_testIterator {

    /** The expected values, in order, of the single parsed record. */
    private String[] expectedValues;

    /** The record under test, parsed from {@link #expectedValues}. */
    private CSVRecord record;

    @BeforeEach
    public void setUp() throws Exception {
        expectedValues = new String[] { "A", "B", "C" };
        final String csvRow = StringUtils.join(expectedValues, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(csvRow))) {
            record = parser.iterator().next();
        }
    }

    @Test
    void testIterator() {
        int index = 0;
        for (final String value : record) {
            assertEquals(expectedValues[index], value);
            index++;
        }
    }
}
