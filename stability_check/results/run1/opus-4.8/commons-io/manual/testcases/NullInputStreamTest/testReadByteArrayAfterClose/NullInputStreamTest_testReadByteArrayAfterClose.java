package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link NullInputStream#read(byte[])} behaves after the stream has been closed.
 */
public class NullInputStreamTest_testReadByteArrayAfterClose {

    @Test
    void testReadByteArrayAfterClose() throws Exception {
        // Default constructor emulates an empty (size 0) stream.
        try (InputStream closedStream = new NullInputStream()) {
            assertEquals(0, closedStream.available(), "An empty stream has no available bytes");

            closedStream.close();

            // Reading into an empty array is a no-op and returns 0 even when closed.
            assertEquals(0, closedStream.read(new byte[0]), "Reading zero bytes should return 0");

            // Reading into a non-empty array after close must fail.
            assertThrows(IOException.class, () -> closedStream.read(new byte[2]),
                    "Reading from a closed stream should throw IOException");
        }
    }
}
