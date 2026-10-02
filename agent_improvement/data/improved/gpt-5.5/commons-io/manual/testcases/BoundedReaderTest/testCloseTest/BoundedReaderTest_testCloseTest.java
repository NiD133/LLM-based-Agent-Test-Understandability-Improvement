package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testCloseTest {

    @Test
    void testCloseTest() throws IOException {
        final AtomicBoolean sourceReaderWasClosed = new AtomicBoolean();
        try (Reader sourceReader = new BufferedReader(new StringReader("01234567890")) {

            @Override
            public void close() throws IOException {
                sourceReaderWasClosed.set(true);
                super.close();
            }
        }) {
            try (BoundedReader boundedReader = new BoundedReader(sourceReader, 3)) {
                // Closing the bounded reader must close the wrapped source reader.
            }
        }
        assertTrue(sourceReaderWasClosed.get());
    }
}
