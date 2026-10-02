package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetUnmappedName {

    /** Headers used when parsing the CSV record under test. Names are FIRST, SECOND, THIRD. */
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

    /**
     * Accessing a column name that is not present in the header mapping must throw
     * {@link IllegalArgumentException}.
     */
    @Test
    void testGetUnmappedName() {
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get("fourth"));
    }
}
