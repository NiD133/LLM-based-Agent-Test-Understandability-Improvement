package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testToListForEach {

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

    /**
     * Verifies that toList() returns the record values in order by iterating
     * with forEach and comparing each element against the original values array.
     * AtomicInteger is used as a mutable index counter inside the lambda.
     */
    @Test
    void testToListForEach() {
        final AtomicInteger i = new AtomicInteger();
        record.toList().forEach(e -> {
            assertEquals(values[i.getAndIncrement()], e);
        });
    }
}
