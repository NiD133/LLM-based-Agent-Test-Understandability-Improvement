package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testIsSetString {

    /** Mirrors the enum used in CSVRecordTest; names become the CSV header keys. */
    public enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** A record parsed without any header mapping. */
    private CSVRecord record;

    /** A record parsed with an enum-based header mapping (FIRST, SECOND, THIRD). */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String[] values = { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class).get()
                .parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testIsSetString() {
        // A record without a header mapping cannot resolve any column by name.
        assertFalse(record.isSet("first"),
                "record without header: 'first' should not be set");

        // A record with an enum header has a mapping for every declared enum constant.
        assertTrue(recordWithHeader.isSet(EnumHeader.FIRST.name()),
                "recordWithHeader: mapped column 'FIRST' should be set");

        // A name that was never declared as a header should not be set.
        assertFalse(recordWithHeader.isSet("DOES NOT EXIST"),
                "recordWithHeader: unknown column should not be set");
    }
}
