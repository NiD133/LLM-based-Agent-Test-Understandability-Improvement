package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadCharArray {

    private static final char NUL = 0;

    private void assertBufferContents(final char[] expected, final char[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "Compare[" + i + "]");
        }
    }

    private void assertRead(final Reader reader, final int expectedCount, final char[] expectedContents,
            final char[] buffer) throws IOException {
        assertEquals(expectedCount, reader.read(buffer));
        assertBufferContents(expectedContents, buffer);
    }

    @Test
    void testReadCharArray() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            char[] buffer = new char[2];
            assertRead(reader, 2, new char[] { 'F', 'o' }, buffer);

            buffer = new char[3];
            assertRead(reader, 3, new char[] { 'o', 'B', 'a' }, buffer);

            buffer = new char[3];
            assertRead(reader, 1, new char[] { 'r', NUL, NUL }, buffer);

            assertEquals(-1, reader.read(buffer));
        }
    }
}
