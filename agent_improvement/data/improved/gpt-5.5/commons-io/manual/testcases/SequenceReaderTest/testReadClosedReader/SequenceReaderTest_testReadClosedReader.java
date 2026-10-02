package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadClosedReader {

    private void assertEndOfReader(final Reader reader) throws IOException {
        for (int readAttempt = 0; readAttempt < 10; readAttempt++) {
            assertEquals(-1, reader.read());
        }
    }

    @Test
    void testReadClosedReader() throws IOException {
        @SuppressWarnings("resource")
        final Reader reader = new SequenceReader(new CharSequenceReader("FooBar"));
        reader.close();

        assertEndOfReader(reader);
    }
}
