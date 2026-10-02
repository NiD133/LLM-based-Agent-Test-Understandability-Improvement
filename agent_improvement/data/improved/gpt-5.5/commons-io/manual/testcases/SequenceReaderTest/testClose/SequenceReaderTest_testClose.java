package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testClose {

    private static final String CONTENT = "FooBar";
    private static final String CONTENT_READ_BEFORE_CLOSE = "Foo";
    private static final int EOF_READ_ATTEMPTS = 10;

    private void assertReadChars(final Reader reader, final String expected) throws IOException {
        for (int index = 0; index < expected.length(); index++) {
            assertEquals(expected.charAt(index), (char) reader.read(), "Read[" + index + "] of '" + expected + "'");
        }
    }

    private void assertRepeatedEndOfFile(final Reader reader) throws IOException {
        for (int attempt = 0; attempt < EOF_READ_ATTEMPTS; attempt++) {
            assertEquals(-1, reader.read());
        }
    }

    @Test
    void testClose() throws IOException {
        final Reader reader = new SequenceReader(new CharSequenceReader(CONTENT));

        assertReadChars(reader, CONTENT_READ_BEFORE_CLOSE);
        reader.close();

        assertRepeatedEndOfFile(reader);
    }
}
