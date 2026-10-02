package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testToString {

    /** Header columns for a three-field CSV record used in toString verification. */
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

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = "A,B,C";
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testToString() {
        final String result = recordWithHeader.toString();
        assertNotNull(result);
        assertTrue(result.contains("comment="));
        assertTrue(result.contains("recordNumber="));
        assertTrue(result.contains("values="));
    }
}
