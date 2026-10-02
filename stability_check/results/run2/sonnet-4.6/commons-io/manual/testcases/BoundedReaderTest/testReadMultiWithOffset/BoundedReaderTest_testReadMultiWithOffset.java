package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadMultiWithOffset {

    private final Reader sourceReader = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testReadMultiWithOffset() throws IOException {
        // BoundedReader limits reading to at most 3 characters from the source
        try (BoundedReader boundedReader = new BoundedReader(sourceReader, 3)) {

            // Buffer of size 4, pre-filled with sentinel 'X' to detect which slots are written
            final char[] buffer = new char[4];
            Arrays.fill(buffer, 'X');

            // Read 2 characters into buffer starting at offset 1 (slots 1 and 2)
            final int charsRead = boundedReader.read(buffer, 1, 2);

            // Exactly 2 characters should have been read
            assertEquals(2, charsRead);

            // Slot 0 was before the offset — should remain untouched
            assertEquals('X', buffer[0]);

            // Slots 1 and 2 receive the first two characters of the source ("01234567890")
            assertEquals('0', buffer[1]);
            assertEquals('1', buffer[2]);

            // Slot 3 was beyond the requested length — should remain untouched
            assertEquals('X', buffer[3]);
        }
    }
}
