package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CSVRecord#get(String)} fails when the record was parsed
 * without a header mapping.
 */
public class CSVRecordTest_testGetStringNoHeader {

    /** A record parsed from "A,B,C" using the default format, i.e. without any header. */
    private CSVRecord recordWithoutHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = "A,B,C";
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            recordWithoutHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetStringNoHeader() {
        // Looking up a value by column name requires a header mapping; since none
        // was provided, get(String) must reject the call with IllegalStateException.
        assertThrows(IllegalStateException.class, () -> recordWithoutHeader.get("first"));
    }
}
