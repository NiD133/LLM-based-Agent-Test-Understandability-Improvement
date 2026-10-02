package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that requesting a column by an out-of-range positive index throws,
 * even when the record was parsed with a header mapping.
 */
public class CSVRecordTest_testGetUnmappedPositiveInt {

    /** A record holding three values ("A", "B", "C") parsed with a header mapping. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = "A,B,C";
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader("FIRST", "SECOND", "THIRD").get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetUnmappedPositiveInt() {
        // The record has only 3 values, so an index of Integer.MAX_VALUE is out of bounds.
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> recordWithHeader.get(Integer.MAX_VALUE));
    }
}
