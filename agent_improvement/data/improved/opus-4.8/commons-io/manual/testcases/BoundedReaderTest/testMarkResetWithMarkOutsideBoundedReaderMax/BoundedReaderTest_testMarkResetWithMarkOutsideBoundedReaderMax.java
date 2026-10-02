package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkResetWithMarkOutsideBoundedReaderMax {

    /** Underlying content is longer than the bound applied by the BoundedReader. */
    private static final String UNDERLYING_CONTENT = "01234567890";

    /** Maximum number of characters the BoundedReader is allowed to read. */
    private static final int MAX_CHARS = 3;

    /** A read-ahead limit larger than MAX_CHARS; the bound must still win. */
    private static final int MARK_READ_AHEAD_LIMIT = 4;

    private static final int EOF = -1;

    /**
     * When the mark's read-ahead limit (4) exceeds the BoundedReader's character
     * bound (3), the bound is what takes effect: exactly 3 characters can be read
     * and the next read returns EOF, regardless of the larger mark limit.
     */
    @Test
    void testMarkResetWithMarkOutsideBoundedReaderMax() throws IOException {
        final Reader underlying = new BufferedReader(new StringReader(UNDERLYING_CONTENT));
        try (BoundedReader boundedReader = new BoundedReader(underlying, MAX_CHARS)) {
            boundedReader.mark(MARK_READ_AHEAD_LIMIT);

            // The first MAX_CHARS reads succeed.
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();

            // The bound (3), not the larger mark limit (4), stops further reads.
            assertEquals(EOF, boundedReader.read());
        }
    }
}
