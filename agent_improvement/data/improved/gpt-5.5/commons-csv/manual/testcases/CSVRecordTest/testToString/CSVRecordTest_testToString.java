package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testToString {

    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String[] values = { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testToString() {
        assertNotNull(recordWithHeader.toString());
        assertTrue(recordWithHeader.toString().contains("comment="));
        assertTrue(recordWithHeader.toString().contains("recordNumber="));
        assertTrue(recordWithHeader.toString().contains("values="));
    }
}
