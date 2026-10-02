package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Tests that reading from a closed {@link SequenceReader} reports end-of-stream.
 */
public class SequenceReaderTest_testReadClosedReader {

    private static final int EOF = -1;

    /**
     * Asserts that repeated calls to {@link Reader#read()} all return EOF, confirming
     * the reader stays exhausted rather than yielding data after being closed.
     */
    private void assertReadsReturnEof(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(EOF, reader.read());
        }
    }

    @Test
    void testReadClosedReader() throws IOException {
        @SuppressWarnings("resource")
        final Reader reader = new SequenceReader(new CharSequenceReader("FooBar"));

        reader.close();

        assertReadsReturnEof(reader);
    }
}
