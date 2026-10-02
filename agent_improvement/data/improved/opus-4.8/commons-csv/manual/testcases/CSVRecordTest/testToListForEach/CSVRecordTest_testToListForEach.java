package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CSVRecord#toList()} in combination with {@link java.util.List#forEach}.
 */
public class CSVRecordTest_testToListForEach {

    /** The values that make up the single parsed record. */
    private String[] values;

    /** The record parsed from {@link #values}, without any header mapping. */
    private CSVRecord record;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
        final String csvLine = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(csvLine))) {
            record = parser.iterator().next();
        }
    }

    @Test
    void testToListForEach() {
        // forEach gives no index, so track the expected position with a mutable counter.
        final AtomicInteger nextIndex = new AtomicInteger();
        record.toList().forEach(value ->
            assertEquals(values[nextIndex.getAndIncrement()], value));
    }
}
