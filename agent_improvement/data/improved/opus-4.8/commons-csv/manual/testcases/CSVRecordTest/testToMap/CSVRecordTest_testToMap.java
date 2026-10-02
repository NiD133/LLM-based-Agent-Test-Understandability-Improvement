package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#toMap()}.
 */
public class CSVRecordTest_testToMap {

    /** Header names used to map the record values by column. */
    public enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** The CSV values parsed for the single record under test. */
    private static final String[] VALUES = { "A", "B", "C" };

    /** A record parsed with an enum-based header so its values can be mapped by name. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String csvLine = StringUtils.join(VALUES, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class)
                .get()
                .parse(new StringReader(csvLine))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testToMap() {
        final Map<String, String> map = recordWithHeader.toMap();

        // Every header name maps to its corresponding value.
        assertTrue(map.containsKey(EnumHeader.FIRST.name()));
        assertTrue(map.containsKey(EnumHeader.SECOND.name()));
        assertTrue(map.containsKey(EnumHeader.THIRD.name()));
        assertEquals("A", map.get(EnumHeader.FIRST.name()));
        assertEquals("B", map.get(EnumHeader.SECOND.name()));
        assertEquals("C", map.get(EnumHeader.THIRD.name()));

        // Unknown keys (including null) are absent and resolve to null.
        assertFalse(map.containsKey("fourth"));
        assertFalse(map.containsKey(null));
        assertNull(map.get("fourth"));
    }
}
