package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testSkipTest {

    // A long string to ensure the bound limit — not the source content — determines EOF
    private final Reader sourceReader = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testSkipTest() throws IOException {
        // BoundedReader allows at most 3 characters: skip 2, read 1, then expect EOF
        try (BoundedReader mr = new BoundedReader(sourceReader, 3)) {
            mr.skip(2);   // consume 2 of the 3 allowed characters
            mr.read();    // consume the last (3rd) allowed character
            assertEquals(-1, mr.read()); // bound is exhausted — EOF regardless of underlying stream
        }
    }
}
