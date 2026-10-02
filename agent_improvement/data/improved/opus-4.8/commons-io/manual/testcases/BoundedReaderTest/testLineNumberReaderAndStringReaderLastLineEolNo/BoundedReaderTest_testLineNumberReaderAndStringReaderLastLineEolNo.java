package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies that reading a {@link BoundedReader} line by line through a
 * {@link LineNumberReader} terminates, even when the very last line has no
 * trailing end-of-line character.
 *
 * <p>
 * This guards against a regression where {@code readLine()} could loop forever
 * once the bound was reached but the underlying content did not end with a
 * newline.
 * </p>
 */
public class BoundedReaderTest_testLineNumberReaderAndStringReaderLastLineEolNo {

    /** Fails the test if reading the lines does not finish within this window. */
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    /** Three lines where the final line ("2") is not followed by a newline. */
    private static final String CONTENT_WITHOUT_TRAILING_EOL = "0\n1\n2";

    /**
     * Upper bound on characters; far larger than the content so the bound is
     * never actually the limiting factor here.
     */
    private static final int MAX_CHARS = 10_000_000;

    /**
     * Reads every line from the given source through a bounded reader until EOF.
     */
    private void readAllLines(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, MAX_CHARS))) {
            while (reader.readLine() != null) {
                // Drain the reader; we only care that the loop terminates.
            }
        }
    }

    @Test
    void testLineNumberReaderAndStringReaderLastLineEolNo() {
        assertTimeout(TIMEOUT, () -> readAllLines(new StringReader(CONTENT_WITHOUT_TRAILING_EOL)));
    }
}
