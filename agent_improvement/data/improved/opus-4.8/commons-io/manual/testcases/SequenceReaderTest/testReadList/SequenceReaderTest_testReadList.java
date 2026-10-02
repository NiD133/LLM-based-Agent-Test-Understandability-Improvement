package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadList {

    /** The value {@link Reader#read()} returns once every reader is exhausted. */
    private static final int END_OF_STREAM = -1;

    /**
     * Asserts that the reader is fully exhausted: every subsequent read must
     * report end-of-stream rather than ever yielding more characters.
     */
    private void assertExhausted(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(END_OF_STREAM, reader.read());
        }
    }

    @Test
    void testReadList() throws IOException {
        // A SequenceReader should concatenate its readers, returning each
        // reader's characters in order before falling through to end-of-stream.
        final List<Reader> readers = new ArrayList<>();
        readers.add(new StringReader("F"));
        readers.add(new StringReader("B"));

        try (Reader sequenceReader = new SequenceReader(readers)) {
            assertEquals('F', sequenceReader.read(), "first character comes from the first reader");
            assertEquals('B', sequenceReader.read(), "second character comes from the second reader");
            assertExhausted(sequenceReader);
        }
    }
}
