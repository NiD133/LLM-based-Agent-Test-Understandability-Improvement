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

public class CSVRecordTest_testToMapWithShortRecord {

    private enum EnumHeader {
        FIRST,
        SECOND,
        THIRD
    }

    private static final String FIRST_HEADER = "A";
    private static final String SECOND_HEADER = "B";
    private static final String THIRD_HEADER = "C";
    private static final String SHORT_RECORD_DATA = "a,b";

    private Map<String, Integer> headerMap;

    private CSVRecord record;

    private CSVRecord recordWithHeader;

    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { FIRST_HEADER, SECOND_HEADER, THIRD_HEADER };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            headerMap = parser.getHeaderMap();
        }
    }

    private void validateMap(final Map<String, String> map, final boolean allowsNulls) {
        assertTrue(map.containsKey(EnumHeader.FIRST.name()));
        assertTrue(map.containsKey(EnumHeader.SECOND.name()));
        assertTrue(map.containsKey(EnumHeader.THIRD.name()));
        assertFalse(map.containsKey("fourth"));
        if (allowsNulls) {
            assertFalse(map.containsKey(null));
        }
        assertEquals(FIRST_HEADER, map.get(EnumHeader.FIRST.name()));
        assertEquals(SECOND_HEADER, map.get(EnumHeader.SECOND.name()));
        assertEquals(THIRD_HEADER, map.get(EnumHeader.THIRD.name()));
        assertNull(map.get("fourth"));
    }

    @Test
    void testToMapWithShortRecord() throws Exception {
        try (CSVParser parser = CSVParser.parse(SHORT_RECORD_DATA,
                CSVFormat.DEFAULT.withHeader(FIRST_HEADER, SECOND_HEADER, THIRD_HEADER))) {
            final CSVRecord recordWithFewerValuesThanHeaders = parser.iterator().next();

            recordWithFewerValuesThanHeaders.toMap();
        }
    }
}
