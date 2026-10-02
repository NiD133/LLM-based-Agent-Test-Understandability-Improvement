package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testCloseTest {

    /**
     * Verifies that closing a BoundedReader propagates the close() call to
     * its underlying reader, ensuring resources are not leaked.
     */
    @Test
    void testCloseTest() throws IOException {
        final AtomicBoolean underlyingReaderWasClosed = new AtomicBoolean();

        // Instrument the underlying reader so we can detect when it is closed.
        try (Reader trackingReader = new BufferedReader(new StringReader("01234567890")) {
            @Override
            public void close() throws IOException {
                underlyingReaderWasClosed.set(true);
                super.close();
            }
        }) {
            try (BoundedReader boundedReader = new BoundedReader(trackingReader, 3)) {
                // BoundedReader is closed here; it must propagate close() to trackingReader.
            }
        }

        assertTrue(underlyingReaderWasClosed.get(),
                "BoundedReader.close() must close the underlying reader");
    }
}
