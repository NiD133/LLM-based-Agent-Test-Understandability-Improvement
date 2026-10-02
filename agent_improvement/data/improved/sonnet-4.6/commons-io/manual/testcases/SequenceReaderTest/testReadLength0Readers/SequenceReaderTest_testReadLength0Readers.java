package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadLength0Readers {

    private static final int REPEATED_READ_COUNT = 10;

    private void checkReadEof(final Reader reader) throws IOException {
        for (int i = 0; i < REPEATED_READ_COUNT; i++) {
            assertEquals(-1, reader.read());
        }
    }

    @Test
    void testReadLength0Readers() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader(""), new StringReader(""), new StringReader(""))) {
            checkReadEof(reader);
        }
    }
}
