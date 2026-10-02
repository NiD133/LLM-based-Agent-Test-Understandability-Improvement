package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testShortReader {

    @Test
    void testShortReader() throws IOException {
        // The underlying reader has only 2 chars ("01"), but the bound is 3.
        // BoundedReader should return EOF when the underlying reader is exhausted,
        // even though the character limit has not been reached yet.
        Reader underlyingReader = new BufferedReader(new StringReader("01"));
        try (BoundedReader boundedReader = new BoundedReader(underlyingReader, 3)) {
            boundedReader.read(); // reads '0'
            boundedReader.read(); // reads '1'
            assertEquals(-1, boundedReader.read()); // underlying reader exhausted → EOF
        }
    }
}
