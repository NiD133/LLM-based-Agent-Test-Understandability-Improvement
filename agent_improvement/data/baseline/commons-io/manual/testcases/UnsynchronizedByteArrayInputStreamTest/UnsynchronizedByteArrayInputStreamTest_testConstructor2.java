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

public class UnsynchronizedByteArrayInputStreamTest_testConstructor2 {

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
    // not necessary to close these resources
    @SuppressWarnings("resource")
    void testConstructor2() {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];
        UnsynchronizedByteArrayInputStream is = newStream(empty, 0);
        assertEquals(empty.length, is.available());
        is = newStream(empty, 1);
        assertEquals(0, is.available());
        is = newStream(one, 0);
        assertEquals(one.length, is.available());
        is = newStream(one, 1);
        assertEquals(0, is.available());
        is = newStream(one, 2);
        assertEquals(0, is.available());
        is = newStream(some, 0);
        assertEquals(some.length, is.available());
        is = newStream(some, 1);
        assertEquals(some.length - 1, is.available());
        is = newStream(some, 10);
        assertEquals(some.length - 10, is.available());
        is = newStream(some, some.length);
        assertEquals(0, is.available());
    }
}
