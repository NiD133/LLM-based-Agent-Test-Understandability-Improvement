package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

/**
 * Tests that closing a {@link BoundedReader} also closes the underlying reader it wraps.
 */
public class BoundedReaderTest_testCloseTest {

    @Test
    void closingBoundedReaderClosesUnderlyingReader() throws IOException {
        // Tracks whether the underlying reader's close() was invoked.
        final AtomicBoolean underlyingReaderClosed = new AtomicBoolean();

        // A reader that records when it is closed.
        final Reader underlyingReader = new BufferedReader(new StringReader("01234567890")) {

            @Override
            public void close() throws IOException {
                underlyingReaderClosed.set(true);
                super.close();
            }
        };

        // Closing the BoundedReader (via try-with-resources) should cascade to the underlying reader.
        try (Reader trackedReader = underlyingReader) {
            try (BoundedReader boundedReader = new BoundedReader(trackedReader, 3)) {
                // No reads needed; we only verify close() propagation.
            }
        }

        assertTrue(underlyingReaderClosed.get(), "Underlying reader should be closed when BoundedReader is closed");
    }
}
