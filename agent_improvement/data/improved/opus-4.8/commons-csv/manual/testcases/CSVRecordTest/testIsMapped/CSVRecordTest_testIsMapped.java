package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#isMapped(String)}.
 *
 * <p>
 * {@code isMapped(name)} reports whether a column name is known to the record's header
 * mapping. A record parsed without a header therefore maps nothing, while a record parsed
 * with a header maps exactly the declared header names.
 * </p>
 */
public class CSVRecordTest_testIsMapped {

    /** Column headers whose names (FIRST, SECOND, THIRD) define the header mapping. */
    public enum EnumHeader {
        FIRST, SECOND, THIRD;
    }

    /** The CSV row shared by both records under test, e.g. "A,B,C". */
    private static final String CSV_ROW = StringUtils.join(new String[] { "A", "B", "C" }, ',');

    /** A record parsed without any header mapping. */
    private CSVRecord recordWithoutHeader;

    /** A record parsed with the {@link EnumHeader} columns (FIRST, SECOND, THIRD). */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(CSV_ROW))) {
            recordWithoutHeader = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(CSV_ROW))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testIsMapped() {
        // A record parsed without a header has no mapping, so no name is mapped.
        assertFalse(recordWithoutHeader.isMapped("first"));

        // A record parsed with a header maps the declared column names...
        assertTrue(recordWithHeader.isMapped(EnumHeader.FIRST.name()));

        // ...but not names that are absent from the header.
        assertFalse(recordWithHeader.isMapped("fourth"));
    }
}
