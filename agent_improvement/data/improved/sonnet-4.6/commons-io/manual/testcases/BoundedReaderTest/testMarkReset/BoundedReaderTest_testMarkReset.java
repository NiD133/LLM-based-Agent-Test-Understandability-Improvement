package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkReset {

    private final Reader underlyingReader = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testMarkReset() throws IOException {
        // BoundedReader limits reads to 3 characters total from the underlying reader
        try (BoundedReader mr = new BoundedReader(underlyingReader, 3)) {

            // Mark the current position; up to 3 characters may be read before reset
            mr.mark(3);

            // Read all 3 allowed characters
            assertEquals('0', mr.read());
            assertEquals('1', mr.read());
            assertEquals('2', mr.read());

            // Reset back to the marked position
            mr.reset();

            // After reset, the same 3 characters can be read again
            assertEquals('0', mr.read());
            assertEquals('1', mr.read());
            assertEquals('2', mr.read());

            // The bound is exhausted again — further reads return EOF
            assertEquals(-1, mr.read());
        }
    }
}
