package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testIsConsistent {

    private enum EnumHeader {
        FIRST,
        SECOND,
        THIRD
    }

    private CSVRecord record;
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws IOException {
        final String[] values = { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }

        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class)
                .get()
                .parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testIsConsistent() {
        assertTrue(record.isConsistent());
        assertTrue(recordWithHeader.isConsistent());

        final Map<String, Integer> headerMapCopy = recordWithHeader.getParser().getHeaderMap();
        headerMapCopy.put("fourth", Integer.valueOf(4));

        // Mutating the returned copy must not change the record's consistency.
        assertTrue(recordWithHeader.isConsistent());
    }
}
