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

public class CSVRecordTest_testPutInMap {

    /**
     * A CSVRecord parsed from "A,B,C" with headers FIRST, SECOND, THIRD
     * provided by {@link EnumHeader}.
     */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String[] values = {"A", "B", "C"};
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(CSVRecordTest.EnumHeader.class)
                .get()
                .parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    /**
     * Verifies that {@code map} contains exactly the three header-to-value
     * mappings produced by the record (FIRST→A, SECOND→B, THIRD→C) and that
     * an unknown key returns null.
     *
     * @param map         the map returned (or populated) by {@code putIn}
     * @param allowsNulls whether the map allows null keys (skip null-key check when false)
     */
    private void assertMapContainsRecordValues(final Map<String, String> map,
                                               final boolean allowsNulls) {
        assertTrue(map.containsKey(CSVRecordTest.EnumHeader.FIRST.name()),  "map should contain key FIRST");
        assertTrue(map.containsKey(CSVRecordTest.EnumHeader.SECOND.name()), "map should contain key SECOND");
        assertTrue(map.containsKey(CSVRecordTest.EnumHeader.THIRD.name()),  "map should contain key THIRD");
        assertFalse(map.containsKey("fourth"), "map should not contain unmapped key 'fourth'");
        if (allowsNulls) {
            assertFalse(map.containsKey(null), "map should not contain null key");
        }
        assertEquals("A", map.get(CSVRecordTest.EnumHeader.FIRST.name()),  "FIRST should map to 'A'");
        assertEquals("B", map.get(CSVRecordTest.EnumHeader.SECOND.name()), "SECOND should map to 'B'");
        assertEquals("C", map.get(CSVRecordTest.EnumHeader.THIRD.name()),  "THIRD should map to 'C'");
        assertNull(map.get("fourth"), "unknown key 'fourth' should return null");
    }

    /**
     * Tests that {@link CSVRecord#putIn(Map)} populates a caller-supplied map
     * with the record's header-to-value pairs and returns that same map typed
     * as the concrete subtype (enabling fluent assignment without an unchecked cast).
     */
    @Test
    void testPutInMap() {
        // Populate a pre-existing ConcurrentHashMap via the void-return overload.
        final Map<String, String> concurrentMap = new ConcurrentHashMap<>();
        recordWithHeader.putIn(concurrentMap);
        assertMapContainsRecordValues(concurrentMap, false);

        // Verify that the return value is typed as the concrete map subtype,
        // allowing direct assignment to TreeMap without an explicit cast.
        final TreeMap<String, String> treeMap = recordWithHeader.putIn(new TreeMap<>());
        assertMapContainsRecordValues(treeMap, false);
    }
}
