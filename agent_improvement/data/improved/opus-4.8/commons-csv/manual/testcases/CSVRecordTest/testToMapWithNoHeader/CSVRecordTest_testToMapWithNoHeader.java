package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class CSVRecordTest_testToMapWithNoHeader {

    /**
     * When a record is parsed by a format that defines no header, {@link CSVRecord#toMap()}
     * has no header names to map the values to, so it must return an empty (but non-null) map.
     */
    @Test
    void testToMapWithNoHeader() throws Exception {
        try (CSVParser parser = CSVParser.parse("a,b", CSVFormat.newFormat(','))) {
            final CSVRecord recordWithoutHeader = parser.iterator().next();

            final Map<String, String> map = recordWithoutHeader.toMap();

            assertNotNull(map, "Map is not null.");
            assertTrue(map.isEmpty(), "Map is empty.");
        }
    }
}
