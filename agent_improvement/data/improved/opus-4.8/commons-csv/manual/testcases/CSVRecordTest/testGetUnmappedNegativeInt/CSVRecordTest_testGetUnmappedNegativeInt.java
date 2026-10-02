package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that requesting a column by a negative index throws an
 * {@link ArrayIndexOutOfBoundsException}, since {@link CSVRecord#get(int)}
 * indexes directly into the backing values array.
 */
public class CSVRecordTest_testGetUnmappedNegativeInt {

    /** Column headers supplied to the parser as an enum. */
    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** A single CSV record "A,B,C" parsed with an enum-based header. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = "A,B,C";
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class).get()
                .parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetUnmappedNegativeInt() {
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> recordWithHeader.get(Integer.MIN_VALUE));
    }
}
