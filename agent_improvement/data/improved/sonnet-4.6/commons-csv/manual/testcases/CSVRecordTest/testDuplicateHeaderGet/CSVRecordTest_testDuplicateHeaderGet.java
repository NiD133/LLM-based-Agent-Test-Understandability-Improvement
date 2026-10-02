package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CSVRecordTest_testDuplicateHeaderGet {

    /**
     * When a CSV has duplicate column headers, {@link CSVRecord#get(String)} must return
     * the value from the last occurrence of that header.
     *
     * Input:  headers "A,A,B,B"  with data row "1,2,5,6"
     * So "A" maps to index 1 (value "2") and "B" maps to index 3 (value "6").
     */
    @Test
    void testDuplicateHeaderGet() throws IOException {
        final String csv = "A,A,B,B\n1,2,5,6\n";
        final CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().get();

        try (CSVParser parser = CSVParser.parse(csv, format)) {
            final CSVRecord record = parser.nextRecord();

            assertAll("get() should return the last value for each duplicate header",
                    () -> assertEquals("2", record.get("A"), "duplicate header 'A': expected last occurrence (index 1)"),
                    () -> assertEquals("6", record.get("B"), "duplicate header 'B': expected last occurrence (index 3)")
            );
        }
    }
}
