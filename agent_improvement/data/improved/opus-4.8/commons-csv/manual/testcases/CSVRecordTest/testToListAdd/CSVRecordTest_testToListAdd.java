package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CSVRecord#toList()} returns a detached, mutable copy:
 * mutating the returned list must not affect the record's own values.
 */
public class CSVRecordTest_testToListAdd {

    /** The values backing the record under test, parsed from a single CSV row. */
    private final String[] recordValues = { "A", "B", "C" };

    private CSVRecord record;

    @BeforeEach
    public void setUp() throws Exception {
        final String csvRow = String.join(",", recordValues);
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(csvRow))) {
            record = parser.iterator().next();
        }
    }

    @Test
    void testToListAdd() {
        final String[] originalValues = recordValues.clone();

        final List<String> list = record.toList();
        list.add("Last");

        // The appended element is present at the end of the returned list...
        assertEquals("Last", list.get(list.size() - 1));
        assertEquals(list.size(), recordValues.length + 1);
        // ...but the record's own values remain untouched.
        assertArrayEquals(originalValues, recordValues);
    }
}
