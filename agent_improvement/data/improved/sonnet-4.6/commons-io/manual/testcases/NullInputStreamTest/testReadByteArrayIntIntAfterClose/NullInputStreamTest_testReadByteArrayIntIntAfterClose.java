package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testReadByteArrayIntIntAfterClose {

    @Test
    @DisplayName("read(byte[], offset, length) behaviour after stream is closed")
    void testReadByteArrayIntIntAfterClose() throws Exception {
        try (InputStream in = new NullInputStream()) {
            // A freshly constructed size-0 NullInputStream should report no bytes available.
            assertEquals(0, in.available());

            in.close();

            // Bounds check (offset + length > bytes.length) is performed before the closed-stream
            // check, so an IndexOutOfBoundsException is expected even on a closed stream.
            assertThrows(IndexOutOfBoundsException.class, () -> in.read(new byte[0], 0, 1));

            // When length == 0 the method short-circuits and returns 0 without checking
            // whether the stream is closed, so no exception should be thrown.
            assertEquals(0, in.read(new byte[1], 0, 0));

            // A valid-bounds read with length > 0 on a closed stream must throw IOException.
            assertThrows(IOException.class, () -> in.read(new byte[2], 0, 1));
        }
    }
}
