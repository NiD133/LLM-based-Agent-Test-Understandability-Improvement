package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BoundedReader} keeps enforcing its character bound after a
 * {@code mark}/{@code reset} round-trip, even when the {@code readAheadLimit}
 * passed to {@code mark} is larger than the bound.
 */
public class BoundedReaderTest_testMarkResetMarkMore {

    /** Maximum number of characters BoundedReader is allowed to read from the target. */
    private static final int MAX_CHARS = 3;

    /** readAheadLimit for mark(); deliberately larger than MAX_CHARS to prove the bound still wins. */
    private static final int READ_AHEAD_LIMIT = 4;

    @Test
    void testMarkResetMarkMore() throws IOException {
        final Reader target = new BufferedReader(new StringReader("01234567890"));
        try (BoundedReader boundedReader = new BoundedReader(target, MAX_CHARS)) {
            boundedReader.mark(READ_AHEAD_LIMIT);

            // Read the 3 characters allowed by the bound.
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();

            // Reset rewinds back to the mark, so the bound is available again.
            boundedReader.reset();

            // Read the same 3 characters once more.
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();

            // The bound is now exhausted, so further reads return EOF.
            assertEquals(-1, boundedReader.read());
        }
    }
}
