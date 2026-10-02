package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetUnmappedNegativeInt {

    private static final String[] ROW_VALUES = { "A", "B", "C" };

    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = StringUtils.join(ROW_VALUES, ',');

        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            parser.iterator().next();
        }

        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            parser.getHeaderMap();
        }
    }

    @Test
    void testGetUnmappedNegativeInt() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> recordWithHeader.get(Integer.MIN_VALUE));
    }
}
