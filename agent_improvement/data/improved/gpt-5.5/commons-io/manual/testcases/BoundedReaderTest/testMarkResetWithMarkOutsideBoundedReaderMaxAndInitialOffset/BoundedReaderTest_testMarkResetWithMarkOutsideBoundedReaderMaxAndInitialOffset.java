package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkResetWithMarkOutsideBoundedReaderMaxAndInitialOffset {

    private static final String READER_CONTENT = "01234567890";
    private static final int MAX_CHARS_FROM_TARGET_READER = 3;
    private static final int READ_AHEAD_LIMIT = 3;

    private final Reader sourceReader = new BufferedReader(new StringReader(READER_CONTENT));

    @Test
    void testMarkResetWithMarkOutsideBoundedReaderMaxAndInitialOffset() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(sourceReader, MAX_CHARS_FROM_TARGET_READER)) {
            boundedReader.read();
            boundedReader.mark(READ_AHEAD_LIMIT);
            boundedReader.read();
            boundedReader.read();

            assertEquals(-1, boundedReader.read());
        }
    }
}
