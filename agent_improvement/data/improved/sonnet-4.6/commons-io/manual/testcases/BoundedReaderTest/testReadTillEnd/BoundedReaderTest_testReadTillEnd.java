package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests that BoundedReader returns EOF after the character limit is reached,
 * even when the underlying reader still has data available.
 */
public class BoundedReaderTest_testReadTillEnd {

    private static final int MAX_CHARS = 3;
    private static final String INPUT_WITH_MORE_THAN_MAX_CHARS = "01234567890";

    @Test
    void testReadTillEnd() throws IOException {
        Reader underlying = new BufferedReader(new StringReader(INPUT_WITH_MORE_THAN_MAX_CHARS));

        try (BoundedReader boundedReader = new BoundedReader(underlying, MAX_CHARS)) {
            // Read exactly up to the character limit
            boundedReader.read(); // reads '0'
            boundedReader.read(); // reads '1'
            boundedReader.read(); // reads '2'

            // The next read must return EOF because the bound has been reached,
            // even though the underlying reader still has characters remaining.
            int result = boundedReader.read();
            assertEquals(-1, result, "BoundedReader should return EOF after reading MAX_CHARS characters");
        }
    }
}
