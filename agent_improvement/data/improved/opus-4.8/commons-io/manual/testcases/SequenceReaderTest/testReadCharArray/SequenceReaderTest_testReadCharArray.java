package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SequenceReader#read(char[])} when reading across the boundary
 * of the two underlying readers "Foo" and "Bar" (concatenated as "FooBar").
 */
public class SequenceReaderTest_testReadCharArray {

    /** The value of an untouched element in a freshly allocated {@code char[]} ('\0'). */
    private static final char UNWRITTEN = 0;

    /** Return value of {@link Reader#read(char[])} signalling end of stream. */
    private static final int END_OF_STREAM = -1;

    /**
     * Asserts that every element of {@code actual} equals the matching element of
     * {@code expected}, reporting the index on failure.
     */
    private void assertBufferContents(final char[] expected, final char[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "Mismatch at index [" + i + "]");
        }
    }

    @Test
    void testReadCharArray() throws IOException {
        // Reading "Foo" then "Bar" in sequence yields the stream "FooBar".
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {

            // First read: a 2-char buffer is filled entirely from "Foo" -> "Fo".
            char[] buffer = new char[2];
            assertEquals(2, reader.read(buffer), "Should read 2 chars: 'F', 'o'");
            assertBufferContents(new char[] { 'F', 'o' }, buffer);

            // Second read: a 3-char buffer crosses the reader boundary,
            // taking the last 'o' of "Foo" and the first two chars "Ba" of "Bar".
            buffer = new char[3];
            assertEquals(3, reader.read(buffer), "Should read 3 chars across the boundary: 'o', 'B', 'a'");
            assertBufferContents(new char[] { 'o', 'B', 'a' }, buffer);

            // Third read: only the final 'r' remains, so just 1 char is read
            // and the rest of the buffer keeps its initial '\0' values.
            buffer = new char[3];
            assertEquals(1, reader.read(buffer), "Should read the last remaining char: 'r'");
            assertBufferContents(new char[] { 'r', UNWRITTEN, UNWRITTEN }, buffer);

            // Stream is now exhausted: a further read reports end of stream.
            assertEquals(END_OF_STREAM, reader.read(buffer), "Should report end of stream");
        }
    }
}
