package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BoundedReader} returns EOF (-1) when the underlying reader
 * is exhausted before the configured character limit is reached.
 */
public class BoundedReaderTest_testShortReader {

    /** Underlying reader holding only two characters: '0' and '1'. */
    private final Reader twoCharReader = new BufferedReader(new StringReader("01"));

    @Test
    void testShortReader() throws IOException {
        // The bound (3) is larger than the underlying reader's content (2 chars),
        // so EOF comes from the exhausted underlying reader, not from the bound.
        final int charLimit = 3;
        try (BoundedReader boundedReader = new BoundedReader(twoCharReader, charLimit)) {
            boundedReader.read(); // reads '0'
            boundedReader.read(); // reads '1'
            assertEquals(-1, boundedReader.read(), "Underlying reader is exhausted, so EOF is expected");
        }
    }
}
