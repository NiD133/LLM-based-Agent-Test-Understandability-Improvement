package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SequenceReader#close()} and that reading after close yields EOF.
 */
public class SequenceReaderTest_testAutoClose {

    private static final int EOF = -1;

    /**
     * Asserts that reading the {@link Reader} character by character returns
     * exactly the {@code expected} text.
     */
    private void assertReadsExactly(final Reader reader, final String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), (char) reader.read(),
                    "Character at index " + i + " of '" + expected + "'");
        }
    }

    /**
     * Asserts that the {@link Reader} is exhausted: repeated reads keep returning EOF.
     */
    private void assertAtEof(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(EOF, reader.read());
        }
    }

    @Test
    void testAutoClose() throws IOException {
        // The try-with-resources auto-closes the SequenceReader on exit.
        try (Reader reader = new SequenceReader(new CharSequenceReader("FooBar"))) {
            // Read only the first three characters of "FooBar".
            assertReadsExactly(reader, "Foo");

            // After an explicit close, the reader must report EOF on every read.
            reader.close();
            assertAtEof(reader);
        }
    }
}
