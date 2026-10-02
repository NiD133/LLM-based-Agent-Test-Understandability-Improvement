package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link UnsynchronizedByteArrayInputStream#read()}, the single-byte read method.
 */
public class UnsynchronizedByteArrayInputStreamTest_testReadSingle {

    /**
     * Builds a stream over the whole {@code buffer} using the builder API.
     * The builder declares a checked {@link IOException}, but it can never be thrown here
     * because a byte array needs no conversion, so we fail the test if it ever occurs.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testReadSingle() {
        // An empty stream immediately reports end of stream.
        UnsynchronizedByteArrayInputStream emptyStream = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        assertEquals(END_OF_STREAM, emptyStream.read());

        // read() returns each byte in order as an unsigned int (0-255), then END_OF_STREAM.
        UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        assertEquals(0xa, stream.read());
        assertEquals(0xb, stream.read());
        assertEquals(0xc, stream.read());
        assertEquals(END_OF_STREAM, stream.read());
    }
}
