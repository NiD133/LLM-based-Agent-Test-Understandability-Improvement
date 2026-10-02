package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CSVRecordTest_testCSVRecordNULLValues {

    @Test
    void testCSVRecordNULLValues() throws IOException {
        try (CSVParser parser = CSVParser.parse("A,B\r\nONE,TWO", CSVFormat.DEFAULT.withHeader())) {
            final CSVRecord recordWithNullValues = new CSVRecord(parser, null, null, 0L, 0L, 0L);

            assertEquals(0, recordWithNullValues.size());
            assertThrows(IllegalArgumentException.class, () -> recordWithNullValues.get("B"));
        }
    }
}
