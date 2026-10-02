package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link NullInputStream#read(byte[])} behaves once the stream has been closed.
 */
public class NullInputStreamTest_testReadByteArrayAfterClose {

    @Test
    void testReadByteArrayAfterClose() throws Exception {
        try (InputStream closedStream = new NullInputStream()) {
            // An empty NullInputStream reports no bytes available.
            assertEquals(0, closedStream.available());

            closedStream.close();

            // Reading into an empty array is a no-op and returns 0, even after close.
            assertEquals(0, closedStream.read(new byte[0]));

            // Reading into a non-empty array after close must fail.
            assertThrows(IOException.class, () -> closedStream.read(new byte[2]));
        }
    }
}
