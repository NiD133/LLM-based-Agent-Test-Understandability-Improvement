package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadList {

    private void assertRepeatedReadsReturnEof(final Reader reader) throws IOException {
        for (int readAttempt = 0; readAttempt < 10; readAttempt++) {
            assertEquals(-1, reader.read());
        }
    }

    @Test
    void testReadList() throws IOException {
        final List<Reader> readers = new ArrayList<>();
        readers.add(new StringReader("F"));
        readers.add(new StringReader("B"));

        try (Reader reader = new SequenceReader(readers)) {
            assertEquals('F', reader.read());
            assertEquals('B', reader.read());
            assertRepeatedReadsReturnEof(reader);
        }
    }
}
