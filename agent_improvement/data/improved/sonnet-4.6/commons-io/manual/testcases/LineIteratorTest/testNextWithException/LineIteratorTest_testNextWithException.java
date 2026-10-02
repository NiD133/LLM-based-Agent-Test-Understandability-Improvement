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
        // A reader whose readLine() always throws — simulates an I/O failure mid-iteration.
        // LineIterator.hasNext() is required to convert that IOException into an
        // IllegalStateException so callers are not forced to catch checked exceptions.
        final Reader failingReader = new BufferedReader(new StringReader("")) {
            @Override
            public String readLine() throws IOException {
                throw new IOException("hasNext");
            }
        };

        try (LineIterator li = new LineIterator(failingReader)) {
            assertThrows(IllegalStateException.class, li::hasNext);
        }
    }
}
