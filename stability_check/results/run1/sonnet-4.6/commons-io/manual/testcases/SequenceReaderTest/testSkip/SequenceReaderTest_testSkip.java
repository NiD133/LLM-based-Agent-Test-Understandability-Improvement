package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testSkip {

    /**
     * Reads each character from the reader and asserts it matches the expected string.
     */
    private void checkRead(final Reader reader, final String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), (char) reader.read(), "Read[" + i + "] of '" + expected + "'");
        }
    }

    /**
     * Verifies that skip() advances past characters in the first reader,
     * that subsequent reads continue from the second reader,
     * and that skip() returns 0 when no characters remain.
     */
    @Test
    void testSkip() throws IOException {
        // "Foo" is the first segment; "Bar" is the second
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            // Skipping exactly 3 characters should consume all of "Foo"
            long charsSkipped = reader.skip(3);
            assertEquals(3, charsSkipped, "skip(3) should skip the entire first reader 'Foo'");

            // The next characters to read should come from the second reader
            checkRead(reader, "Bar");

            // No characters remain, so skip should return 0
            long charsSkippedAtEnd = reader.skip(3);
            assertEquals(0, charsSkippedAtEnd, "skip(3) past end-of-stream should return 0");
        }
    }
}
