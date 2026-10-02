package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CSVRecord#get(Enum)} rejects a {@code null} enum key.
 */
public class CSVRecordTest_testGetNullEnum {

    /** Column names supplied to the parser as an enum-based header. */
    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** A record parsed with an enum-based header, so values can be looked up by name. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = "A,B,C";
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class)
                .get()
                .parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetNullEnum() {
        // Looking up a value with a null enum key is not allowed.
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get((Enum<?>) null));
    }
}
