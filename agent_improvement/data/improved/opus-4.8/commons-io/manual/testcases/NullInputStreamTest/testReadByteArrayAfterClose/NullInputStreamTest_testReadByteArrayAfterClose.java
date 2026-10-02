package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link NullInputStream} behaves when its array-based {@code read}
 * method is called after the stream has been closed.
 */
public class NullInputStreamTest_testReadByteArrayAfterClose {

    @Test
    void testReadByteArrayAfterClose() throws Exception {
        // A default NullInputStream emulates an empty (size 0) stream.
        try (InputStream in = new NullInputStream()) {
            // Nothing is available to read from an empty stream.
            assertEquals(0, in.available());

            in.close();

            // Reading zero bytes is a no-op and is allowed even after closing.
            assertEquals(0, in.read(new byte[0]));

            // Reading one or more bytes after closing must fail.
            assertThrows(IOException.class, () -> in.read(new byte[2]));
        }
    }
}
