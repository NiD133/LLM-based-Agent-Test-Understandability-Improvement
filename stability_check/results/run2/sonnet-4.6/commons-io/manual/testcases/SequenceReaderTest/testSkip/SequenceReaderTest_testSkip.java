package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testSkip {

    /**
     * Reads each character from {@code reader} in sequence and asserts that
     * each one matches the corresponding character in {@code expected}.
     */
    private void checkRead(final Reader reader, final String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), (char) reader.read(), "Read[" + i + "] of '" + expected + "'");
        }
    }

    /**
     * Verifies that {@code skip(n)} advances past the first reader's content,
     * that the remaining content of the second reader is still readable, and
     * that {@code skip} returns 0 when the stream is exhausted.
     */
    @Test
    void testSkip() throws IOException {
        final String firstSegment  = "Foo";
        final String secondSegment = "Bar";
        final int    firstSegmentLength = firstSegment.length(); // characters to skip

        try (Reader reader = new SequenceReader(
                new StringReader(firstSegment),
                new StringReader(secondSegment))) {

            // Skipping exactly the length of the first segment should consume "Foo"
            assertEquals(firstSegmentLength, reader.skip(firstSegmentLength));

            // After the skip, only "Bar" (the second segment) should remain
            checkRead(reader, secondSegment);

            // Once the stream is exhausted, skip must report 0 characters skipped
            assertEquals(0, reader.skip(firstSegmentLength));
        }
    }
}
