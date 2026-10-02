package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadCollection {

    /**
     * Asserts that the reader is exhausted by verifying repeated reads all return EOF.
     */
    private void assertReaderExhausted(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(EOF, reader.read(), "Expected EOF on read attempt " + (i + 1));
        }
    }

    /**
     * Verifies that SequenceReader built from a Collection reads characters from each
     * underlying reader in order and then signals EOF.
     */
    @Test
    void testReadCollection() throws IOException {
        final Collection<Reader> readers = new ArrayList<>();
        readers.add(new StringReader("F"));
        readers.add(new StringReader("B"));

        try (Reader sequenceReader = new SequenceReader(readers)) {
            assertEquals('F', sequenceReader.read(), "First character should come from the first reader");
            assertEquals('B', sequenceReader.read(), "Second character should come from the second reader");
            assertReaderExhausted(sequenceReader);
        }
    }
}
