package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkResetFromOffset1 {

    // A reader with more content than the BoundedReader's character limit
    private final Reader bufReader1 = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testMarkResetFromOffset1() throws IOException {
        // BoundedReader limits reads to 3 characters from the underlying reader
        try (BoundedReader mr = new BoundedReader(bufReader1, 3)) {

            // Phase 1: mark at position 0 with readAheadLimit=3, consume all 3 allowed chars,
            // then confirm that a 4th read hits EOF (the bounded limit is exhausted)
            mr.mark(3);
            mr.read(); // reads '0' (charsRead = 1)
            mr.read(); // reads '1' (charsRead = 2)
            mr.read(); // reads '2' (charsRead = 3, limit reached)
            assertEquals(-1, mr.read()); // EOF: charsRead >= maxCharsFromTargetReader

            // Phase 2: reset back to the marked position, then re-mark with readAheadLimit=1
            // so that only 1 char can be read before EOF is returned again
            mr.reset();
            mr.mark(1);
            mr.read(); // reads '0' again (charsRead - markedAt = 1, hitting the new readAheadLimit)
            assertEquals(-1, mr.read()); // EOF: readAheadLimit of 1 is now exceeded
        }
    }
}
