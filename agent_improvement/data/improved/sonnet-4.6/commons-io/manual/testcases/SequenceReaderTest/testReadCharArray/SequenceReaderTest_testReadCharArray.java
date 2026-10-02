package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadCharArray {

    // Represents the default zero-value char that fills an unwritten buffer slot
    private static final char UNWRITTEN = 0;

    /**
     * Asserts that the first {@code expected.length} elements of {@code actual} match {@code expected}.
     */
    private void assertCharsEqual(final char[] expected, final char[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "Mismatch at index " + i);
        }
    }

    /**
     * Verifies that reading across two concatenated readers ("Foo" + "Bar" = "FooBar")
     * fills char buffers correctly, spanning reader boundaries, and returns EOF when exhausted.
     */
    @Test
    void testReadCharArray() throws IOException {
        // Compose "FooBar" from two separate readers
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {

            // Read first 2 chars: consumes "Fo" from the first reader
            char[] buf2 = new char[2];
            assertEquals(2, reader.read(buf2), "Should read 2 chars");
            assertCharsEqual(new char[] { 'F', 'o' }, buf2);

            // Read next 3 chars: spans the boundary — 'o' from "Foo" and 'B','a' from "Bar"
            char[] buf3 = new char[3];
            assertEquals(3, reader.read(buf3), "Should read 3 chars across reader boundary");
            assertCharsEqual(new char[] { 'o', 'B', 'a' }, buf3);

            // Read remaining 1 char ('r') into a 3-element buffer; leftover slots stay zero-filled
            char[] buf3b = new char[3];
            assertEquals(1, reader.read(buf3b), "Should read the last remaining char");
            assertCharsEqual(new char[] { 'r', UNWRITTEN, UNWRITTEN }, buf3b);

            // All chars consumed — subsequent read must signal EOF
            assertEquals(EOF, reader.read(buf3b), "Should return EOF when stream is exhausted");
        }
    }
}
