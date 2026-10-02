package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.StringReader;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testIsMapped {

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


    private CSVRecord record;

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String[] values = { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testIsMapped() {
        assertFalse(record.isMapped("first"));
        assertTrue(recordWithHeader.isMapped(EnumHeader.FIRST.name()));
        assertFalse(recordWithHeader.isMapped("fourth"));
    }
}
