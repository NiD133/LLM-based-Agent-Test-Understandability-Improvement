package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetNullEnum {

    private static final String[] RECORD_VALUES = { "A", "B", "C" };

    private enum EnumHeader {
        FIRST,
        SECOND,
        THIRD
    }

    private Map<String, Integer> headerMap;

    private CSVRecord record;

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = StringUtils.join(RECORD_VALUES, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }

        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            headerMap = parser.getHeaderMap();
        }
    }

    @Test
    void testGetNullEnum() {
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get((Enum<?>) null));
    }
}
