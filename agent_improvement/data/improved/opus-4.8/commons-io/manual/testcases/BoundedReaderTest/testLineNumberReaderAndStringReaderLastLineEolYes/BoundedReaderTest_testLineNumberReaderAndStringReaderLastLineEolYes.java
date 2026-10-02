package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that reading every line through a {@link LineNumberReader} backed by a {@link BoundedReader}
 * terminates promptly when the input ends with a trailing end-of-line character.
 */
public class BoundedReaderTest_testLineNumberReaderAndStringReaderLastLineEolYes {

    /** Fails the test if reading all lines does not finish within this window (guards against infinite loops). */
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    /** A generous character limit so the bound never trips during this test. */
    private static final int UNLIMITED = 10_000_000;

    /** Three lines, each terminated by a newline (note the trailing EOL after "2"). */
    private static final String INPUT_WITH_TRAILING_EOL = "0\n1\n2\n";

    /**
     * Reads every line from the source through a {@link BoundedReader} until EOF.
     */
    private void readAllLines(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, UNLIMITED))) {
            while (reader.readLine() != null) {
                // Drain all lines; we only care that reading reaches EOF and returns.
            }
        }
    }

    @Test
    void testLineNumberReaderAndStringReaderLastLineEolYes() {
        assertTimeout(TIMEOUT, () -> readAllLines(new StringReader(INPUT_WITH_TRAILING_EOL)));
    }
}
