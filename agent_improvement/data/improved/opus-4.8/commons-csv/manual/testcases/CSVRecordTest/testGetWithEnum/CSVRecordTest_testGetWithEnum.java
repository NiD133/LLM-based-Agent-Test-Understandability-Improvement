package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CSVRecord#get(Enum)} looks up a column by the enum
 * constant's {@link Enum#name()} and behaves identically to looking it up by
 * the equivalent String.
 */
public class CSVRecordTest_testGetWithEnum {

    /** A column name that is intentionally absent from the header. */
    private enum MissingColumn {
        UNKNOWN_COLUMN
    }

    /**
     * Header columns FIRST, SECOND, THIRD. {@code toString()} is overridden on
     * purpose to prove that {@code get(Enum)} resolves by {@code name()} and not
     * by {@code toString()}.
     */
    public enum Header {
        FIRST("first"), SECOND("second"), THIRD("third");

        private final String label;

        Header(final String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    /** Record built from the row "A,B,C" with FIRST,SECOND,THIRD as headers. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String row = "A,B,C";
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(Header.class)
                .get()
                .parse(new StringReader(row))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetWithEnum() {
        // Looking up by enum constant matches looking up by its name().
        assertEquals(recordWithHeader.get("FIRST"), recordWithHeader.get(Header.FIRST));
        assertEquals(recordWithHeader.get("SECOND"), recordWithHeader.get(Header.SECOND));

        // An enum whose name() is not a mapped header is rejected.
        assertThrows(IllegalArgumentException.class,
                () -> recordWithHeader.get(MissingColumn.UNKNOWN_COLUMN));
    }
}
