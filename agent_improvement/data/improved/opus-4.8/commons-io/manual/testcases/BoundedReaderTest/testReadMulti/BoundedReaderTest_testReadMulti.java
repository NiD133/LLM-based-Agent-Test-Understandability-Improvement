package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link BoundedReader#read(char[], int, int)} when the caller asks for more
 * characters than the configured bound allows.
 */
public class BoundedReaderTest_testReadMulti {

    /** Characters that the underlying reader could supply if it were not bounded. */
    private static final String UNDERLYING_CONTENT = "01234567890";

    /** Upper limit on how many characters the BoundedReader is allowed to return. */
    private static final int MAX_CHARS = 3;

    @Test
    void testReadMulti() throws IOException {
        final Reader underlying = new BufferedReader(new StringReader(UNDERLYING_CONTENT));

        try (BoundedReader boundedReader = new BoundedReader(underlying, MAX_CHARS)) {
            // Request 4 characters into a buffer pre-filled with a sentinel ('X') so we
            // can tell which slots were actually written by the read.
            final char[] buffer = new char[4];
            Arrays.fill(buffer, 'X');

            final int charsRead = boundedReader.read(buffer, 0, 4);

            // The reader is bounded to 3 characters, so only 3 are returned even though 4 were requested.
            assertEquals(3, charsRead);
            // The first three slots hold the bounded content; the fourth keeps its sentinel value.
            assertEquals('0', buffer[0]);
            assertEquals('1', buffer[1]);
            assertEquals('2', buffer[2]);
            assertEquals('X', buffer[3]);
        }
    }
}
