package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#get(String)}, which looks up a value by its header name.
 */
public class CSVRecordTest_testGetString {

    /** Column headers used to build a record whose values can be looked up by name. */
    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** The values that make up the single parsed CSV row. */
    private static final String[] VALUES = { "A", "B", "C" };

    /** A record parsed from {@link #VALUES} with column headers taken from {@link EnumHeader}. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String rowData = StringUtils.join(VALUES, ',');
        final CSVFormat formatWithHeader = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get();
        try (CSVParser parser = formatWithHeader.parse(new StringReader(rowData))) {
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
