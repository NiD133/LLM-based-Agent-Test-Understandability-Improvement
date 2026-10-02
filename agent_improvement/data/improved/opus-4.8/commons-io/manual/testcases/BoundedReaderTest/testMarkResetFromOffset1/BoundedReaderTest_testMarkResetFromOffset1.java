package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BoundedReader#mark(int)} and {@link BoundedReader#reset()} respect the
 * character limit imposed by the reader, even when reading resumes from a marked offset.
 */
public class BoundedReaderTest_testMarkResetFromOffset1 {

    /** End-of-stream sentinel returned by {@link Reader#read()}. */
    private static final int END_OF_STREAM = -1;

    /** Maximum number of characters the {@link BoundedReader} is allowed to read from its target. */
    private static final int MAX_CHARS = 3;

    /** Underlying reader with more characters than the bound, so the bound is what stops reads. */
    private final Reader underlying = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testMarkResetFromOffset1() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlying, MAX_CHARS)) {
            // Mark at the start, allowing up to MAX_CHARS to be read before reset() is no longer possible.
            boundedReader.mark(MAX_CHARS);

            // Consume the 3 characters permitted by the bound.
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();

            // The bound is now exhausted, so the next read reports end-of-stream.
            assertEquals(END_OF_STREAM, boundedReader.read());

            // Reset back to the mark and re-mark with a read-ahead limit of 1 character.
            boundedReader.reset();
            boundedReader.mark(1);

            // Only a single character may be read before the new (smaller) limit is hit.
            boundedReader.read();
            assertEquals(END_OF_STREAM, boundedReader.read());
        }
    }
}
