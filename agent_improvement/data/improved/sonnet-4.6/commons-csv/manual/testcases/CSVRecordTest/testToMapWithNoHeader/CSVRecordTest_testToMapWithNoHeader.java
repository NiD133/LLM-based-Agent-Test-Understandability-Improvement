package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies that CSVRecord.toMap() returns an empty (not null) map when the
 * parser was created without a header mapping.
 */
public class CSVRecordTest_testToMapWithNoHeader {

    @Test
    void testToMapWithNoHeader() throws Exception {
        try (CSVParser parser = CSVParser.parse("a,b", CSVFormat.newFormat(','))) {
            final CSVRecord shortRec = parser.iterator().next();
            final Map<String, String> map = shortRec.toMap();
            assertNotNull(map, "Map is not null.");
            assertTrue(map.isEmpty(), "Map is empty.");
        }
    }
}
