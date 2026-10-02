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
     * Asserts that reading character-by-character from the given reader yields exactly the
     * expected text, reporting which character mismatched on failure.
     */
    private void assertReadsExactly(final Reader reader, final String expected) throws IOException {
        for (int index = 0; index < expected.length(); index++) {
            final char actual = (char) reader.read();
            assertEquals(expected.charAt(index), actual, "Read[" + index + "] of '" + expected + "'");
        }
    }

    @Test
    void testSkip() throws IOException {
        // The sequence reader concatenates "Foo" and "Bar" into the logical stream "FooBar".
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            // Skip past the first reader's content ("Foo").
            assertEquals(3, reader.skip(3));

            // The next characters available should be the second reader's content ("Bar").
            assertReadsExactly(reader, "Bar");

            // Nothing is left to skip once the whole sequence has been consumed.
            assertEquals(0, reader.skip(3));
        }
    }
}
