package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.StringReader;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetInt {

    private CSVRecord record;

    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
    }

    @Test
    void testGetInt() {
        assertEquals(values[0], record.get(0));
        assertEquals(values[1], record.get(1));
        assertEquals(values[2], record.get(2));
    }
}
