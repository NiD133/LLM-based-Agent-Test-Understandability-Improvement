package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetString {

    /** This enum overrides toString() but it's the names that matter. */
    public enum EnumHeader {
        FIRST("first"), SECOND("second"), THIRD("third");

        private final String number;

        EnumHeader(final String number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return number;
        }
    }

    private CSVRecord recordWithHeader;

    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    /**
     * Verifies that CSVRecord.get(String) returns the correct field value
     * when column names come from an enum-based header mapping.
     */
    @Test
    void testGetString() {
        assertEquals(values[0], recordWithHeader.get(EnumHeader.FIRST.name()));
        assertEquals(values[1], recordWithHeader.get(EnumHeader.SECOND.name()));
        assertEquals(values[2], recordWithHeader.get(EnumHeader.THIRD.name()));
    }
}
