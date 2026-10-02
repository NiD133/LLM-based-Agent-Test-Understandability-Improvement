package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testToMap {

    /** This enum overrides toString() but the header map keys use .name(), not toString(). */
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

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws IOException {
        final String[] values = {"A", "B", "C"};
        final String rowData = String.join(",", values);
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class)
                .get()
                .parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testToMap() {
        final Map<String, String> map = recordWithHeader.toMap();

        // All three enum-named headers should be present with their CSV values
        assertTrue(map.containsKey(EnumHeader.FIRST.name()));
        assertTrue(map.containsKey(EnumHeader.SECOND.name()));
        assertTrue(map.containsKey(EnumHeader.THIRD.name()));
        assertEquals("A", map.get(EnumHeader.FIRST.name()));
        assertEquals("B", map.get(EnumHeader.SECOND.name()));
        assertEquals("C", map.get(EnumHeader.THIRD.name()));

        // Keys not in the header should not be present
        assertFalse(map.containsKey("fourth"));
        assertNull(map.get("fourth"));

        // The map should not contain a null key
        assertFalse(map.containsKey(null));
    }
}
