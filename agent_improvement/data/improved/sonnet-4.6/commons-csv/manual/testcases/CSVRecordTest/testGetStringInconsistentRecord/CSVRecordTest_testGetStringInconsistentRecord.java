package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetStringInconsistentRecord {

    /**
     * Enum whose three names (FIRST, SECOND, THIRD) become the CSV column headers.
     * toString() is intentionally different from name() to verify that the header
     * mapping uses name() rather than toString().
     */
    public enum EnumHeader {
        FIRST("first"), SECOND("second"), THIRD("third");

        private final String label;

        EnumHeader(final String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private Map<String, Integer> headerMap;
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = StringUtils.join(new String[]{"A", "B", "C"}, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class)
                .get()
                .parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            headerMap = parser.getHeaderMap();
        }
    }

    /**
     * When the header map contains a column name whose index falls outside the
     * record's value array (i.e. the record is inconsistent), get(String) must
     * throw IllegalArgumentException instead of silently returning garbage.
     */
    @Test
    void testGetStringInconsistentRecord() {
        // "fourth" maps to index 4, but the record only has values at indices 0–2
        headerMap.put("fourth", Integer.valueOf(4));
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get("fourth"));
    }
}
