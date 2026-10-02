package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CSVRecord#toList()} returns an independent, mutable list:
 * editing the returned list must not change the record's backing values.
 */
public class CSVRecordTest_testToListSet {

    /** The raw values backing the record under test. */
    private String[] values;

    /** A record parsed from the {@link #values} above (no header). */
    private CSVRecord record;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
        final String rowData = String.join(",", values);
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
    }

    @Test
    void testToListSet() {
        // Snapshot the original values so we can later confirm they were not touched.
        final String[] expectedUnchangedValues = values.clone();

        final List<String> list = record.toList();

        // Mutating the returned list should affect only the list copy.
        final int lastIndex = list.size() - 1;
        list.set(lastIndex, "Last");

        assertEquals("Last", list.get(lastIndex), "the list copy should reflect the update");
        assertEquals(list.size(), values.length, "toList() should preserve the number of values");
        assertArrayEquals(expectedUnchangedValues, values, "the record's backing values must remain unchanged");
    }
}
