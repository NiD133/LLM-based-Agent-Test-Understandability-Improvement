package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Verifies how a {@link CSVRecord} behaves when it is constructed with {@code null} values
 * while still being backed by a parser that has a header mapping.
 */
public class CSVRecordTest_testCSVRecordNULLValues {

    @Test
    void testCSVRecordNULLValues() throws IOException {
        // Parser whose header maps the names "A" and "B" to column indexes 0 and 1.
        try (CSVParser parser = CSVParser.parse("A,B\r\nONE,TWO", CSVFormat.DEFAULT.withHeader())) {

            // Build a record with a null values array; CSVRecord substitutes an empty array internally.
            final CSVRecord recordWithNullValues = new CSVRecord(parser, null, null, 0L, 0L, 0L);

            // A null values array is treated as having no values.
            assertEquals(0, recordWithNullValues.size());

            // "B" is a mapped header (index 1), but the record holds zero values,
            // so looking it up by name fails with IllegalArgumentException.
            assertThrows(IllegalArgumentException.class, () -> recordWithNullValues.get("B"));
        }
    }
}
