package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testMarkReset {

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset, final int length) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).setLength(length).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testMarkReset() {
        // not necessary to close these resources
        @SuppressWarnings("resource")
        final UnsynchronizedByteArrayInputStream is = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        assertTrue(is.markSupported());
        assertEquals(0xa, is.read());
        assertTrue(is.markSupported());
        is.mark(10);
        assertEquals(0xb, is.read());
        assertEquals(0xc, is.read());
        is.reset();
        assertEquals(0xb, is.read());
        assertEquals(0xc, is.read());
        assertEquals(END_OF_STREAM, is.read());
        is.reset();
        assertEquals(0xb, is.read());
        assertEquals(0xc, is.read());
        assertEquals(END_OF_STREAM, is.read());
    }
}
