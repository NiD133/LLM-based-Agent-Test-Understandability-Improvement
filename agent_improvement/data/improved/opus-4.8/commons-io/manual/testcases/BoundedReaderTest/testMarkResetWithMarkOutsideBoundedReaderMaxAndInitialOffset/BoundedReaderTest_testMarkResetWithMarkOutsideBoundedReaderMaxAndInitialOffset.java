package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkResetWithMarkOutsideBoundedReaderMaxAndInitialOffset {

    /** End-of-file marker returned by {@link Reader#read()} when no more characters are available. */
    private static final int EOF = -1;

    /** The maximum number of characters the {@link BoundedReader} is allowed to read from its target. */
    private static final int MAX_CHARS = 3;

    /** Underlying reader with more characters than the bound, so the bound (not the data) ends the stream. */
    private final Reader underlying = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testMarkResetWithMarkOutsideBoundedReaderMaxAndInitialOffset() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlying, MAX_CHARS)) {
            // Read one character first, so the mark is set at a non-zero offset.
            boundedReader.read();

            // Mark with a read-ahead limit that, combined with the initial offset, points past MAX_CHARS.
            boundedReader.mark(3);

            // Read up to the bound: these two reads bring the total read count to MAX_CHARS.
            boundedReader.read();
            boundedReader.read();

            // The bound is now reached, so any further read returns EOF.
            assertEquals(EOF, boundedReader.read());
        }
    }
}
