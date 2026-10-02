package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BoundedReader#read(char[], int, int)} honors the given
 * offset and length: it fills only the requested slice of the buffer and
 * leaves the surrounding positions untouched.
 */
public class BoundedReaderTest_testReadMultiWithOffset {

    /** Underlying reader whose content is longer than the bound below. */
    private final Reader underlying = new BufferedReader(new StringReader("01234567890"));

    /** Maximum number of characters the BoundedReader is allowed to read. */
    private static final int MAX_CHARS = 3;

    @Test
    void testReadMultiWithOffset() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlying, MAX_CHARS)) {
            // Prefill the buffer with a sentinel so we can tell which slots get overwritten.
            final char[] buffer = new char[4];
            Arrays.fill(buffer, 'X');

            // Read 2 characters into the buffer starting at offset 1.
            final int charsRead = boundedReader.read(buffer, 1, 2);

            assertEquals(2, charsRead, "should read exactly the requested 2 characters");
            assertEquals('X', buffer[0], "slot before the offset must stay untouched");
            assertEquals('0', buffer[1], "first read character lands at the offset");
            assertEquals('1', buffer[2], "second read character follows the first");
            assertEquals('X', buffer[3], "slot beyond offset + length must stay untouched");
        }
    }
}
