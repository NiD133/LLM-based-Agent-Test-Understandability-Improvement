package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testClose {

    private static final String CONTENT = "FooBar";
    private static final String CONTENT_PREFIX = "Foo";

    private void assertReadsCharacters(final Reader reader, final String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), (char) reader.read(), "Read[" + i + "] of '" + expected + "'");
        }
    }

    private void assertAllReadsReturnEof(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(EOF, reader.read());
        }
    }

    @Test
    void testClose() throws IOException {
        final Reader reader = new SequenceReader(new CharSequenceReader(CONTENT));
        assertReadsCharacters(reader, CONTENT_PREFIX);
        reader.close();
        assertAllReadsReturnEof(reader);
    }
}
