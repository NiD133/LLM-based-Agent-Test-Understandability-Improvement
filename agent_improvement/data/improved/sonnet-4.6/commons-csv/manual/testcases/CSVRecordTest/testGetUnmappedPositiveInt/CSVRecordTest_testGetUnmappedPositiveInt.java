package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetUnmappedPositiveInt {

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        String[] values = new String[] { "A", "B", "C" };
        String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(CSVRecordTest.EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetUnmappedPositiveInt() {
        // Accessing an index far beyond the record's bounds (3 values) must throw
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> recordWithHeader.get(Integer.MAX_VALUE));
    }
}
