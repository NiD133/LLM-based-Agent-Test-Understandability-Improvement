package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#isSet(String)}, which reports whether a column name is
 * mapped to a header <em>and</em> has a value in the record.
 */
public class CSVRecordTest_testIsSetString {

    /** Header columns used when parsing the record with a header mapping. */
    public enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    /** Single CSV row "A,B,C" shared by both records under test. */
    private static final String ROW = StringUtils.join(new String[] { "A", "B", "C" }, ',');

    /** Record parsed without any header mapping. */
    private CSVRecord recordWithoutHeader;

    /** Record parsed with the {@link EnumHeader} columns FIRST, SECOND, THIRD. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(ROW))) {
            recordWithoutHeader = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(ROW))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testIsSetString() {
        // Without a header mapping, no column name is considered set.
        assertFalse(recordWithoutHeader.isSet("first"));

        // With a header mapping, a known column name is set...
        assertTrue(recordWithHeader.isSet(EnumHeader.FIRST.name()));

        // ...but an unknown column name is not.
        assertFalse(recordWithHeader.isSet("DOES NOT EXIST"));
    }
}
