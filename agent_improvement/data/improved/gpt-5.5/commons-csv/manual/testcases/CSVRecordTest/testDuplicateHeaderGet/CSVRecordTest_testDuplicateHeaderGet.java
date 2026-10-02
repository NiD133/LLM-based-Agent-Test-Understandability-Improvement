package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CSVRecordTest_testDuplicateHeaderGet {

    @Test
    void testDuplicateHeaderGet() throws IOException {
        final String csv = "A,A,B,B\n1,2,5,6\n";
        final CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().get();

        try (CSVParser parser = CSVParser.parse(csv, format)) {
            final CSVRecord record = parser.nextRecord();

            assertAll("Test that it gets the last instance of a column when there are duplicate headings",
                    () -> assertEquals("2", record.get("A")),
                    () -> assertEquals("6", record.get("B")));
        }
    }
}
