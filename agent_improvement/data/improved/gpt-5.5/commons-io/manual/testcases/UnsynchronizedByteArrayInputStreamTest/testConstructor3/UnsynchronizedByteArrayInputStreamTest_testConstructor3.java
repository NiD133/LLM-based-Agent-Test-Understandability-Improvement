package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testConstructor3 {

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

    private void assertAvailable(final int expectedAvailable, final byte[] buffer, final int offset) {
        final UnsynchronizedByteArrayInputStream inputStream = newStream(buffer, offset);
        assertEquals(expectedAvailable, inputStream.available());
    }

    private void assertAvailable(final int expectedAvailable, final byte[] buffer, final int offset, final int length) {
        final UnsynchronizedByteArrayInputStream inputStream = newStream(buffer, offset, length);
        assertEquals(expectedAvailable, inputStream.available());
    }

    @Test
    // not necessary to close these resources
    @SuppressWarnings("resource")
    void testConstructor3() {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

        assertAvailableForEmptyBuffer(empty);
        assertAvailableForSingleByteBuffer(one);
        assertAvailableForLargerBuffer(some);
    }

    private void assertAvailableForEmptyBuffer(final byte[] empty) {
        assertAvailable(empty.length, empty, 0);
        assertAvailable(0, empty, 1);
        assertAvailable(0, empty, 0, 1);
        assertAvailable(0, empty, 1, 1);
    }

    private void assertAvailableForSingleByteBuffer(final byte[] one) {
        assertAvailable(one.length, one, 0);
        assertAvailable(one.length - 1, one, 1);
        assertAvailable(0, one, 2);
        assertAvailable(1, one, 0, 1);
        assertAvailable(0, one, 1, 1);
        assertAvailable(1, one, 0, 2);
        assertAvailable(0, one, 2, 1);
        assertAvailable(0, one, 2, 2);
    }

    private void assertAvailableForLargerBuffer(final byte[] some) {
        assertAvailable(some.length, some, 0);
        assertAvailable(some.length - 1, some, 1);
        assertAvailable(some.length - 10, some, 10);
        assertAvailable(0, some, some.length);
        assertAvailable(0, some, some.length, some.length);
        assertAvailable(1, some, some.length - 1, some.length);
        assertAvailable(7, some, 0, 7);
        assertAvailable(7, some, 7, 7);
        assertAvailable(some.length, some, 0, some.length * 2);
        assertAvailable(1, some, some.length - 1, 7);
    }
}
