package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        // Empty buffer: offset=0 means nothing is available
        UnsynchronizedByteArrayInputStream emptyAtZero = newStream(empty, 0);
        assertEquals(empty.length, emptyAtZero.available());

        // Empty buffer: offset beyond end still yields 0 available
        UnsynchronizedByteArrayInputStream emptyPastEnd = newStream(empty, 1);
        assertEquals(0, emptyPastEnd.available());

        // Single-byte buffer: offset=0 exposes the full 1-byte buffer
        UnsynchronizedByteArrayInputStream oneAtStart = newStream(one, 0);
        assertEquals(one.length, oneAtStart.available());

        // Single-byte buffer: offset at last valid position leaves 0 available
        UnsynchronizedByteArrayInputStream oneAtEnd = newStream(one, 1);
        assertEquals(0, oneAtEnd.available());

        // Single-byte buffer: offset beyond length is clamped, leaving 0 available
        UnsynchronizedByteArrayInputStream onePastEnd = newStream(one, 2);
        assertEquals(0, onePastEnd.available());

        // 25-byte buffer: offset=0 exposes full buffer
        UnsynchronizedByteArrayInputStream someAtStart = newStream(some, 0);
        assertEquals(some.length, someAtStart.available());

        // 25-byte buffer: offset=1 reduces available count by 1
        UnsynchronizedByteArrayInputStream someAtOne = newStream(some, 1);
        assertEquals(some.length - 1, someAtOne.available());

        // 25-byte buffer: offset=10 reduces available count by 10
        UnsynchronizedByteArrayInputStream someAtTen = newStream(some, 10);
        assertEquals(some.length - 10, someAtTen.available());

        // 25-byte buffer: offset at exactly the end leaves 0 available
        UnsynchronizedByteArrayInputStream someAtEnd = newStream(some, some.length);
        assertEquals(0, someAtEnd.available());
    }
}
