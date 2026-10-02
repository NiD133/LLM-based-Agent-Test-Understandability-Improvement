package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadIterable {

    /**
     * Asserts that reading from an exhausted reader returns EOF consistently
     * across multiple calls, confirming stable end-of-stream behavior.
     */
    private void checkReadEof(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(EOF, reader.read());
        }
    }

    @Test
    void testReadIterable() throws IOException {
        // Arrange: two single-character readers supplied as an Iterable
        final Collection<Reader> readerList = new ArrayList<>();
        readerList.add(new StringReader("F"));
        readerList.add(new StringReader("B"));
        final Iterable<Reader> iterable = readerList;

        // Act + Assert: characters are read in order, then EOF is stable
        try (Reader sequenceReader = new SequenceReader(iterable)) {
            assertEquals('F', sequenceReader.read());
            assertEquals('B', sequenceReader.read());
            checkReadEof(sequenceReader);
        }
    }
}
