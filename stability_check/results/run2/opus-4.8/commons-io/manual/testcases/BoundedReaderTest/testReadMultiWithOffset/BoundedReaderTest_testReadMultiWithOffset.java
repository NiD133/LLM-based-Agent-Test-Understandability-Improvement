package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadMultiWithOffset {

    /**
     * Verifies that {@link BoundedReader#read(char[], int, int)} honors the requested offset
     * and length: only the bounded number of characters is read, and only the targeted slice
     * of the destination buffer is overwritten while the surrounding cells are left untouched.
     */
    @Test
    void testReadMultiWithOffset() throws IOException {
        final Reader underlying = new BufferedReader(new StringReader("01234567890"));

        try (BoundedReader boundedReader = new BoundedReader(underlying, 3)) {
            // Pre-fill the buffer with a sentinel so we can tell which cells get written.
            final char[] buffer = new char[4];
            Arrays.fill(buffer, 'X');

            // Read 2 characters into buffer starting at index 1.
            final int charsRead = boundedReader.read(buffer, 1, 2);

            assertEquals(2, charsRead, "should read exactly the 2 requested characters");
            assertEquals('X', buffer[0], "cell before the offset must stay untouched");
            assertEquals('0', buffer[1], "first read character lands at the offset");
            assertEquals('1', buffer[2], "second read character lands after the offset");
            assertEquals('X', buffer[3], "cell beyond offset + length must stay untouched");
        }
    }
}
