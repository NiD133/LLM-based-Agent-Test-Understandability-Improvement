package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testReadByteArrayAfterClose {

    @Test
    void testReadByteArrayAfterClose() throws Exception {
        final byte[] emptyBuffer = new byte[0];
        final byte[] nonEmptyBuffer = new byte[2];

        try (InputStream in = new NullInputStream()) {
            assertEquals(0, in.available(), "A new zero-length NullInputStream has no available bytes");

            in.close();

            assertEquals(0, in.read(emptyBuffer), "A zero-length read succeeds even after the stream is closed");
            assertThrows(IOException.class, () -> in.read(nonEmptyBuffer),
                    "A non-empty read after close must fail");
        }
    }
}
