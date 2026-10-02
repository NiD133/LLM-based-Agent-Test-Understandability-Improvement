package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import org.apache.commons.csv.CSVRecordTest.EnumHeader;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testIsSetInt {

    private CSVRecord record;

    private CSVRecord recordWithHeader;

    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(CSVRecordTest.EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testIsSetInt() {
        // Negative index is always out of bounds
        assertFalse(record.isSet(-1));

        // First and last valid indices (0-based, record has 3 values)
        assertTrue(record.isSet(0));
        assertTrue(record.isSet(2));

        // Index equal to the size is out of bounds
        assertFalse(record.isSet(3));

        // A valid index works the same on a record that has a header mapping
        assertTrue(recordWithHeader.isSet(1));

        // Far-out-of-range index is also out of bounds
        assertFalse(recordWithHeader.isSet(1000));
    }
}
