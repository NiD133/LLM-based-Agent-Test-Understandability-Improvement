package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CSVRecord#get(String)} fails fast when the header mapping
 * has been made inconsistent with the record's actual values.
 */
public class CSVRecordTest_testGetStringInconsistentRecord {

    /** The three column headers used to parse the record. */
    public enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** A single CSV row holding three values: A, B and C. */
    private static final String THREE_VALUE_ROW = "A,B,C";

    /** A record parsed with a three-column {@link EnumHeader}: FIRST, SECOND, THIRD. */
    private CSVRecord recordWithHeader;

    /** The live header-name to column-index mapping shared by {@link #recordWithHeader}. */
    private Map<String, Integer> headerMap;

    @BeforeEach
    public void setUp() throws Exception {
        final CSVFormat formatWithHeader = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get();
        try (CSVParser parser = formatWithHeader.parse(new StringReader(THREE_VALUE_ROW))) {
            recordWithHeader = parser.iterator().next();
            headerMap = parser.getHeaderMap();
        }
    }

    @Test
    void testGetStringInconsistentRecord() {
        // Add a header "fourth" that points at column index 4, even though the
        // record only has three values. This makes the mapping inconsistent
        // with the record.
        headerMap.put("fourth", Integer.valueOf(4));

        // Resolving "fourth" maps to an out-of-range index, so get(String)
        // must reject it with an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get("fourth"));
    }
}
