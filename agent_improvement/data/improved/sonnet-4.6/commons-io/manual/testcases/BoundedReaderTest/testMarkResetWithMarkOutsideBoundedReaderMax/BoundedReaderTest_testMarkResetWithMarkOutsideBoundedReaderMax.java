package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkResetWithMarkOutsideBoundedReaderMax {

    @Test
    void testMarkResetWithMarkOutsideBoundedReaderMax() throws IOException {
        // BoundedReader is capped at 3 characters; mark(4) requests a readAheadLimit
        // that exceeds the cap, but the cap still wins — the 4th read must return EOF.
        Reader source = new BufferedReader(new StringReader("01234567890"));
        try (BoundedReader mr = new BoundedReader(source, 3)) {
            mr.mark(4); // readAheadLimit > bound; the bound (3) is the hard ceiling
            mr.read();  // reads '0'
            mr.read();  // reads '1'
            mr.read();  // reads '2' — exhausts the 3-char bound
            assertEquals(-1, mr.read()); // bound reached; further reads return EOF
        }
    }
}
