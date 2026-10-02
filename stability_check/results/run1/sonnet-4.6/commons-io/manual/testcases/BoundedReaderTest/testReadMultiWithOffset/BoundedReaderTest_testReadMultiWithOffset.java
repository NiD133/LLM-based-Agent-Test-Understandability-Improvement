package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadMultiWithOffset {

    // A reader backed by a long string — only the first 3 characters are accessible via BoundedReader
    private final Reader sourceReader = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testReadMultiWithOffset() throws IOException {
        // BoundedReader caps reads at 3 characters from sourceReader
        try (BoundedReader mr = new BoundedReader(sourceReader, 3)) {
            // Buffer of size 4, pre-filled with sentinel 'X' to detect which slots are written
            final char[] cbuf = new char[4];
            Arrays.fill(cbuf, 'X');

            // Read 2 chars into cbuf starting at offset 1 (slots 1 and 2)
            final int read = mr.read(cbuf, 1, 2);

            // Exactly 2 characters should have been read
            assertEquals(2, read);
            // Slot 0 is before the offset — must remain untouched
            assertEquals('X', cbuf[0]);
            // Slots 1 and 2 receive the first two characters from the source ('0' and '1')
            assertEquals('0', cbuf[1]);
            assertEquals('1', cbuf[2]);
            // Slot 3 is beyond offset+length — must remain untouched
            assertEquals('X', cbuf[3]);
        }
    }
}
