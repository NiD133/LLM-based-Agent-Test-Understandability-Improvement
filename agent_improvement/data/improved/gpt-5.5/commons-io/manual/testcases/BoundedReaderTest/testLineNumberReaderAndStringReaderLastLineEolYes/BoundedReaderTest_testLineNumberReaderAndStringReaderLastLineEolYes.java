package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testLineNumberReaderAndStringReaderLastLineEolYes {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final int LARGE_BOUND = 10_000_000;
    private static final String STRING_END_EOL = "0\n1\n2\n";

    private void readAllLines(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, LARGE_BOUND))) {
            while (reader.readLine() != null) {
                // Drain the reader to verify that a final line ending reaches EOF normally.
            }
        }
    }

    @Test
    void testLineNumberReaderAndStringReaderLastLineEolYes() {
        assertTimeout(TIMEOUT, () -> readAllLines(new StringReader(STRING_END_EOL)));
    }
}
