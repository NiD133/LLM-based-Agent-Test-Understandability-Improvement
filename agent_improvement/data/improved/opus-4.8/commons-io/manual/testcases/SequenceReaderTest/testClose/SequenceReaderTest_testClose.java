package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testClose {

    /**
     * Reads characters one at a time and asserts they match {@code expected} in order.
     */
    private void assertReadsChars(final Reader reader, final String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), (char) reader.read(), "Read[" + i + "] of '" + expected + "'");
        }
    }

    /**
     * Asserts the reader is exhausted: every {@link Reader#read()} returns EOF (-1).
     */
    private void assertAtEof(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(EOF, reader.read());
        }
    }

    @Test
    void testClose() throws IOException {
        final Reader reader = new SequenceReader(new CharSequenceReader("FooBar"));

        // Consume the first three characters.
        assertReadsChars(reader, "Foo");

        // After closing, the reader must report EOF instead of returning "Bar".
        reader.close();
        assertAtEof(reader);
    }
}
