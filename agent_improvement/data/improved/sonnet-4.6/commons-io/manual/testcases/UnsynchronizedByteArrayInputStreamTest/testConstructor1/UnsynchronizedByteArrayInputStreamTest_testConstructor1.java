package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testConstructor1 {

    /**
     * Creates a stream from a byte array using the builder API.
     * IOException is not expected here since no I/O conversion occurs for byte arrays.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Verifies that a stream constructed from a byte array reports available()
     * equal to the full length of that array, for empty, single-byte, and
     * multi-byte inputs.
     */
    @Test
    void testConstructor1() throws IOException {
        final byte[] emptyBuffer = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] singleByteBuffer = new byte[1];
        final byte[] multiByteBuffer = new byte[25];

        try (UnsynchronizedByteArrayInputStream emptyStream = newStream(emptyBuffer)) {
            assertEquals(emptyBuffer.length, emptyStream.available(),
                    "Stream over an empty array should report 0 bytes available");
        }
        try (UnsynchronizedByteArrayInputStream singleByteStream = newStream(singleByteBuffer)) {
            assertEquals(singleByteBuffer.length, singleByteStream.available(),
                    "Stream over a 1-byte array should report 1 byte available");
        }
        try (UnsynchronizedByteArrayInputStream multiByteStream = newStream(multiByteBuffer)) {
            assertEquals(multiByteBuffer.length, multiByteStream.available(),
                    "Stream over a 25-byte array should report 25 bytes available");
        }
    }
}
