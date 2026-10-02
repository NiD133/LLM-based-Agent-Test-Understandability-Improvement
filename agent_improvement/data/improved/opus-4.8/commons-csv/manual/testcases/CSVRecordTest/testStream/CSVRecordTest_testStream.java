package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CSVRecord#stream()} returns the record's values as a
 * sequential, ordered stream.
 */
public class CSVRecordTest_testStream {

    /** The expected values of the single parsed record, in column order. */
    private static final String[] EXPECTED_VALUES = { "A", "B", "C" };

    /** The record under test, parsed from a single "A,B,C" line. */
    private CSVRecord record;

    @BeforeEach
    public void setUp() throws Exception {
        final String singleRow = String.join(",", EXPECTED_VALUES);
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(singleRow))) {
            record = parser.iterator().next();
        }
    }

    @Test
    void testStream() {
        final AtomicInteger index = new AtomicInteger();
        record.stream().forEach(value -> {
            assertEquals(EXPECTED_VALUES[index.get()], value);
            index.incrementAndGet();
        });
    }
}
