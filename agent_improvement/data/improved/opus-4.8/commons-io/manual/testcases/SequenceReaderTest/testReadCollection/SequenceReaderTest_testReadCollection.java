package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link SequenceReader} can be constructed from a {@link Collection}
 * of readers and that it streams their characters one after another.
 */
public class SequenceReaderTest_testReadCollection {

    /** Value returned by {@link Reader#read()} once the end of the stream is reached. */
    private static final int END_OF_STREAM = -1;

    /**
     * Asserts that the reader is exhausted: repeated reads keep returning end-of-stream.
     */
    private void assertReaderExhausted(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(END_OF_STREAM, reader.read());
        }
    }

    @Test
    void testReadCollection() throws IOException {
        // Two single-character readers supplied as a Collection.
        final Collection<Reader> readers = new ArrayList<>();
        readers.add(new StringReader("F"));
        readers.add(new StringReader("B"));

        try (Reader sequenceReader = new SequenceReader(readers)) {
            // Characters are returned in the order the readers were added.
            assertEquals('F', sequenceReader.read());
            assertEquals('B', sequenceReader.read());

            // After both readers are consumed, the sequence reports end-of-stream.
            assertReaderExhausted(sequenceReader);
        }
    }
}
