package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class LineIteratorTest_testNextWithException {

    @Test
    void testNextWithException() throws Exception {
        final Reader reader = readerThatThrowsWhenReadingLine();

        try (LineIterator lineIterator = new LineIterator(reader)) {
            assertThrows(IllegalStateException.class, lineIterator::hasNext);
        }
    }

    private static Reader readerThatThrowsWhenReadingLine() {
        return new BufferedReader(new StringReader("")) {

            @Override
            public String readLine() throws IOException {
                throw new IOException("hasNext");
            }
        };
    }
}
