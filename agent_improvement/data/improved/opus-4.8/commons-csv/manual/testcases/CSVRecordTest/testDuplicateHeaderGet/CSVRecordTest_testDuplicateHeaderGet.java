package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CSVRecordTest_testDuplicateHeaderGet {

    /**
     * When a CSV has duplicate header names, {@link CSVRecord#get(String)} must
     * return the value of the <em>last</em> column that uses that header name.
     *
     * <p>For the input below the headers "A" and "B" each appear twice:</p>
     * <pre>
     *   header:  A  A  B  B
     *   values:  1  2  5  6
     * </pre>
     * <p>So {@code get("A")} resolves to the second "A" column ("2") and
     * {@code get("B")} resolves to the second "B" column ("6").</p>
     */
    @Test
    void testDuplicateHeaderGet() throws IOException {
        final String csvWithDuplicateHeaders = "A,A,B,B\n1,2,5,6\n";
        final CSVFormat formatWithHeader = CSVFormat.DEFAULT.builder().setHeader().get();

        try (CSVParser parser = CSVParser.parse(csvWithDuplicateHeaders, formatWithHeader)) {
            final CSVRecord record = parser.nextRecord();

            assertAll("get(String) returns the last column when headers are duplicated",
                    () -> assertEquals("2", record.get("A")),
                    () -> assertEquals("6", record.get("B")));
        }
    }
}
