package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#get(String)}, retrieving record values by their header name.
 */
public class CSVRecordTest_testGetString {

    /** Header names used as the CSV columns; one constant per column. */
    public enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** Column values of the single CSV row under test, in column order. */
    private static final String[] VALUES = { "A", "B", "C" };

    /** A record parsed with the {@link EnumHeader} header, so values can be looked up by name. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String singleRow = StringUtils.join(VALUES, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class)
                .get()
                .parse(new StringReader(singleRow))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetString() {
        // Each header name maps to the value in its corresponding column.
        assertEquals(VALUES[0], recordWithHeader.get(EnumHeader.FIRST.name()));
        assertEquals(VALUES[1], recordWithHeader.get(EnumHeader.SECOND.name()));
        assertEquals(VALUES[2], recordWithHeader.get(EnumHeader.THIRD.name()));
    }
}
