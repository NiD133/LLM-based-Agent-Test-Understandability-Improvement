package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetInt {

    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    private static final String[] EXPECTED_VALUES = { "A", "B", "C" };

    private Map<String, Integer> headerMap;

    private CSVRecord record;

    private CSVRecord recordWithHeader;

    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = EXPECTED_VALUES;
        final String rowData = StringUtils.join(values, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }

        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            headerMap = parser.getHeaderMap();
        }
    }

    @Test
    void testGetInt() {
        assertAll(
                () -> assertEquals(values[0], record.get(0)),
                () -> assertEquals(values[1], record.get(1)),
                () -> assertEquals(values[2], record.get(2)));
    }
}
