package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link SequenceReader#skip(long)} across the boundary between the
 * two underlying readers "Foo" and "Bar".
 */
public class SequenceReaderTest_testSkip {

    /**
     * Reads {@code expected} one character at a time from {@code reader} and
     * asserts that each character matches, so failures point at the exact index.
     */
    private void assertReadsExactly(final Reader reader, final String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), (char) reader.read(), "Read[" + i + "] of '" + expected + "'");
        }
    }

    @Test
    void testSkip() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            // Skipping 3 characters consumes the whole first reader ("Foo").
            assertEquals(3, reader.skip(3));

            // The second reader ("Bar") is now the current source.
            assertReadsExactly(reader, "Bar");

            // Nothing left to skip once both readers are exhausted.
            assertEquals(0, reader.skip(3));
        }
    }
}
