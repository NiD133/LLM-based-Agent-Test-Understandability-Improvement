package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testIterator {

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

    @Test
    void testIterator() {
        int valueIndex = 0;

        for (final String actualValue : record) {
            assertEquals(values[valueIndex], actualValue);
            valueIndex++;
        }
    }
}
