package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkReset {

    /** End-of-stream sentinel returned by {@link Reader#read()}. */
    private static final int EOF = -1;

    /** Maximum number of characters the BoundedReader is allowed to read from the target. */
    private static final int MAX_CHARS = 3;

    /** Underlying reader with more characters than the BoundedReader's limit. */
    private final Reader target = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testMarkReset() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(target, MAX_CHARS)) {
            // Mark the start, then read up to the limit.
            boundedReader.mark(MAX_CHARS);
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();

            // Reset rewinds to the mark, so the same MAX_CHARS characters can be read again.
            boundedReader.reset();
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();

            // The bound is reached again, so the next read reports end-of-stream.
            assertEquals(EOF, boundedReader.read());
        }
    }
}
