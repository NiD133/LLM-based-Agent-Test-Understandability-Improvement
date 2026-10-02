package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testIsSetInt {

    private enum EnumHeader {
        FIRST, SECOND, THIRD
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
    void testIsSetInt() {
        assertFalse(record.isSet(-1));
        assertTrue(record.isSet(0));
        assertTrue(record.isSet(2));
        assertFalse(record.isSet(3));

        assertTrue(recordWithHeader.isSet(1));
        assertFalse(recordWithHeader.isSet(1000));
    }
}
