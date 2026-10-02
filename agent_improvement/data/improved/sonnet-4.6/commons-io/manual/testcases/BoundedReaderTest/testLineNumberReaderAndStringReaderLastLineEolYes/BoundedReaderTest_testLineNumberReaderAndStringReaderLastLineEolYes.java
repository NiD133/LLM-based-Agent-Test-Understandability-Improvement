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

    // Three lines, last line terminated with a newline
    private static final String STRING_END_EOL = "0\n1\n2\n";

    /**
     * Drains all lines through a LineNumberReader backed by a BoundedReader.
     * The loop detects any hang caused by BoundedReader returning EOF prematurely,
     * which would cause LineNumberReader to loop indefinitely.
     */
    private void testLineNumberReader(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, 10_000_000))) {
            while (reader.readLine() != null) {
                // noop
            }
        }
    }

    @Test
    void testLineNumberReaderAndStringReaderLastLineEolYes() {
        assertTimeout(TIMEOUT, () -> testLineNumberReader(new StringReader(STRING_END_EOL)));
    }
}
