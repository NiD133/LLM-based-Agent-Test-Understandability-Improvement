package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#putIn(Map)}, which copies a record's header-to-value
 * mappings into a caller-supplied Map and returns that same Map.
 */
public class CSVRecordTest_testPutInMap {

    /**
     * Used as the record header; the column header names are the enum constant
     * names FIRST, SECOND and THIRD.
     */
    public enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** A single CSV row whose three columns hold these values. */
    private static final String[] COLUMN_VALUES = { "A", "B", "C" };

    /** Header name that is intentionally absent from the record. */
    private static final String UNMAPPED_HEADER = "fourth";

    /**
     * A record parsed with {@link EnumHeader} as its header, so its columns are
     * keyed by the enum constant names FIRST, SECOND and THIRD.
     */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String csvRow = StringUtils.join(COLUMN_VALUES, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class)
                .get()
                .parse(new StringReader(csvRow))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    /**
     * Asserts that {@code map} contains exactly the three header-to-value
     * mappings produced from {@link #recordWithHeader}, and nothing for the
     * unmapped header.
     */
    private void assertContainsRecordMappings(final Map<String, String> map) {
        assertTrue(map.containsKey(EnumHeader.FIRST.name()));
        assertTrue(map.containsKey(EnumHeader.SECOND.name()));
        assertTrue(map.containsKey(EnumHeader.THIRD.name()));
        assertFalse(map.containsKey(UNMAPPED_HEADER));

        assertEquals("A", map.get(EnumHeader.FIRST.name()));
        assertEquals("B", map.get(EnumHeader.SECOND.name()));
        assertEquals("C", map.get(EnumHeader.THIRD.name()));
        assertNull(map.get(UNMAPPED_HEADER));
    }

    @Test
    void testPutInMap() {
        // putIn populates the map passed in.
        final Map<String, String> concurrentMap = new ConcurrentHashMap<>();
        recordWithHeader.putIn(concurrentMap);
        assertContainsRecordMappings(concurrentMap);

        // putIn also returns the same map instance, preserving its concrete type
        // (here a TreeMap) so the result can be assigned without casting.
        final TreeMap<String, String> treeMap = recordWithHeader.putIn(new TreeMap<>());
        assertContainsRecordMappings(treeMap);
    }
}
