package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.StringReader;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testIsConsistent {

    private CSVRecord record;

    private CSVRecord recordWithHeader;

    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(CSVRecordTest.EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    void testIsConsistent() {
        // A record without a header mapping is always consistent
        assertTrue(record.isConsistent(), "Record without headers should always be consistent");

        // A record whose header count matches its value count is consistent
        assertTrue(recordWithHeader.isConsistent(), "Record with a matching header-to-value count should be consistent");

        // getHeaderMap() returns a defensive copy; mutating it must not affect the parser's internal state
        final Map<String, Integer> headerMapCopy = recordWithHeader.getParser().getHeaderMap();
        headerMapCopy.put("fourth", Integer.valueOf(4));
        // We are working on a copy of the map, so the record should still be OK.
        assertTrue(recordWithHeader.isConsistent(), "Record should remain consistent after mutating the copy returned by getHeaderMap()");
    }
}
