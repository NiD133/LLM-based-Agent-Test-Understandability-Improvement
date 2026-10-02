package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link NullInputStream#read(byte[], int, int)} behaviour after the stream has been closed.
 */
public class NullInputStreamTest_testReadByteArrayIntIntAfterClose {

    @Test
    void testReadByteArrayIntIntAfterClose() throws Exception {
        // An empty NullInputStream (size 0).
        try (InputStream closedStream = new NullInputStream()) {
            // Nothing available before closing.
            assertEquals(0, closedStream.available());

            closedStream.close();

            // Bounds are validated first: length (1) exceeds the array length (0),
            // so an IndexOutOfBoundsException is thrown regardless of the closed state.
            assertThrows(IndexOutOfBoundsException.class,
                    () -> closedStream.read(new byte[0], 0, 1));

            // A zero-length read is a no-op that short-circuits before the closed
            // check, so it returns 0 even on a closed stream.
            assertEquals(0, closedStream.read(new byte[1], 0, 0));

            // A real read on a closed stream fails with an IOException.
            assertThrows(IOException.class,
                    () -> closedStream.read(new byte[2], 0, 1));
        }
    }
}
