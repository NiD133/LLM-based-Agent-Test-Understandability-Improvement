package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link CSVRecord#get(String)}: when a record is parsed with a header,
 * each column value can be looked up by its header name.
 */
public class CSVRecordTest_testGetString {

    /** Column header names used to parse the record. */
    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** The three column values, in header order: FIRST="A", SECOND="B", THIRD="C". */
    private static final String[] VALUES = { "A", "B", "C" };

    /** A single record parsed with the {@link EnumHeader} column names. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = StringUtils.join(VALUES, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(EnumHeader.class)
                .get()
                .parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetString() {
        assertEquals(VALUES[0], recordWithHeader.get(EnumHeader.FIRST.name()));
        assertEquals(VALUES[1], recordWithHeader.get(EnumHeader.SECOND.name()));
        assertEquals(VALUES[2], recordWithHeader.get(EnumHeader.THIRD.name()));
    }
}
