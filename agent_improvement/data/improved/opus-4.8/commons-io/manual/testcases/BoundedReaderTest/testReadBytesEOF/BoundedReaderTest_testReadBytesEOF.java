package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link BoundedReader} wrapped in a {@link BufferedReader} reaches
 * end-of-file cleanly once its character limit is hit, without blocking.
 */
public class BoundedReaderTest_testReadBytesEOF {

    /** Fail the test if reading does not finish within this duration. */
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    /** Maximum number of characters the BoundedReader is allowed to read. */
    private static final int MAX_CHARS = 3;

    @Test
    void testReadBytesEOF() {
        assertTimeout(TIMEOUT, () -> {
            final Reader underlying = new BufferedReader(new StringReader("01234567890"));
            final BoundedReader boundedReader = new BoundedReader(underlying, MAX_CHARS);

            // Two readLine() calls must complete and signal EOF rather than block,
            // even though the underlying reader still has more characters available.
            try (BufferedReader reader = new BufferedReader(boundedReader)) {
                reader.readLine();
                reader.readLine();
            }
        });
    }
}
