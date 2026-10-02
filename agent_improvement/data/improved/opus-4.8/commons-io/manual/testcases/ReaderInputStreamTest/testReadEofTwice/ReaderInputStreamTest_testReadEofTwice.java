package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests that once a {@link ReaderInputStream} has reached the end of its input,
 * every subsequent call to {@link ReaderInputStream#read()} keeps returning the
 * end-of-stream marker ({@code -1}) instead of failing or returning stale data.
 */
public class ReaderInputStreamTest_testReadEofTwice {

    /** The three characters the stream is expected to yield, in order, before EOF. */
    private static final String SOURCE_TEXT = "123";

    /** Value returned by {@link ReaderInputStream#read()} once the input is exhausted. */
    private static final int END_OF_STREAM = -1;

    @Test
    void testReadEofTwice() throws IOException {
        try (ReaderInputStream stream = ReaderInputStream.builder()
                .setCharset(StandardCharsets.UTF_8)
                .setReader(new StringReader(SOURCE_TEXT))
                .get()) {

            // Read each character of "123"; UTF-8 encodes these as single bytes.
            assertEquals('1', stream.read());
            assertEquals('2', stream.read());
            assertEquals('3', stream.read());

            // The input is now exhausted: the first read past the end signals EOF...
            assertEquals(END_OF_STREAM, stream.read());
            // ...and reading again stays at EOF rather than throwing or wrapping around.
            assertEquals(END_OF_STREAM, stream.read());
        }
    }
}
