package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkResetMarkMore {

    private static final String SOURCE_TEXT = "01234567890";
    private static final int MAX_CHARS_FROM_TARGET_READER = 3;
    private static final int MARK_READ_AHEAD_LIMIT = 4;
    private static final int CHARS_TO_READ_BEFORE_RESET = 3;
    private static final int EOF = -1;

    private final Reader bufReader1 = new BufferedReader(new StringReader(SOURCE_TEXT));

    @Test
    void testMarkResetMarkMore() throws IOException {
        try (BoundedReader mr = new BoundedReader(bufReader1, MAX_CHARS_FROM_TARGET_READER)) {
            mr.mark(MARK_READ_AHEAD_LIMIT);
            readCharacters(mr, CHARS_TO_READ_BEFORE_RESET);
            mr.reset();
            readCharacters(mr, CHARS_TO_READ_BEFORE_RESET);
            assertEquals(EOF, mr.read());
        }
    }

    private void readCharacters(final BoundedReader reader, final int count) throws IOException {
        for (int i = 0; i < count; i++) {
            reader.read();
        }
    }
}
