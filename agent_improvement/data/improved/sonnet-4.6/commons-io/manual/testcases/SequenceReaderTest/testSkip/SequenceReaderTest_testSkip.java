package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testSkip {

    /**
     * Reads characters one-by-one from {@code reader} and asserts each matches
     * the corresponding character in {@code expected}.
     */
    private void checkRead(final Reader reader, final String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), (char) reader.read(), "Read[" + i + "] of '" + expected + "'");
        }
    }

    /**
     * Verifies that {@link SequenceReader#skip(long)} correctly advances across
     * reader boundaries and returns 0 when the combined stream is exhausted.
     *
     * <p>Scenario:
     * <ol>
     *   <li>Skip exactly all 3 characters of the first reader ("Foo") —
     *       skip should report 3 characters skipped.</li>
     *   <li>Read the remaining 3 characters from the second reader ("Bar") —
     *       each character must match in order.</li>
     *   <li>Skip again after EOF — skip should report 0, not throw.</li>
     * </ol>
     */
    @Test
    void testSkip() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            // Skip all of "Foo"; the sequence reader should cross into "Bar"
            assertEquals(3, reader.skip(3));

            // "Foo" is now exhausted; read characters from "Bar" one by one
            checkRead(reader, "Bar");

            // Both readers are exhausted; skip on an empty stream must return 0
            assertEquals(0, reader.skip(3));
        }
    }
}
