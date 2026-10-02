package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testIsInconsistent {

    @Test
    void testIsInconsistent() throws IOException {
        // A record is inconsistent when its value count differs from the header count.
        // Here we start with 3 headers and 3 values, then inject a 4th header entry
        // so the header map (size 4) no longer matches the record (size 3).
        final String[] values = {"A", "B", "C"};
        final String[] headers = {"first", "second", "third"};
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.withHeader(headers).parse(new StringReader(rowData))) {
            final Map<String, Integer> headerMap = parser.getHeaderMapRaw();
            final CSVRecord record = parser.iterator().next();
            headerMap.put("fourth", Integer.valueOf(4));
            assertFalse(record.isConsistent());
        }
    }
}
