package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CSVRecord#get(Enum)} throws {@link IllegalArgumentException}
 * when the supplied enum constant is not present in the record's header mapping.
 */
public class CSVRecordTest_testGetUnmappedEnum {

    /** Column headers used to give the parsed record a header mapping. */
    private enum MappedColumn {
        FIRST, SECOND, THIRD
    }

    /** An enum whose constants are intentionally absent from the header mapping. */
    private enum UnmappedColumn {
        UNKNOWN_COLUMN
    }

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String csvRow = StringUtils.join(new String[] { "A", "B", "C" }, ',');
        // Parse the row with MappedColumn as the header enum so the record
        // carries a mapping for FIRST, SECOND, and THIRD only.
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(MappedColumn.class).get()
                .parse(new StringReader(csvRow))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetUnmappedEnum() {
        // Accessing a column whose enum name does not exist in the header mapping
        // must throw IllegalArgumentException.
        assertThrows(IllegalArgumentException.class,
                () -> recordWithHeader.get(UnmappedColumn.UNKNOWN_COLUMN));
    }
}
