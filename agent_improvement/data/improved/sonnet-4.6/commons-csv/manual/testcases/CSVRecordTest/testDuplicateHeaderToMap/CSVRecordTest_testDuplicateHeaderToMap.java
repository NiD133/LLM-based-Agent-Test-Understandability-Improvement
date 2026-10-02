package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testDuplicateHeaderToMap {

    @Test
    void testDuplicateHeaderToMap() throws IOException {
        // CSV where columns "A" and "B" each appear twice
        final String csv = "A,A,B,B\n1,2,5,6\n";
        final CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().get();

        try (CSVParser parser = CSVParser.parse(csv, format)) {
            final CSVRecord record = parser.nextRecord();
            final Map<String, String> map = record.toMap();

            // When duplicate headers exist, toMap() keeps only the last occurrence per column
            assertAll("duplicate headers: toMap() should return the last value for each column",
                () -> assertEquals("2", map.get("A"), "column 'A': last occurrence is index 1, value '2'"),
                () -> assertEquals("6", map.get("B"), "column 'B': last occurrence is index 3, value '6'")
            );
        }
    }
}
