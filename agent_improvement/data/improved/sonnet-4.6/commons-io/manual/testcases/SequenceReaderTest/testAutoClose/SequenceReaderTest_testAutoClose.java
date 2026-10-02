package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testAutoClose {

    /** Reads each character of {@code expected} from {@code reader} and asserts equality. */
    private void readAndVerifyChars(final Reader reader, final String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), (char) reader.read(), "Read[" + i + "] of '" + expected + "'");
        }
    }

    /** Asserts that 10 successive reads from {@code reader} all return EOF. */
    private void assertAllReadsReturnEof(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(EOF, reader.read());
        }
    }

    /**
     * Verifies that closing a SequenceReader mid-stream causes all subsequent reads to return EOF,
     * even though the underlying reader still has unread content ("Bar" after reading "Foo").
     */
    @Test
    void testAutoClose() throws IOException {
        try (Reader reader = new SequenceReader(new CharSequenceReader("FooBar"))) {
            // Read the first half of the content; "Bar" remains unread
            readAndVerifyChars(reader, "Foo");

            // Explicit mid-stream close; the SequenceReader should discard remaining readers
            reader.close();

            // Every read after close must signal EOF, not return the remaining "Bar"
            assertAllReadsReturnEof(reader);
        } // try-with-resources issues a second close(), which must be idempotent
    }
}
