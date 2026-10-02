package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testRemoveAndAddColumns {

    /** The three column headers used when parsing the record by name. */
    public enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** The raw cell values shared by both records: header columns FIRST, SECOND, THIRD map to A, B, C. */
    private static final String[] VALUES = { "A", "B", "C" };

    /** A record parsed without a header; values are accessible only by index. */
    private CSVRecord record;

    /** A record parsed with an {@link EnumHeader}, so its values can be turned into a name-to-value map. */
    private CSVRecord recordWithHeader;

    /** The header-name-to-column-index mapping captured from the header-aware parser. */
    private Map<String, Integer> headerMap;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = StringUtils.join(VALUES, ',');

        // Parse the same row twice: once without a header, once with the EnumHeader columns.
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            headerMap = parser.getHeaderMap();
        }
    }

    /**
     * Verifies that the {@link Map} returned by {@link CSVRecord#toMap()} is a detached copy that can be freely
     * mutated. Removing an absent column is a no-op, adding a new column inserts a value, and the resulting set of
     * values still round-trips correctly through a {@link CSVPrinter}.
     */
    @Test
    void testRemoveAndAddColumns() throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new StringBuilder(), CSVFormat.DEFAULT)) {
            final Map<String, String> map = recordWithHeader.toMap();

            // "OldColumn" is not a header, so removing it leaves the original A, B, C values untouched.
            map.remove("OldColumn");
            // Add a brand-new column that was never part of the parsed header.
            map.put("ZColumn", "NewValue");

            // Print the values in sorted order so the output is deterministic: A, B, C, NewValue.
            final List<String> sortedValues = new ArrayList<>(map.values());
            sortedValues.sort(null);
            printer.printRecord(sortedValues);

            final String expected = "A,B,C,NewValue" + CSVFormat.DEFAULT.getRecordSeparator();
            assertEquals(expected, printer.getOut().toString());
        }
    }
}
