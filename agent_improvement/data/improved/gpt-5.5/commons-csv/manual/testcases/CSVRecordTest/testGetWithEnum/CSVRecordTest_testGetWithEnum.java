package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetWithEnum {

    private enum EnumHeader {
        FIRST,
        SECOND,
        THIRD
    }

    private enum EnumFixture {
        UNKNOWN_COLUMN
    }

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String[] values = { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            parser.iterator().next();
        }

        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            parser.getHeaderMap();
        }
    }

    @Test
    void testGetWithEnum() {
        assertEquals(recordWithHeader.get("FIRST"), recordWithHeader.get(EnumHeader.FIRST));
        assertEquals(recordWithHeader.get("SECOND"), recordWithHeader.get(EnumHeader.SECOND));
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get(EnumFixture.UNKNOWN_COLUMN));
    }
}
