package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadIterable {

    private void assertReadsEndOfFileRepeatedly(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(-1, reader.read());
        }
    }

    @Test
    void testReadIterable() throws IOException {
        final Collection<Reader> sourceReaders = new ArrayList<>();
        sourceReaders.add(new StringReader("F"));
        sourceReaders.add(new StringReader("B"));

        final Iterable<Reader> iterable = sourceReaders;
        try (Reader reader = new SequenceReader(iterable)) {
            assertEquals('F', reader.read());
            assertEquals('B', reader.read());
            assertReadsEndOfFileRepeatedly(reader);
        }
    }
}
