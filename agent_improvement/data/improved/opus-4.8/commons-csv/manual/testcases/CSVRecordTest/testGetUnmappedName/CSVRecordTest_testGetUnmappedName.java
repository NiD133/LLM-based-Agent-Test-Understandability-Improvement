package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CSVRecord#get(String)} rejects a column name that is not
 * part of the record's header mapping.
 */
public class CSVRecordTest_testGetUnmappedName {

    /** A single-row record whose columns are mapped to the header names FIRST, SECOND, THIRD. */
    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String singleRow = "A,B,C";
        // setHeader(...class) maps the three columns to the enum constant
        // names: FIRST -> "A", SECOND -> "B", THIRD -> "C".
        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader(CSVRecordTest.EnumHeader.class)
                .get()
                .parse(new StringReader(singleRow))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testGetUnmappedName() {
        // "fourth" is not one of the mapped header names, so looking it up by name
        // must fail with IllegalArgumentException rather than return a value.
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get("fourth"));
    }
}
