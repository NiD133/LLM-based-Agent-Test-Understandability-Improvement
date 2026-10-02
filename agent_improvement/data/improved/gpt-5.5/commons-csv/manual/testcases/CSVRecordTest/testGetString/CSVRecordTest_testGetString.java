package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetString {

    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    private String[] rowValues;

    private CSVRecord record;

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        rowValues = new String[] { "A", "B", "C" };
        final String rowData = StringUtils.join(rowValues, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetString() {
        assertEquals(rowValues[0], recordWithHeader.get(EnumHeader.FIRST.name()));
        assertEquals(rowValues[1], recordWithHeader.get(EnumHeader.SECOND.name()));
        assertEquals(rowValues[2], recordWithHeader.get(EnumHeader.THIRD.name()));
    }
}
