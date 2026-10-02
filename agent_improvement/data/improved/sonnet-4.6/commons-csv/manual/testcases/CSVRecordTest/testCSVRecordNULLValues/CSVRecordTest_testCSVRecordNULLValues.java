package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Verifies CSVRecord behaviour when constructed with a null values array.
 * A null values argument is normalised to an empty array internally, so
 * size() must return 0 and any header-based get() must throw
 * IllegalArgumentException because no value index can be satisfied.
 */
public class CSVRecordTest_testCSVRecordNULLValues {

    @Test
    void testCSVRecordNULLValues() throws IOException {
        // Build a parser whose header map knows about columns "A" and "B".
        try (CSVParser parser = CSVParser.parse("A,B\r\nONE,TWO", CSVFormat.DEFAULT.withHeader())) {

            // Construct a record with null values – internally stored as empty array.
            final CSVRecord csvRecord = new CSVRecord(parser, null, null, 0L, 0L, 0L);

            // Null values collapses to an empty array, so size is 0.
            assertEquals(0, csvRecord.size());

            // Although the parser has a header map for "B", the record has no
            // values, so get("B") cannot resolve the index and must throw.
            assertThrows(IllegalArgumentException.class, () -> csvRecord.get("B"));
        }
    }
}
