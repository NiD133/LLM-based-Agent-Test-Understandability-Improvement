package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link SequenceReader} accepts an {@link Iterable} of readers and
 * yields their characters one after another, then reports end-of-stream.
 */
public class SequenceReaderTest_testReadIterable {

    /** Value returned by {@link Reader#read()} once the stream is exhausted. */
    private static final int END_OF_STREAM = -1;

    /**
     * Asserts that the reader is exhausted: repeated reads keep returning
     * end-of-stream rather than ever producing another character.
     */
    private void assertExhausted(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(END_OF_STREAM, reader.read());
        }
    }

    @Test
    void testReadIterable() throws IOException {
        // Two single-character readers supplied as an Iterable<Reader>.
        final List<Reader> readers = new ArrayList<>();
        readers.add(new StringReader("F"));
        readers.add(new StringReader("B"));
        final Iterable<Reader> iterable = readers;

        try (Reader reader = new SequenceReader(iterable)) {
            // The readers are consumed in order: first "F", then "B".
            assertEquals('F', reader.read());
            assertEquals('B', reader.read());
            // After both readers are drained, only end-of-stream remains.
            assertExhausted(reader);
        }
    }
}
