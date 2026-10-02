package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testIsSetString {

    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    private static final String[] RECORD_VALUES = { "A", "B", "C" };

    private CSVRecord recordWithoutHeaders;

    private CSVRecord recordWithHeaders;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = StringUtils.join(RECORD_VALUES, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            recordWithoutHeaders = parser.iterator().next();
        }

        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeaders = parser.iterator().next();
        }
    }

    @Test
    void testIsSetString() {
        assertFalse(recordWithoutHeaders.isSet("first"));
        assertTrue(recordWithHeaders.isSet(EnumHeader.FIRST.name()));
        assertFalse(recordWithHeaders.isSet("DOES NOT EXIST"));
    }
}
