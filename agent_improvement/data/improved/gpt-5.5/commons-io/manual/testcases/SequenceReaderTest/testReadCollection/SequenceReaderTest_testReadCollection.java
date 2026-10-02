package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadCollection {

    private void assertRepeatedEndOfFile(final Reader reader) throws IOException {
        for (int attempt = 0; attempt < 10; attempt++) {
            assertEquals(-1, reader.read());
        }
    }

    @Test
    void testReadCollection() throws IOException {
        final Collection<Reader> readers = new ArrayList<>();
        readers.add(new StringReader("F"));
        readers.add(new StringReader("B"));

        try (Reader reader = new SequenceReader(readers)) {
            assertEquals('F', reader.read());
            assertEquals('B', reader.read());
            assertRepeatedEndOfFile(reader);
        }
    }
}
