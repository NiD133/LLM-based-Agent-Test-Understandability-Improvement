package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadTillEnd {

    /** Number of characters the BoundedReader is allowed to read from the target. */
    private static final int MAX_CHARS = 3;

    /** Underlying reader with more characters available than the bound permits. */
    private final Reader target = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testReadTillEnd() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(target, MAX_CHARS)) {
            // Consume exactly the allowed number of characters.
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();

            // Once the bound is reached, further reads report EOF even though
            // the underlying reader still has characters left.
            assertEquals(-1, boundedReader.read());
        }
    }
}
