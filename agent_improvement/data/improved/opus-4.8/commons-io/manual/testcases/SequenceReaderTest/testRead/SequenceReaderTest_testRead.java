package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SequenceReader#read()}, verifying that characters are returned
 * from each underlying {@link Reader} in sequence and that end-of-stream is
 * reported once every reader is exhausted.
 */
public class SequenceReaderTest_testRead {

    /** Value that {@link Reader#read()} returns once no more characters are available. */
    private static final int END_OF_STREAM = -1;

    /**
     * Asserts that the reader is fully exhausted: every subsequent read must
     * report end-of-stream rather than blocking or returning stale data.
     */
    private void assertExhausted(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(END_OF_STREAM, reader.read());
        }
    }

    @Test
    void testRead() throws IOException {
        // Two readers concatenated: "Foo" should be drained before "Bar".
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            // Characters from the first reader ("Foo").
            assertEquals('F', reader.read());
            assertEquals('o', reader.read());
            assertEquals('o', reader.read());
            // Characters from the second reader ("Bar"), read seamlessly after the first.
            assertEquals('B', reader.read());
            assertEquals('a', reader.read());
            assertEquals('r', reader.read());
            // Both readers are now consumed, so further reads signal end-of-stream.
            assertExhausted(reader);
        }
    }
}
