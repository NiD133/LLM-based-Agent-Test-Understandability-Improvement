package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#toString()}.
 */
public class CSVRecordTest_testToString {

    /** Column headers used to give the parsed record a header mapping. */
    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** The record under test, parsed from a single CSV row that has a header. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        // Build a single CSV row: "A,B,C".
        final String[] values = { "A", "B", "C" };
        final String csvRow = StringUtils.join(values, ',');

        // Parse the row using the EnumHeader columns so the record carries a header mapping.
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(csvRow))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testToString() {
        final String text = recordWithHeader.toString();

        // The string representation must exist and expose the record's key fields.
        assertNotNull(text);
        assertTrue(text.contains("comment="), "toString should include the comment field");
        assertTrue(text.contains("recordNumber="), "toString should include the recordNumber field");
        assertTrue(text.contains("values="), "toString should include the values field");
    }
}
