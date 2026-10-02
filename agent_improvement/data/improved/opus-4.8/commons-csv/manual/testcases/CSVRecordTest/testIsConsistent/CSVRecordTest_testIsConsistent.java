package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.Map;

import org.apache.commons.csv.CSVRecordTest.EnumHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#isConsistent()}, which reports whether the number of
 * values in a record matches the number of columns declared by its header.
 */
public class CSVRecordTest_testIsConsistent {

    /** A single CSV row holding three values: "A", "B", "C". */
    private static final String THREE_VALUE_ROW = "A,B,C";

    /** Record parsed without any header mapping. */
    private CSVRecord recordWithoutHeader;

    /** Record parsed with a three-column header (FIRST, SECOND, THIRD). */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        // Parse the row with no header: the record simply carries its three values.
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(THREE_VALUE_ROW))) {
            recordWithoutHeader = parser.iterator().next();
        }

        // Parse the same row with a three-entry header so the sizes line up.
        final CSVFormat formatWithHeader =
                CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get();
        try (CSVParser parser = formatWithHeader.parse(new StringReader(THREE_VALUE_ROW))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testIsConsistent() {
        // No header mapping means there is nothing to be inconsistent with.
        assertTrue(recordWithoutHeader.isConsistent());

        // Header size (3) equals value count (3), so the record is consistent.
        assertTrue(recordWithHeader.isConsistent());

        // getHeaderMap() hands back a copy, so mutating it must not affect the
        // record's own header mapping; the record therefore stays consistent.
        final Map<String, Integer> headerMapCopy = recordWithHeader.getParser().getHeaderMap();
        headerMapCopy.put("fourth", Integer.valueOf(4));
        assertTrue(recordWithHeader.isConsistent());
    }
}
