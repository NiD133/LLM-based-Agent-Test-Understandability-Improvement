package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadMultiWithOffset {

    private static final char UNTOUCHED_BUFFER_VALUE = 'X';
    private static final int READ_OFFSET = 1;
    private static final int READ_LENGTH = 2;
    private static final int READER_LIMIT = 3;

    @Test
    void testReadMultiWithOffset() throws IOException {
        final Reader sourceReader = new BufferedReader(new StringReader("01234567890"));

        try (BoundedReader boundedReader = new BoundedReader(sourceReader, READER_LIMIT)) {
            final char[] buffer = new char[4];
            Arrays.fill(buffer, UNTOUCHED_BUFFER_VALUE);

            final int charsRead = boundedReader.read(buffer, READ_OFFSET, READ_LENGTH);

            assertEquals(2, charsRead);
            assertEquals('X', buffer[0]);
            assertEquals('0', buffer[1]);
            assertEquals('1', buffer[2]);
            assertEquals('X', buffer[3]);
        }
    }
}
