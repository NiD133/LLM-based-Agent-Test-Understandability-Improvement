package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#get(int)}: a value can be retrieved by its 0-based column index.
 */
public class CSVRecordTest_testGetInt {

    /** The three column values of the single parsed record. */
    private String[] values;

    /** The record parsed from the {@link #values}, accessed by column index. */
    private CSVRecord record;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
        final String csvLine = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(csvLine))) {
            record = parser.iterator().next();
        }
    }

    @Test
    void testGetInt() {
        // Each column value must be returned at its corresponding 0-based index.
        assertEquals(values[0], record.get(0));
        assertEquals(values[1], record.get(1));
        assertEquals(values[2], record.get(2));
    }
}
