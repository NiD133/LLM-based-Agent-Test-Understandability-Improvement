package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CSVRecordTest_testDuplicateHeaderToMap {

    /**
     * When a CSV header line contains duplicate column names, {@link CSVRecord#toMap()}
     * keeps only the last occurrence of each name. Here the header "A,A,B,B" maps to the
     * data row "1,2,5,6", so column "A" should resolve to the second "A" value ("2") and
     * column "B" to the second "B" value ("6").
     */
    @Test
    void testDuplicateHeaderToMap() throws IOException {
        final String csvWithDuplicateHeaders = "A,A,B,B\n1,2,5,6\n";
        final CSVFormat formatWithHeader = CSVFormat.DEFAULT.builder().setHeader().get();

        try (CSVParser parser = CSVParser.parse(csvWithDuplicateHeaders, formatWithHeader)) {
            final CSVRecord record = parser.nextRecord();
            final Map<String, String> valuesByHeader = record.toMap();

            assertAll("toMap() keeps the last value for each duplicated header",
                    () -> assertEquals("2", valuesByHeader.get("A")),
                    () -> assertEquals("6", valuesByHeader.get("B")));
        }
    }
}
