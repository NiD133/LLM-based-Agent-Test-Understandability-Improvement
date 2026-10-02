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

public class UnsynchronizedByteArrayInputStreamTest_testReadArray {

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
    void testReadArray() {
        byte[] buf = new byte[10];
        UnsynchronizedByteArrayInputStream is = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        int read = is.read(buf);
        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], buf);
        buf = IOUtils.EMPTY_BYTE_ARRAY;
        is = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        read = is.read(buf);
        assertEquals(0, read);
        buf = new byte[10];
        is = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        read = is.read(buf);
        assertEquals(3, read);
        assertEquals(0xa, buf[0]);
        assertEquals(0xb, buf[1]);
        assertEquals(0xc, buf[2]);
        assertEquals(0, buf[3]);
        buf = new byte[2];
        is = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        read = is.read(buf);
        assertEquals(2, read);
        assertEquals(0xa, buf[0]);
        assertEquals(0xb, buf[1]);
        read = is.read(buf);
        assertEquals(1, read);
        assertEquals(0xc, buf[0]);
    }
}
