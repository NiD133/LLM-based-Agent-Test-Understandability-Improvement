package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LineIterator} translates an {@link IOException} raised while
 * reading the underlying {@link Reader} into an {@link IllegalStateException}.
 */
public class LineIteratorTest_testNextWithException {

    @Test
    void hasNextWrapsReaderIOExceptionAsIllegalState() throws Exception {
        // A Reader whose readLine() always fails, simulating an I/O error mid-iteration.
        final Reader failingReader = new BufferedReader(new StringReader("")) {

            @Override
            public String readLine() throws IOException {
                throw new IOException("simulated read failure");
            }
        };

        try (LineIterator lineIterator = new LineIterator(failingReader)) {
            // hasNext() reads from the failing reader and must surface the IOException
            // as an IllegalStateException rather than propagating it directly.
            assertThrows(IllegalStateException.class, lineIterator::hasNext);
        }
    }
}
