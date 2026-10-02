package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testToListAdd {

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

    /**
     * Verifies that toList() returns a mutable list and that mutating it
     * does not affect the original record values.
     */
    @Test
    void testToListAdd() {
        final String[] originalValues = values.clone();

        final List<String> list = record.toList();
        list.add("Last");

        assertEquals("Last", list.get(list.size() - 1));
        assertEquals(values.length + 1, list.size());
        assertArrayEquals(originalValues, values);
    }
}
