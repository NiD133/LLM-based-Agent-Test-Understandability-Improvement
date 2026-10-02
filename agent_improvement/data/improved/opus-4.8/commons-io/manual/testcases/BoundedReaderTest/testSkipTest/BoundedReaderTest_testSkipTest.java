package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BoundedReader#skip(long)} counts skipped characters against
 * the reader's character limit.
 */
public class BoundedReaderTest_testSkipTest {

    /** Underlying reader whose content is longer than the bound we impose below. */
    private final Reader underlyingReader = new BufferedReader(new StringReader("01234567890"));

    /** End-of-file marker returned by {@link Reader#read()}. */
    private static final int EOF = -1;

    /** Maximum number of characters the BoundedReader is allowed to consume. */
    private static final int MAX_CHARS = 3;

    @Test
    void testSkipCountsTowardCharacterLimit() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReader, MAX_CHARS)) {
            // Skipping 2 characters consumes 2 of the 3 allowed characters.
            boundedReader.skip(2);
            // Reading 1 character consumes the final allowed character.
            boundedReader.read();
            // The limit is now reached, so the next read must report EOF.
            assertEquals(EOF, boundedReader.read());
        }
    }
}
