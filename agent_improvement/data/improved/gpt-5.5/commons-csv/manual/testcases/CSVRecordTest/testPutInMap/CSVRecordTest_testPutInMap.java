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

    private enum EnumHeader {
        FIRST,
        SECOND,
        THIRD
    }

    private Map<String, Integer> headerMap;

    private CSVRecord record;

    private CSVRecord recordWithHeader;

    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            headerMap = parser.getHeaderMap();
        }
    }

    private void assertHeaderValues(final Map<String, String> actualValuesByHeader, final boolean allowsNulls) {
        assertTrue(actualValuesByHeader.containsKey(EnumHeader.FIRST.name()));
        assertTrue(actualValuesByHeader.containsKey(EnumHeader.SECOND.name()));
        assertTrue(actualValuesByHeader.containsKey(EnumHeader.THIRD.name()));
        assertFalse(actualValuesByHeader.containsKey("fourth"));
        if (allowsNulls) {
            assertFalse(actualValuesByHeader.containsKey(null));
        }
        assertEquals("A", actualValuesByHeader.get(EnumHeader.FIRST.name()));
        assertEquals("B", actualValuesByHeader.get(EnumHeader.SECOND.name()));
        assertEquals("C", actualValuesByHeader.get(EnumHeader.THIRD.name()));
        assertNull(actualValuesByHeader.get("fourth"));
    }

    @Test
    void testPutInMap() {
        final Map<String, String> concurrentValuesByHeader = new ConcurrentHashMap<>();
        this.recordWithHeader.putIn(concurrentValuesByHeader);
        assertHeaderValues(concurrentValuesByHeader, false);

        final TreeMap<String, String> sortedValuesByHeader = recordWithHeader.putIn(new TreeMap<>());
        assertHeaderValues(sortedValuesByHeader, false);
    }
}
