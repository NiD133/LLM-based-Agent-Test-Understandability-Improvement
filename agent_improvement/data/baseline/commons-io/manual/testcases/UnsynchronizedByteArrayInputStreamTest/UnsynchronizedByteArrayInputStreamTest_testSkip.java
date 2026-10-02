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

public class UnsynchronizedByteArrayInputStreamTest_testSkip {

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
    void testSkip() {
        UnsynchronizedByteArrayInputStream is = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        assertEquals(3, is.available());
        is.skip(1);
        assertEquals(2, is.available());
        assertEquals(0xb, is.read());
        is.skip(1);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());
        is = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        assertEquals(3, is.available());
        is.skip(0);
        assertEquals(3, is.available());
        assertEquals(0xa, is.read());
        is = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        assertEquals(3, is.available());
        is.skip(2);
        assertEquals(1, is.available());
        assertEquals(0xc, is.read());
        assertEquals(END_OF_STREAM, is.read());
        is = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        assertEquals(3, is.available());
        is.skip(3);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());
        is = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        assertEquals(3, is.available());
        is.skip(999);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());
    }
}
