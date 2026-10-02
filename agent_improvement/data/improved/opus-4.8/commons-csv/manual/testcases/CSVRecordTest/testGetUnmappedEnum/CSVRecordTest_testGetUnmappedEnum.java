package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CSVRecord#get(Enum)} rejects an enum constant whose name
 * is not present in the record's header mapping.
 */
public class CSVRecordTest_testGetUnmappedEnum {

    /** Enum whose constant names ("FIRST", "SECOND", "THIRD") define the record's headers. */
    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** Enum providing a constant ("UNKNOWN_COLUMN") that is absent from the header mapping. */
    private enum EnumFixture {
        UNKNOWN_COLUMN
    }

    /** A record parsed with a header derived from {@link EnumHeader}. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        // Single CSV row "A,B,C" whose columns are mapped to EnumHeader's constants.
        final String csvRow = "A,B,C";
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class)
                .get()
                .parse(new StringReader(csvRow))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetUnmappedEnum() {
        // UNKNOWN_COLUMN has no matching header, so lookup by enum must fail.
        assertThrows(IllegalArgumentException.class,
                () -> recordWithHeader.get(EnumFixture.UNKNOWN_COLUMN));
    }
}
