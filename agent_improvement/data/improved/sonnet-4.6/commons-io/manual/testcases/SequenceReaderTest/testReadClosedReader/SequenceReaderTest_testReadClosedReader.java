package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadClosedReader {

    // Number of consecutive reads used to confirm that EOF is returned consistently
    private static final int EOF_VERIFICATION_READ_COUNT = 10;

    /**
     * Verifies that every read on the given reader returns EOF, confirming the reader
     * is exhausted or closed and will not produce any further characters.
     */
    private void assertReadsReturnEof(final Reader reader) throws IOException {
        for (int i = 0; i < EOF_VERIFICATION_READ_COUNT; i++) {
            assertEquals(EOF, reader.read(),
                    "Expected EOF on read attempt " + (i + 1) + " of " + EOF_VERIFICATION_READ_COUNT);
        }
    }

    @Test
    @DisplayName("Reading from a closed SequenceReader should return EOF on every read")
    void testReadClosedReader() throws IOException {
        // SequenceReader is intentionally closed before reading; resource warning suppressed.
        @SuppressWarnings("resource")
        final Reader reader = new SequenceReader(new CharSequenceReader("FooBar"));

        reader.close();

        // All subsequent reads after close must return EOF
        assertReadsReturnEof(reader);
    }
}
