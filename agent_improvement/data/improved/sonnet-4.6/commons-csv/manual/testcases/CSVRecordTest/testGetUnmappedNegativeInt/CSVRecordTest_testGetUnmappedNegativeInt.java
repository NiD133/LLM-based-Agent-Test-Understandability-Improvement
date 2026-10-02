package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that CSVRecord.get(int) throws ArrayIndexOutOfBoundsException when
 * called with a negative index that has no header mapping.
 */
public class CSVRecordTest_testGetUnmappedNegativeInt {

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String[] values = {"A", "B", "C"};
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class)
                .get()
                .parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetUnmappedNegativeInt() {
        // Integer.MIN_VALUE is a negative index with no valid array position,
        // so CSVRecord.get(int) must throw ArrayIndexOutOfBoundsException.
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> recordWithHeader.get(Integer.MIN_VALUE));
    }
}
