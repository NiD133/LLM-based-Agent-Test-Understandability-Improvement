package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.StringReader;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testToListSet {

    private CSVRecord record;

    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
    }

    @Test
    void testToListSet() {
        // Snapshot original values before any mutation
        final String[] originalValues = values.clone();

        // toList() returns a mutable independent copy of the record's values
        final java.util.List<String> list = record.toList();

        // The list size must match the number of values in the record
        assertEquals(values.length, list.size());

        // Mutating the list must not affect the record's underlying values array
        list.set(list.size() - 1, "Last");
        assertEquals("Last", list.get(list.size() - 1));
        assertArrayEquals(originalValues, values);
    }
}
