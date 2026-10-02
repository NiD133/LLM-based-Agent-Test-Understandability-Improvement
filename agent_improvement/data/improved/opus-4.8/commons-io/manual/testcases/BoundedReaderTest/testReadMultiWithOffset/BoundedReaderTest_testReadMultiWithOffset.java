package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link BoundedReader#read(char[], int, int)} when reading into the middle of a
 * destination buffer (a non-zero offset).
 */
public class BoundedReaderTest_testReadMultiWithOffset {

    /** Underlying data; the BoundedReader will only expose the first few characters of it. */
    private final Reader underlying = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testReadMultiWithOffset() throws IOException {
        // Allow at most 3 characters to be read from the underlying reader.
        final int maxChars = 3;
        try (BoundedReader boundedReader = new BoundedReader(underlying, maxChars)) {
            // Pre-fill the buffer with a sentinel 'X' so we can tell which slots get overwritten.
            final char[] buffer = new char[4];
            Arrays.fill(buffer, 'X');

            // Read 2 characters, writing them starting at offset 1.
            final int charsRead = boundedReader.read(buffer, 1, 2);

            // Two characters ('0' and '1') were read into slots 1 and 2.
            assertEquals(2, charsRead);
            assertEquals('X', buffer[0], "offset 0 should be untouched");
            assertEquals('0', buffer[1], "first character read");
            assertEquals('1', buffer[2], "second character read");
            assertEquals('X', buffer[3], "trailing slot should be untouched");
        }
    }
}
