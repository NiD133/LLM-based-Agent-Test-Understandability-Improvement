package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadMulti {

    private static final String SOURCE_TEXT = "01234567890";
    private static final int MAX_CHARS_TO_READ = 3;
    private static final int REQUESTED_CHARS = 4;
    private static final char UNTOUCHED_BUFFER_VALUE = 'X';

    @Test
    void testReadMulti() throws IOException {
        final Reader sourceReader = new BufferedReader(new StringReader(SOURCE_TEXT));

        try (BoundedReader boundedReader = new BoundedReader(sourceReader, MAX_CHARS_TO_READ)) {
            final char[] buffer = new char[REQUESTED_CHARS];
            Arrays.fill(buffer, UNTOUCHED_BUFFER_VALUE);

            final int charsRead = boundedReader.read(buffer, 0, REQUESTED_CHARS);

            assertEquals(MAX_CHARS_TO_READ, charsRead);
            assertArrayEquals(new char[] {'0', '1', '2', UNTOUCHED_BUFFER_VALUE}, buffer);
        }
    }
}
