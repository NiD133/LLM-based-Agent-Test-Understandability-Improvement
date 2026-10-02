package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SequenceReader#skip(long)}.
 */
public class SequenceReaderTest_testSkip {

    /**
     * Asserts that reading one character at a time from {@code reader} yields exactly
     * the characters of {@code expected}, in order.
     */
    private void assertReadsExactly(final Reader reader, final String expected) throws IOException {
        for (int index = 0; index < expected.length(); index++) {
            final char expectedChar = expected.charAt(index);
            assertEquals(expectedChar, (char) reader.read(),
                    "Character at index " + index + " of '" + expected + "'");
        }
    }

    @Test
    void testSkip() throws IOException {
        // A SequenceReader that reads "Foo" followed by "Bar" (6 characters in total).
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            // Skipping 3 characters consumes the whole first reader ("Foo").
            assertEquals(3, reader.skip(3));

            // The remaining content is exactly the second reader ("Bar").
            assertReadsExactly(reader, "Bar");

            // Nothing is left to skip once every reader has been exhausted.
            assertEquals(0, reader.skip(3));
        }
    }
}
