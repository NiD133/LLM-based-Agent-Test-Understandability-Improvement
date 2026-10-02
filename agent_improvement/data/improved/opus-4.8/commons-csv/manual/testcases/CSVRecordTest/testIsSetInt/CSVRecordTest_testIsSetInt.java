package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#isSet(int)}, which reports whether a 0-based column
 * index points at an existing value in the record.
 */
public class CSVRecordTest_testIsSetInt {

    /** A row of three values: index 0 = "A", 1 = "B", 2 = "C". */
    private static final String THREE_COLUMN_ROW = "A,B,C";

    /** Record parsed without any header mapping. */
    private CSVRecord record;

    /** Record parsed with a three-column header (EnumHeader: FIRST, SECOND, THIRD). */
    private CSVRecord recordWithHeader;

    /** Header names used to build {@link #recordWithHeader}. */
    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    @BeforeEach
    public void setUp() throws Exception {
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(THREE_COLUMN_ROW))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get()
                .parse(new StringReader(THREE_COLUMN_ROW))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testIsSetInt() {
        // Valid indices are 0..2 for the three-value record; anything outside is unset.
        assertFalse(record.isSet(-1), "negative index is never set");
        assertTrue(record.isSet(0), "first column is set");
        assertTrue(record.isSet(2), "last column is set");
        assertFalse(record.isSet(3), "index past the last column is not set");

        // A header mapping does not change index-based bounds checking.
        assertTrue(recordWithHeader.isSet(1), "in-range index is set");
        assertFalse(recordWithHeader.isSet(1000), "far out-of-range index is not set");
    }
}
