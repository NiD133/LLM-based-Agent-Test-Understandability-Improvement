package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkResetWithMarkOutsideBoundedReaderMaxAndInitialOffset {

    @Test
    void testMarkResetWithMarkOutsideBoundedReaderMaxAndInitialOffset() throws IOException {
        // BoundedReader limits reads to 3 characters total from the underlying source.
        // One character is read before calling mark(), so only 2 more characters remain
        // within the bound. The mark readAheadLimit of 3 intentionally exceeds this
        // remaining capacity, so the BoundedReader's max (not the readAheadLimit) is
        // what enforces EOF after those 2 additional reads.
        Reader source = new BufferedReader(new StringReader("01234567890"));
        try (BoundedReader boundedReader = new BoundedReader(source, 3)) {
            boundedReader.read();       // reads '0'; charsRead = 1
            boundedReader.mark(3);      // mark set after 1st char; effective readAheadLimit = 3-1 = 2
            boundedReader.read();       // reads '1'; charsRead = 2
            boundedReader.read();       // reads '2'; charsRead = 3 (hits the bound)
            // The next read must return EOF because the 3-character bound is exhausted
            assertEquals(-1, boundedReader.read());
        }
    }
}
